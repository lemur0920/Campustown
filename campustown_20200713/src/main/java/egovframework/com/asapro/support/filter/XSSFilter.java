/**
 * 
 */
package egovframework.com.asapro.support.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import egovframework.com.asapro.support.Constant;


/**
 * XSS 필터
 * @author yckim
 * @since 2018. 8. 31.
 */
public class XSSFilter implements Filter{
	
	private FilterConfig filterConfig = null;

	@Override
	public void destroy() {
		this.filterConfig = null;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		//관리자 패스일 경우 XSS필터 패스시킨다
		if( request.getRequestURI().contains(Constant.ADMIN_PATH) ){
			chain.doFilter(servletRequest, servletResponse);
		} else {
			chain.doFilter(new XSSRequestWrapper(request), servletResponse);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

}
