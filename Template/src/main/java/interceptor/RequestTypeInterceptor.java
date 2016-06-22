package interceptor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;

import controller.base.ControllerHelper;

/**
 * 请求类型拦截器，通过请求方法的类型（GET OR POST）判断该请求是合法请求还是非法请求
 * 
 * @author Venson Yang
 */
public class RequestTypeInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(RequestTypeInterceptor.class);
	private static final Set<String> methodStartName = new HashSet<String>();
	static {
		methodStartName.add("show");
		methodStartName.add("add");
		methodStartName.add("modify");
		methodStartName.add("delete");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		MethodNameResolver methodNameResolver = new InternalPathMethodNameResolver();
		String methodName = methodNameResolver.getHandlerMethodName(request);
		String requestType = request.getMethod();
		logger.debug("methodName:" + methodName + ",requestType:" + requestType);
		for (String startName : methodStartName) {
			if (methodName.startsWith(startName)) {
				if (requestType.equals("GET"))
					return ControllerHelper.renderJSON("非法用户,禁止访问", logger);

			}
		}
		return true;
	}

}