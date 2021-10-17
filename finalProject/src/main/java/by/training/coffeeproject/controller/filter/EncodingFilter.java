package by.training.coffeeproject.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.ControllerServlet;

public class EncodingFilter implements Filter {
	private static final Logger LOG = LogManager.getLogger(ControllerServlet.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOG.info ("start EncodingFilter ");
//		HttpServletResponse httpResponse = (HttpServletResponse) response;
//		httpResponse.setHeader("Cache-Control", "no-cache");
//		httpResponse.setHeader("Pragma", "no-cache");
//		httpResponse.setDateHeader("Expires", 0);

		request.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}


	
}
