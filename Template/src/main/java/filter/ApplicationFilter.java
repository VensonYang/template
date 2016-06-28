package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.base.ControllerContext;

public class ApplicationFilter implements Filter {

	public static final String ENCODING_PARAM = "encoding";

	private String encoding;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 统一全站字符编码
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		// 将request,response保存到当前线程中去
		ControllerContext.setRequest((HttpServletRequest) request);
		ControllerContext.setResponse((HttpServletResponse) response);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.encoding = filterConfig.getInitParameter(ENCODING_PARAM);
	}

}