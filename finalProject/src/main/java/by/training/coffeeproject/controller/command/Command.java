package by.training.coffeeproject.controller.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {
	ForwardRedirect execute(HttpServletRequest request);
}
