package interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;

import common.StaticsConstancts;
import controller.base.ControllerContext;
import controller.base.ControllerHelper;
import model.system.PrivilegesVectorVO;

/**
 * 权限拦截器，通过前台请求参数{@linkplain #PRIVILEGES_ID privilegesID}判断该请求是合法请求还是非法请求
 * 
 * @author Venson Yang
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter {
	private static final String PRIVILEGES_ID = "privilegesID";
	private static final Map<String, String> methodStartName = new HashMap<String, String>();
	static {
		methodStartName.put("add", "iscreate");
		methodStartName.put("modify", "ismodify");
		methodStartName.put("delete", "isdelete");
		methodStartName.put("import", "isimport");
		methodStartName.put("export", "isexport");
	}
	private static final Logger logger = LoggerFactory.getLogger(AuthorityInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// 判断是否是排除验证的方法
		MethodNameResolver methodNameResolver = new InternalPathMethodNameResolver();
		String methodName = methodNameResolver.getHandlerMethodName(request);

		Method method = handlerMethod.getMethod();
		logger.debug("methodName:" + methodName);

		Exclude annotation = method.getAnnotation(Exclude.class);
		if (annotation != null)
			return true;

		// 判断请求是否存在权限ID参数且ID是否为有效数字
		String privilegeId = request.getParameter(PRIVILEGES_ID);
		if (!StringUtils.isNumeric(privilegeId))
			return ControllerHelper.renderJSON("权限ID丢失或有误，禁止访问", logger);

		// 获取session中的权限
		@SuppressWarnings("unchecked")
		Map<Integer, PrivilegesVectorVO> privilegesVectors = (Map<Integer, PrivilegesVectorVO>) request.getSession()
				.getAttribute(StaticsConstancts.PRIVILEGES_VECTOR);
		if (privilegesVectors == null)
			return ControllerHelper.renderJSON("用户未登录，禁止访问", logger);

		// 查看该用户是否存在该权限ID
		Set<Integer> privilegeIdSet = privilegesVectors.keySet();
		if (!privilegeIdSet.contains(Integer.valueOf(privilegeId)))
			return ControllerHelper.renderJSON("非法权限ID，禁止访问", logger);

		// 查看该请求对应的方法是否为增，删，改，导入，导出，如果是则进行权限矩阵判断
		Map<String, Boolean> privilegesMatrix = ((PrivilegesVectorVO) privilegesVectors
				.get(Integer.valueOf(privilegeId))).getPrivilegeMatrix();
		for (Entry<String, String> entry : methodStartName.entrySet()) {
			if (methodName.startsWith(entry.getKey())) {
				if (privilegesMatrix.get(entry.getValue()))
					return true;
				else
					return ControllerHelper
							.renderJSON(ControllerHelper.getMessage("messages.authority." + entry.getKey()), logger);
			}
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		ControllerContext.removeRequest();
		ControllerContext.removeResponse();
		ControllerContext.removeResult();
		super.afterCompletion(request, response, handler, ex);
	}

}