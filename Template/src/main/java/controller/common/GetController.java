package controller.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.StaticsConstancts;
import common.utils.CookieUtil;
import common.utils.VerifyCodeUtil;
import controller.base.ControllerContext;
import controller.result.ReturnResult;
import controller.result.StatusCode;
import interceptor.Exclude;

@RequestMapping("/get")
@ResponseBody
@Controller
public class GetController {
	private static final Logger logger = LoggerFactory.getLogger(GetController.class);

	@Exclude
	@RequestMapping(value = "userInfo")
	public ReturnResult userInfo() {
		ReturnResult returnResult = ControllerContext.getResult();
		HttpSession session = ControllerContext.getSession();
		Object map = session.getAttribute(StaticsConstancts.USER_INFO);
		if (map != null) {
			returnResult.setStatus(StatusCode.SUCCESS).setRows(map);
		} else {
			returnResult.setStatus(StatusCode.FAIL.setMessage("您还未登录，即将跳转至登录页面!"));
		}
		logger.debug("userInfo success");
		return returnResult;
	}

	@RequestMapping(value = "sessionId")
	public ReturnResult sessionId() {
		HttpServletRequest request = ControllerContext.getRequest();
		ReturnResult returnResult = ControllerContext.getResult();
		returnResult.setStatus(StatusCode.SUCCESS).setRows(request.getSession().getId());
		logger.debug("sessionId success");
		return returnResult;
	}

	@RequestMapping(value = "getPriviledgeVector")
	public ReturnResult getPriviledgeVector() {
		ReturnResult returnResult = ControllerContext.getResult();
		HttpSession session = ControllerContext.getSession();
		Object map = session.getAttribute(StaticsConstancts.PRIVILEDGES_VECTOR);
		if (null == map) {
			returnResult.setStatus(StatusCode.FAIL);
		} else {
			returnResult.setStatus(StatusCode.SUCCESS).setRows(map);
			logger.debug("getPriviledgeVector success");
		}
		return returnResult;
	}

	@Exclude
	@RequestMapping(value = "getUserAccount")
	public ReturnResult getUserAccount() throws UnsupportedEncodingException {
		HttpServletRequest request = ControllerContext.getRequest();
		ReturnResult returnResult = ControllerContext.getResult();
		String userAccount = CookieUtil.findCookie(StaticsConstancts.USER_ACCOUNT, request);
		if (StringUtils.isNotBlank(userAccount)) {
			returnResult.setStatus(StatusCode.SUCCESS).setRows(userAccount);
		} else {
			logger.debug("fail");
			returnResult.setStatus(StatusCode.FAIL);
		}

		return returnResult;
	}

	/**
	 * 生成验证码
	 * 
	 * @throws IOException
	 * 
	 */
	@Exclude
	@RequestMapping(value = "createVerifyCode")
	public void createVerifyCode() throws Exception {
		HttpSession session = ControllerContext.getSession();
		HttpServletResponse response = ControllerContext.getResponse();
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		ServletOutputStream stream = response.getOutputStream();
		Map<String, BufferedImage> map = VerifyCodeUtil.createImage();
		// 返回此映射中包含的键的 Set 视图。然后对其迭代
		String code = map.keySet().iterator().next();
		session.setAttribute(StaticsConstancts.VERIFY_CODE, code);
		// 获取图片对象
		BufferedImage image = map.get(code);
		stream.write(IOUtils.toByteArray(VerifyCodeUtil.getInputStream(image)));
		stream.flush();
		stream.close();
	}

}
