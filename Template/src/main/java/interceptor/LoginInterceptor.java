package interceptor;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;

import common.StaticsConstancts;
import controller.base.ControllerContext;
import controller.base.ReturnResult;
import controller.base.StatusCode;
import model.system.PriviledgesVectorVO;
import service.system.UserService;
import utils.common.CookieUtil;
import utils.common.MD5Util;
import utils.common.NetworkUtil;

/**
 * 用户登录拦截器，
 * 
 * @author Venson Yang
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getContextPath();
		String uri = request.getRequestURI();
		uri = uri.substring(path.length(), uri.length());
		logger.debug("当前请求地址:" + uri);
		if (filterCurrUrl(request)) {
			response.setContentType("application/json");
			ReturnResult returnResult = ControllerContext.getResult();
			returnResult.setStatus(StatusCode.FAIL.setMessage("您还未登录,请先登录！"));
			logger.debug("非法用户,禁止访问");
			response.getWriter().print(JSONObject.toJSON(returnResult));
			return false;
		}
		return true;
	}

	/**
	 * 检查是否属于允许URL
	 * 
	 * private boolean isPermitUrl(ServletRequest request) { boolean isPermit =
	 * false; String currentURL = currentUrl(request); String[] rootUrls =
	 * currentURL.split("/"); String rooturl = ""; if (rootUrls.length > 1) {
	 * rooturl = "/" + rootUrls[1]; } if (PERMIT_URL != null &&
	 * PERMIT_URL.length > 0) { for (int i = 0; i < PERMIT_URL.length; i++) { if
	 * (PERMIT_URL[i].equals(currentURL) || PERMIT_URL[i].equals(rooturl)) {
	 * isPermit = true; logger.debug("rooturl=" + rooturl); break; } }
	 * 
	 * } return isPermit; }
	 */
	/**
	 * 检查当前URL有无登录，并且判断cookie中是否有存在用户免登陆记录。
	 * 
	 * @throws IOException
	 */
	private boolean filterCurrUrl(HttpServletRequest res) throws IOException {
		HttpSession session = res.getSession();
		if (null == session.getAttribute(StaticsConstancts.USER_INFO)) {
			StringBuffer salt = new StringBuffer();
			salt.append(Calendar.getInstance().get(1));
			salt.append(Calendar.getInstance().get(3));
			String cookie_account = CookieUtil.findCookie(StaticsConstancts.USER_ACCOUNT, res);
			String cookie_secret_key = CookieUtil.findCookie(StaticsConstancts.SECRET_KEY, res);

			if (StringUtils.isNotBlank(cookie_account) && StringUtils.isNotBlank(cookie_secret_key)
					&& MD5Util.getMD5StringWithSalt(cookie_account, salt.toString()).equals(cookie_secret_key)) {
				Map<String, Object> userMap = userService.getUserByAccount(cookie_account);
				Integer userId = (Integer) userMap.get(StaticsConstancts.USER_ID);
				Map<Integer, PriviledgesVectorVO> priviledgesVector = userService
						.getPriviledgesVectors((Integer) userMap.get(StaticsConstancts.USER_ID));
				userMap.put(StaticsConstancts.USER_IP_ADDRESS, NetworkUtil.getIpAddress(res));
				userMap.put(StaticsConstancts.USER_ROLE, userService.getUserRoleByUserId(userId));
				session.setAttribute(StaticsConstancts.USER_NAME, userMap.get(StaticsConstancts.USER_NAME));
				session.setAttribute(StaticsConstancts.USER_ACCOUNT, userMap.get(StaticsConstancts.USER_ACCOUNT));
				session.setAttribute(StaticsConstancts.USER_ID, userId);
				session.setAttribute(StaticsConstancts.USER_INFO, userMap);
				session.setAttribute(StaticsConstancts.PRIVILEDGES_VECTOR, priviledgesVector);
				logger.info(
						"账号：[" + cookie_account + "]IP：[" + userMap.get(StaticsConstancts.USER_IP_ADDRESS) + "]免密登录");
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

}