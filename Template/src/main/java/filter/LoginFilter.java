package filter;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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

public class LoginFilter implements Filter {

	public static final String ENCODING_PARAM = "encoding";
	public static final String IGNORE_PARAM = "ignore";
	public static final String PERMIT_URL_PARAM = "permitUrls";

	private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

	private UserService userService;
	private boolean ignore = false;
	private String encoding;
	public static final String permitUrls[] = { "/get/createVerifyCode", "/get/getUserAccount", "/user/login" };

	public void destroy() {
		ignore = false;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		/**
		 * 服务端跨域设置
		 */
		// HttpServletResponse rep = (HttpServletResponse) response;
		// rep.addHeader("Access-Control-Allow-Origin",
		// "http://localhost:8080");
		// rep.addHeader("Access-Control-Allow-Credentials", "true");
		/**
		 * 如果是静态资源则不进行拦截
		 */
		// HttpServletRequest req = (HttpServletRequest) request;
		// String reqURI = req.getRequestURI();
		// logger.debug(reqURI);
		// if (reqURI.lastIndexOf(".") != -1) {
		// chain.doFilter(request, response);
		// return;
		// }
		// 统一全站字符编码
		logger.debug("进入站点。。。");
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		// 将request,response保存到当前线程中去
		ControllerContext.setRequest((HttpServletRequest) request);
		ControllerContext.setResponse((HttpServletResponse) response);

		if (ignore == false) {
			if (isPermitUrl(request) == false) {
				if (filterCurrUrl(request)) {
					response.setContentType("application/json");
					ReturnResult returnResult = ControllerContext.getResult();
					returnResult.setStatus(StatusCode.NO_ACCESS.setMessage("非法用户,禁止访问"));
					logger.debug("非法用户,禁止访问");
					response.getWriter().print(JSONObject.toJSON(returnResult));
					return;
				}
			}

		}

		chain.doFilter(request, response);
	}

	/**
	 * 检查是否属于允许URL
	 */
	public boolean isPermitUrl(ServletRequest request) {
		boolean isPermit = false;
		String currentURL = currentUrl(request);
		String[] rootUrls = currentURL.split("/");
		String rooturl = "";
		if (rootUrls.length > 1) {
			rooturl = "/" + rootUrls[1];
		}

		if (permitUrls != null && permitUrls.length > 0) {
			for (int i = 0; i < permitUrls.length; i++) {
				if (permitUrls[i].equals(currentURL) || permitUrls[i].equals(rooturl)) {
					isPermit = true;
					logger.debug("rooturl=" + rooturl);
					break;
				}
			}

		}
		return isPermit;
	}

	/**
	 * 检查当前URL有无登录，并且判断cookie中是否有存在用户免登陆记录。
	 * 
	 * @throws IOException
	 */
	public boolean filterCurrUrl(ServletRequest request) throws IOException {
		HttpServletRequest res = (HttpServletRequest) request;
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

	/**
	 * 获取当前请求URL
	 */
	public String currentUrl(ServletRequest request) {

		HttpServletRequest res = (HttpServletRequest) request;
		String path = res.getContextPath();
		String uri = res.getRequestURI();
		uri = uri.substring(path.length(), uri.length());
		logger.debug("当前请求地址:" + uri);
		return uri;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		ServletContext context = filterConfig.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		userService = (UserService) ctx.getBean("userService");
		String ignore = filterConfig.getInitParameter(IGNORE_PARAM);
		this.encoding = filterConfig.getInitParameter(ENCODING_PARAM);
		if (Boolean.parseBoolean(ignore)) {
			this.ignore = true;
		}
	}

}