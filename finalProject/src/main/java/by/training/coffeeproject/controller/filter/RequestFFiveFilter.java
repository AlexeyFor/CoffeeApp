package by.training.coffeeproject.controller.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *         For defending from f5. On each page, a random number is put into the
 *         session (requestIdentity) , this number is matched in this filter and
 *         the previous one (lastRequestIdentity), if they are equal - error and
 *         redirect to startPage
 *
 */
//@WebFilter(urlPatterns = { "*.html" })
public class RequestFFiveFilter implements Filter {
	private static final Logger LOG = LogManager.getLogger(SecurityFilter.class);
	private static final String REQUESTIDENTITY = "requestIdentity";
	private static final String LASTREQUESTIDENTITY = "lastRequestIdentity";
//	private static final Set<String> checkedCommands = new HashSet<>();
//
//	static {
//		checkedCommands.add("/webCoffeeApp/jsp/recipe/showAllCoffeeType.html");
//		checkedCommands.add("/webCoffeeApp/jsp/recipe/showAllCoffeeTypeEdit.html");
//		checkedCommands.add("/webCoffeeApp/jsp/recipe/createRecipeStep1Coffee.html");
//		checkedCommands.add("/webCoffeeApp/jsp/recipe/createRecipeStep2.html");
//		checkedCommands.add("/webCoffeeApp/jsp/recipe/createSaveRecipePourover.html");
//		checkedCommands.add("/webCoffeeApp/jsp/result.html");
//		checkedCommands.add("/webCoffeeApp/jsp/guest.html");
//		checkedCommands.add("/webCoffeeApp/jsp/recipe/saveCommonRecipe.html");
//		checkedCommands.add("/webCoffeeApp/jsp/recipe/deleteNotCommonRecipe.html");
//		checkedCommands.add("/webCoffeeApp/jsp/recipe/deleteSavedCommonRecipe.html");
//		checkedCommands.add("/webCoffeeApp/jsp/recipe/editRecipeStep1Coffee.html");
//		checkedCommands.add("/webCoffeeApp/jsp/recipe/editRecipeStep2.html");
//		checkedCommands.add("/webCoffeeApp/jsp/recipe/editSaveChangesRecipePourover.html");
//	}
//
//	public void destroy() {
//	}

	public void doFilter(ServletRequest requestServ, ServletResponse responseServ, FilterChain chain)
			throws IOException, ServletException {
		LOG.debug("start doFilter ");
		HttpServletRequest request = (HttpServletRequest) requestServ;
		HttpServletResponse response = (HttpServletResponse) responseServ;
		HttpSession session = request.getSession();
//		String uri = request.getRequestURI();

//		if (checkedCommands.contains(uri)) {
		if (session != null) {
			Integer requestIdentity = (Integer) session.getAttribute(REQUESTIDENTITY);
			Integer lastRequestIdentity = (Integer) session.getAttribute(LASTREQUESTIDENTITY);
//			LOG.debug("get requestIdentity " + requestIdentity);
//			LOG.debug("get lastRequestIdentity " + lastRequestIdentity);

			if (requestIdentity != null && lastRequestIdentity != null) {
				if (lastRequestIdentity.equals(requestIdentity)) {
					session.setAttribute("securutyFilterMessage", "SecurutyrMessage.FFive");
					session.setAttribute(LASTREQUESTIDENTITY, -1);
					session.setAttribute(REQUESTIDENTITY, -5);
					response.sendRedirect(request.getContextPath() + "/jsp/startPage.html");
				} else {
					session.setAttribute(LASTREQUESTIDENTITY, requestIdentity);
					session.setAttribute(REQUESTIDENTITY, -2);
					chain.doFilter(request, response);
				}
			} else {
				session.setAttribute(LASTREQUESTIDENTITY, -3);
				session.setAttribute(REQUESTIDENTITY, -4);
				chain.doFilter(request, response);
			}
		}
//		}else {
//			chain.doFilter(request, response);
//		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
