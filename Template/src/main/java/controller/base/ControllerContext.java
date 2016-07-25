package controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControllerContext {

	private static ThreadLocal<HttpServletRequest> request_threadLocal = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> reponse_threadLocal = new ThreadLocal<HttpServletResponse>();
	private static ThreadLocal<ReturnResult> result_threadLocal = new ThreadLocal<ReturnResult>();

	public static void setRequest(HttpServletRequest request) {
		request_threadLocal.set(request);
	}

	public static HttpServletRequest getRequest() {
		return request_threadLocal.get();
	}

	public static void removeRequest() {
		request_threadLocal.remove();
	}

	public static HttpSession getSession() {
		return request_threadLocal.get().getSession();
	}

	public static void setReult(ReturnResult result) {
		result_threadLocal.set(result);
	}

	public static ReturnResult getResult() {
		ReturnResult result = result_threadLocal.get();
		if (result == null) {
			result = new ReturnResult();
			result_threadLocal.set(result);
		}
		return result;
	}

	public static void removeResult() {
		result_threadLocal.remove();
	}

	public static void setResponse(HttpServletResponse response) {
		reponse_threadLocal.set(response);
	}

	public static HttpServletResponse getResponse() {
		return reponse_threadLocal.get();
	}

	public static void removeResponse() {
		reponse_threadLocal.remove();
	}
}