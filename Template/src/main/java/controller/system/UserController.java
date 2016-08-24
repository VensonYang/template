package controller.system;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.StaticsConstancts;
import controller.base.ControllerContext;
import controller.base.ControllerHelper;
import controller.base.ReturnResult;
import controller.base.StatusCode;
import controller.base.ValidParam;
import controller.base.ValidationAware;
import controller.base.ValidationAwareSupport;
import interceptor.Exclude;
import model.common.CropVO;
import model.system.LoginVO;
import model.system.LoginVO.IModifyPas;
import model.system.LoginVO.Ilogin;
import model.system.NodeVO;
import model.system.PrivilegesVectorVO;
import service.system.UserService;
import utils.bean.BeanDirectorFactory;
import utils.common.CookieUtil;
import utils.common.ImageCutterUtil;
import utils.common.MD5Util;
import utils.common.NetworkUtil;

@SuppressWarnings("unchecked")
@RequestMapping("/user")
@ResponseBody
@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Exclude
	@RequestMapping(value = "login")
	public ReturnResult login() throws IOException {
		ReturnResult returnResult = ControllerContext.getResult();
		ValidationAware va = new ValidationAwareSupport();
		HttpSession session = ControllerContext.getSession();
		if ((Map<String, Object>) session.getAttribute(StaticsConstancts.USER_INFO) != null) {
			logger.debug("用户已经登陆,注销用户!");
			session.invalidate();
			// returnResult.setStatus(StatusCode.FAIL.setMessage("用户已经登陆,不允许重复登陆,请先进入主页注销"));
			return returnResult;
		}
		LoginVO loginVO = BeanDirectorFactory.getBeanDirector().getDataVO(LoginVO.class, va, Ilogin.class);
		logger.debug(loginVO.toString());
		if (ControllerHelper.checkError(loginVO, va, returnResult, logger)) {
			return returnResult;
		}
		if (session.getAttribute(StaticsConstancts.VERIFY_CODE) == null
				|| !session.getAttribute(StaticsConstancts.VERIFY_CODE).toString().equals(loginVO.getVerifyCode())) {
			logger.debug(session.getAttribute(StaticsConstancts.VERIFY_CODE) + "验证码错误" + loginVO.getVerifyCode());
			returnResult.setStatus(StatusCode.FAIL.setMessage("验证码错误"));
			return returnResult;
		}
		Map<String, Object> userMap = null;
		if (loginVO.isSavePassword()) {
			logger.debug("loginByCookie");
			userMap = loginByCookie(loginVO);
		} else {
			logger.debug("loginByCode");
			userMap = loginByCode(loginVO);
		}
		if (userMap != null) {
			returnResult = loginAfter(userMap);
		} else {
			logger.debug("账号或密码错误");
			returnResult.setStatus(StatusCode.PARAMETER_ERROR.setMessage("账号或密码错误"));
		}
		return returnResult;
	}

	/**
	 * 根据Cookie查询用户信息
	 */
	private Map<String, Object> loginByCookie(LoginVO loginVO) throws UnsupportedEncodingException, IOException {
		HttpServletRequest request = ControllerContext.getRequest();
		StringBuffer salt = new StringBuffer();
		salt.append(Calendar.getInstance().get(1));
		salt.append(Calendar.getInstance().get(3));
		String cookie_account = CookieUtil.findCookie(StaticsConstancts.USER_ACCOUNT, request);
		String cookie_secret_key = CookieUtil.findCookie(StaticsConstancts.SECRET_KEY, request);
		if (StringUtils.isNotBlank(cookie_account) && StringUtils.isNotBlank(cookie_secret_key)
				&& cookie_account.equals(loginVO.getUserAccount())
				&& MD5Util.getMD5StringWithSalt(cookie_account, salt.toString()).equals(cookie_secret_key)) {
			return userService.getUserByAccount(cookie_account);
		} else {
			return loginByCode(loginVO);
		}

	}

	/**
	 * 根据用户账号密码查询用户信息
	 */
	private Map<String, Object> loginByCode(LoginVO loginVO) throws UnsupportedEncodingException, IOException {
		HttpServletResponse response = ControllerContext.getResponse();
		Map<String, Object> userMap = userService.getUserByCode(loginVO);
		if (userMap != null && userMap.size() > 0) {
			if (loginVO.isSavePassword()) {
				StringBuffer salt = new StringBuffer();
				salt.append(Calendar.getInstance().get(1));
				salt.append(Calendar.getInstance().get(3));
				CookieUtil.addCookie(StaticsConstancts.USER_ACCOUNT, loginVO.getUserAccount(), response);
				CookieUtil.addCookie(StaticsConstancts.SECRET_KEY,
						MD5Util.getMD5StringWithSalt(loginVO.getUserAccount(), salt.toString()), response);
			}
			return userMap;
		} else {
			CookieUtil.deleteCookie(StaticsConstancts.USER_ACCOUNT, response);
			CookieUtil.deleteCookie(StaticsConstancts.SECRET_KEY, response);
			return null;
		}
	}

	/**
	 * 登录成功后保存用户信息
	 */
	private ReturnResult loginAfter(Map<String, Object> userMap) throws IOException {
		HttpServletRequest request = ControllerContext.getRequest();
		ReturnResult returnResult = ControllerContext.getResult();
		HttpSession session = ControllerContext.getSession();
		if (((String) userMap.get("state")).equalsIgnoreCase("0")) {
			logger.debug("用户已被禁用,请联系系统管理员");
			return returnResult.setStatus(StatusCode.FAIL.setMessage("用户已被禁用,请联系系统管理员"));
		}
		// List<Map<String, Object>> userRoles = userService
		// .getUserRoleByUserId((Integer) userMap.get(StaticContanct.USER_ID));
		// userMap.put(StaticContanct.USER_ROLE, userRoles);
		Integer userId = (Integer) userMap.get(StaticsConstancts.USER_ID);
		Map<Integer, PrivilegesVectorVO> privilegesVector = userService.getPrivilegesVectors(userId);
		if (privilegesVector != null && privilegesVector.size() > 0) {
			userMap.put(StaticsConstancts.USER_IP_ADDRESS, NetworkUtil.getIpAddress(request));
			userMap.put(StaticsConstancts.USER_ROLE, userService.getUserRoleByUserId(userId));
			session.setAttribute(StaticsConstancts.USER_NAME, userMap.get(StaticsConstancts.USER_NAME));
			session.setAttribute(StaticsConstancts.USER_ACCOUNT, userMap.get(StaticsConstancts.USER_ACCOUNT));
			session.setAttribute(StaticsConstancts.USER_ID, userId);
			session.setAttribute(StaticsConstancts.USER_INFO, userMap);
			session.setAttribute(StaticsConstancts.PRIVILEGES_VECTOR, privilegesVector);
			logger.debug("用户登陆成功");
			return returnResult.setStatus(StatusCode.SUCCESS);
		} else {
			logger.debug("该用户没有权限");
			return returnResult.setStatus(StatusCode.FAIL.setMessage("该用户没有权限"));
		}
	}

	@RequestMapping(value = "getUOP")
	public ReturnResult getUOP() {
		ReturnResult returnResult = ControllerContext.getResult();
		HttpSession session = ControllerContext.getSession();
		String priviledgeId = ControllerHelper.checkParam(ValidParam.NUM, StaticsConstancts.PRIVILEGES_ID);
		if (priviledgeId == null) {
			return returnResult;
		}
		Map<Integer, PrivilegesVectorVO> privilegesVector = (Map<Integer, PrivilegesVectorVO>) session
				.getAttribute(StaticsConstancts.PRIVILEGES_VECTOR);
		if (privilegesVector == null) {
			returnResult.setStatus(StatusCode.FAIL.setMessage("获取页面操作权限失败"));
			logger.debug("获取页面操作权限失败");
			return returnResult;
		}
		PrivilegesVectorVO vo = ((PrivilegesVectorVO) privilegesVector.get(Integer.valueOf(priviledgeId)));
		if (vo == null) {
			returnResult.setStatus(StatusCode.FAIL.setMessage("获取页面操作权限失败"));
			logger.debug("获取页面操作权限失败");
			return returnResult;
		}
		returnResult.setStatus(StatusCode.SUCCESS).setRows(vo.getPrivilegeMatrix());
		logger.debug("获取页面操作权限成功");
		return returnResult;
	}

	@Exclude
	@RequestMapping("loginOut")
	public ReturnResult loginOut() {
		ControllerContext.getSession().invalidate();
		return ControllerContext.getResult();
	}

	@Exclude
	@RequestMapping("modifyPassword")
	public ReturnResult modifyPassword() {
		ReturnResult returnResult = ControllerContext.getResult();
		ValidationAware va = new ValidationAwareSupport();
		HttpSession session = ControllerContext.getSession();
		HttpServletResponse response = ControllerContext.getResponse();
		LoginVO loginVO = BeanDirectorFactory.getBeanDirector().getDataVO(LoginVO.class, va, IModifyPas.class);
		if (ControllerHelper.checkError(loginVO, va, returnResult, logger)) {
			return returnResult;
		}
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(StaticsConstancts.USER_INFO);
		String pas = loginVO.getPassword();
		loginVO.setPassword(loginVO.getOldPassword());
		loginVO.setUserAccount((String) userInfo.get("userAccount"));
		Map<String, Object> userMap = userService.getUserByCode(loginVO);
		if (userMap != null && userMap.size() > 0) {
			userService.modifyPassword((Integer) userInfo.get(StaticsConstancts.USER_ID), pas);
			CookieUtil.deleteCookie(StaticsConstancts.SECRET_KEY, response);
			CookieUtil.deleteCookie(StaticsConstancts.USER_ACCOUNT, response);
			logger.debug("密码修改成功！");
			returnResult.setStatus(StatusCode.SUCCESS);
		} else {
			logger.debug("旧密码有误，请重新输入！");
			returnResult.setStatus(StatusCode.PARAMETER_ERROR.setMessage("旧密码有误，请重新输入！"));
		}
		return returnResult;
	}

	@Exclude
	@RequestMapping("getMenuByUserId")
	public ReturnResult getMenuByUserId() {
		ReturnResult returnResult = ControllerContext.getResult();
		String userId = ControllerHelper.checkParam(ValidParam.NUM);
		if (userId == null) {
			return returnResult;
		}
		NodeVO result = userService.getMenuByUserId(Integer.parseInt(userId));
		returnResult.setStatus(StatusCode.SUCCESS).setRows(result);
		logger.debug("根据用户Id:[{}]获取权限成功" + returnResult);
		return returnResult;
	}

	@RequestMapping("modifyHeadImage")
	public ReturnResult modifyHeadImage() {
		ReturnResult returnResult = ControllerContext.getResult();
		CropVO crop = BeanDirectorFactory.getBeanDirector().getDataVO(CropVO.class);
		String srcPath = ControllerHelper.getUploadPath(ControllerHelper.IMAGE_UPLOAD_PATH) + crop.getFileName();
		String outFile = ControllerHelper.getUploadPath(ControllerHelper.HEAD_IMAGE_UPLOAD_PATH);
		String ext = srcPath.substring(srcPath.lastIndexOf("."));
		String buildName = ControllerHelper.makeFileName(ext);
		outFile = outFile + buildName;
		boolean result = ImageCutterUtil.cutPic(srcPath, outFile, (int) crop.getX(), (int) crop.getY(),
				(int) crop.getW(), (int) crop.getH());
		if (result) {
			userService.updateHeadImage(buildName, ControllerHelper.getUserId());
			returnResult.setStatus(StatusCode.SUCCESS);
		} else {
			returnResult.setStatus(StatusCode.FAIL.setMessage("图像裁剪失败!"));
		}
		return returnResult;
	}

}
