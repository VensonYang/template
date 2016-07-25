package controller.system;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import model.common.QueryVO;
import model.system.UserVO;
import model.system.UserVO.IAddUser;
import model.system.UserVO.IModifyUser;
import service.system.UserManageService;
import utils.bean.BeanDirectorFactory;

@ResponseBody
@RequestMapping("/userManage")
@Controller
public class UserManageController {
	private static final Logger logger = LoggerFactory.getLogger(UserManageController.class);
	@Autowired
	private UserManageService userManageService;

	@RequestMapping("showQueryUser")
	public ReturnResult showQueryUser() {
		ReturnResult returnResult = ControllerContext.getResult();
		QueryVO queryVO = BeanDirectorFactory.getBeanDirector().getDataVO(QueryVO.class);
		Map<String, Object> result = userManageService.qeuryUser(queryVO, ControllerHelper.getUserId());
		returnResult.setStatus(StatusCode.SUCCESS).setRows(result.get(StaticsConstancts.DATA))
				.setTotal(result.get(StaticsConstancts.TOTAL));
		logger.debug("showQueryFiles success");
		return returnResult;
	}

	@RequestMapping(value = "addUser")
	public ReturnResult addUser() {
		ReturnResult returnResult = ControllerContext.getResult();
		ValidationAware va = new ValidationAwareSupport();
		UserVO userVO = BeanDirectorFactory.getBeanDirector().getDataVO(UserVO.class, va, IAddUser.class);
		if (ControllerHelper.checkError(userVO, va, returnResult, logger)) {
			return returnResult;
		}
		userVO.setCreator(ControllerHelper.getUserId());
		userVO.setCreateTime(new Date());
		userManageService.addUser(userVO);
		returnResult.setStatus(StatusCode.SUCCESS);
		logger.debug("save User success");
		return returnResult;
	}

	@RequestMapping(value = "modifyUser")
	public ReturnResult modifyUser() {
		ReturnResult returnResult = ControllerContext.getResult();
		ValidationAware va = new ValidationAwareSupport();
		UserVO userVO = BeanDirectorFactory.getBeanDirector().getDataVO(UserVO.class, va, IModifyUser.class);
		if (ControllerHelper.checkError(userVO, va, returnResult, logger)) {
			return returnResult;
		}
		userVO.setModifier(ControllerHelper.getUserId());
		userVO.setModifyTime(new Date());
		userManageService.modifyUser(userVO);
		returnResult.setStatus(StatusCode.SUCCESS);
		logger.debug("modify User success");
		return returnResult;
	}

	@RequestMapping(value = "deleteUser")
	public ReturnResult deleteUser() {
		ReturnResult returnResult = ControllerContext.getResult();
		String param = ControllerHelper.checkParam(ValidParam.NUM);
		if (param == null) {
			return returnResult;
		}
		userManageService.deleteUser(Integer.parseInt(param));
		logger.debug("delete User success");
		return returnResult;
	}

	@RequestMapping(value = "getUser")
	public ReturnResult getUser() {
		ReturnResult returnResult = ControllerContext.getResult();
		String param = ControllerHelper.checkParam(ValidParam.NUM);
		if (param == null) {
			return returnResult;
		}
		UserVO userVO = userManageService.getUserVOByUserId(Integer.parseInt(param));
		returnResult.setStatus(StatusCode.SUCCESS).setRows(userVO);
		logger.debug("get User success");
		return returnResult;
	}

	@RequestMapping(value = "resetPassword")
	public ReturnResult resetPassword() {
		ReturnResult returnResult = ControllerContext.getResult();
		String param = ControllerHelper.checkParam(ValidParam.NUM);
		if (param == null) {
			return returnResult;
		}
		userManageService.resetPassword(Integer.parseInt(param), "123456");
		returnResult.setStatus(StatusCode.SUCCESS);
		logger.debug("密码重置成功！");
		return returnResult;
	}

	@RequestMapping("setUserRole")
	public ReturnResult setUserRole() {
		HttpServletRequest request = ControllerContext.getRequest();
		ReturnResult returnResult = ControllerContext.getResult();
		String roleId = request.getParameter("roleId");
		String userId = request.getParameter("userId");
		if (StringUtils.isNumeric(roleId) && StringUtils.isNumeric(userId)) {
			userManageService.addUserRoleByUserId(Integer.parseInt(roleId), Integer.parseInt(userId));
			returnResult.setStatus(StatusCode.SUCCESS);
			logger.debug("setUserRole success");
		} else {
			returnResult.setStatus(StatusCode.PARAMETER_ERROR.setMessage("非法用户，禁止访问！"));
		}

		return returnResult;
	}

}
