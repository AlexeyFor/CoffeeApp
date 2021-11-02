package by.training.coffeeproject.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.controller.ControllerServlet;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         set utf-8 for every request
 *
 */
public class EncodingFilter implements Filter {
	private static final Logger LOG = LogManager.getLogger(ControllerServlet.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOG.info("start EncodingFilter ");

		request.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}

}
