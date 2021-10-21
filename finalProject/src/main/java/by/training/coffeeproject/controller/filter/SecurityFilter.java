package by.training.coffeeproject.controller.filter;

import javax.servlet.annotation.WebFilter;
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

@WebFilter(urlPatterns = { "*.html" })
public class SecurityFilter implements Filter {
	private static final Logger LOG = LogManager.getLogger(SecurityFilter.class);

	public void destroy() {
	}

	private static final Set<String> notAuthorizedCommands = new HashSet<>();
	private static final Set<String> guestAllowedCommands = new HashSet<>();
	private static final Set<String> userAllowedCommands = new HashSet<>();
	private static final Set<String> onlyPostCommands = new HashSet<>();

	static {
		notAuthorizedCommands.add("/webCoffeeApp/jsp/authorization.html");
		notAuthorizedCommands.add("/webCoffeeApp/");
		notAuthorizedCommands.add("/webCoffeeApp/jsp/startPage.html");
		notAuthorizedCommands.add("/webCoffeeApp/jsp/guest.html");
		notAuthorizedCommands.add("/webCoffeeApp/jsp/result.html");
		notAuthorizedCommands.add("/webCoffeeApp/jsp/locale.html");

		guestAllowedCommands.addAll(notAuthorizedCommands);
		guestAllowedCommands.add("/webCoffeeApp/jsp/recipe/showRecipe.html");
		guestAllowedCommands.add("/webCoffeeApp/jsp/recipe/showAllRecipes.html");
		guestAllowedCommands.add("/webCoffeeApp/jsp/menu.html");
		guestAllowedCommands.add("/webCoffeeApp/jsp/logOut.html");
		guestAllowedCommands.add("/webCoffeeApp/jsp/user/showPublicUserInfo.html");

		userAllowedCommands.addAll(guestAllowedCommands);
		userAllowedCommands.add("/webCoffeeApp/jsp/recipe/createRecipeType.html");
		userAllowedCommands.add("/webCoffeeApp/jsp/recipe/createRecipeCoffee.html");
		userAllowedCommands.add("/webCoffeeApp/jsp/recipe/showAllCoffeeType.html");
		userAllowedCommands.add("/webCoffeeApp/jsp/user/showAllSavedRecipes.html");
		userAllowedCommands.add("/webCoffeeApp/jsp/recipe/createPouroverRecipe.html");
		userAllowedCommands.add("/webCoffeeApp/jsp/recipe/deleteNotCommonRecipe.html");
	
		onlyPostCommands.add("/webCoffeeApp/jsp/recipe/showAllCoffeeType.html");
		onlyPostCommands.add("/webCoffeeApp/jsp/result.html");
		onlyPostCommands.add("/webCoffeeApp/jsp/recipe/createRecipeCoffee.html");
		onlyPostCommands.add("/webCoffeeApp/jsp/recipe/createRecipeType.html");
		onlyPostCommands.add("/webCoffeeApp/jsp/guest.html");
		onlyPostCommands.add("/webCoffeeApp/jsp/recipe/createPouroverRecipe.html");
		onlyPostCommands.add("/webCoffeeApp/jsp/recipe/deleteNotCommonRecipe.html");

		


	}

	public void doFilter(ServletRequest requestServ, ServletResponse responseServ, FilterChain chain)
			throws IOException, ServletException {
		LOG.debug("start doFilter ");
		HttpServletRequest request = (HttpServletRequest) requestServ;
		HttpServletResponse response = (HttpServletResponse) responseServ;
		HttpSession session = request.getSession();
		String roleName = null;
		String uri = request.getRequestURI();
		LOG.debug("getRequestURI is " + uri);

		if (session != null) {
			roleName = (String) session.getAttribute("role");
			LOG.debug("roleName" + roleName + "end");
		}

		if (roleName == null) {
			LOG.debug("roleName == null");
			if (notAuthorizedCommands.contains(uri)) {
				checkForPostOrRedirect (request, uri, session, response, chain);
			} else {
				LOG.debug("redirect not Authorized Commands");
				session.setAttribute("securutyFilterMessage", "SecurutyrMessage.AuthCommand");
				response.sendRedirect(request.getContextPath() + "/jsp/startPage.html");
			}
		}

		if (roleName != null) {
			LOG.debug("her1e");
			if (roleName.equals("user")) {
				if (userAllowedCommands.contains(uri)) {
					checkForPostOrRedirect (request, uri, session, response, chain);

				} else {
					LOG.debug("redirect user Allowed Commands");
					session.setAttribute("securutyFilterMessage", "SecurutyrMessage.UserCommand");
					response.sendRedirect(request.getContextPath() + "/jsp/startPage.html");
				}
			}

			if (roleName.equals("guest")) {
				if (guestAllowedCommands.contains(uri)) {
					checkForPostOrRedirect (request, uri, session, response, chain);
				} else {
					LOG.debug("redirect guest Allowed Commands");
					session.setAttribute("securutyFilterMessage", "SecurutyrMessage.GuestCommand");
					response.sendRedirect(request.getContextPath() + "/jsp/startPage.html");
				}
			}
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	private boolean checkForPost(HttpServletRequest request, String uri, HttpSession session,
			HttpServletResponse response) throws IOException {
		if (onlyPostCommands.contains(uri) && (!request.getMethod().equals("POST"))) {
			LOG.debug("get " + request.getMethod());
			LOG.debug("redirect only Post Commands");

			session.setAttribute("securutyFilterMessage", "SecurutyrMessage.WrongMethod");
			LOG.debug("session");
			return false;
		} else {
			return true;

		}
	}

	private void checkForPostOrRedirect(HttpServletRequest request, String uri, HttpSession session,
			HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (checkForPost(request, uri, session, response)) {
			chain.doFilter(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/jsp/startPage.html");
		}
	}

}
