package by.training.coffeeproject.service.taglogic;

import java.io.IOException;
import java.util.regex.Pattern;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.coffeeproject.service.ServiceException;
import by.training.coffeeproject.service.ServiceFactory;
import by.training.coffeeproject.service.UserInfoService;

/**
 * 
 * Logic for UserNameTag. Take user.ID and return its name
 *
 */
public class UserNameTag extends BodyTagSupport {

	private static final long serialVersionUID = -5619562297023924334L;

	private static final Logger LOG = LogManager.getLogger(UserNameTag.class);

	private static final String ID_PATTERN = "([0-9]+)";

	@Override
	public int doAfterBody() throws JspException {
		BodyContent content = this.getBodyContent();
		String body = content.getString();
		LOG.debug("body " + body);
		String name = "";
		if (Pattern.matches(ID_PATTERN, body)) {
			Integer ID = Integer.valueOf(body);
			ServiceFactory fct = ServiceFactory.getInstance();
			UserInfoService srv = fct.getUserInfoService();
			try {
				name = srv.takeUserNameByID(ID);
			} catch (ServiceException e) {
				LOG.debug("wrong ID " + e.getMessage());
			}
		} else {
			LOG.debug("wrong ID ");
			name = "";
		}
		JspWriter out = content.getEnclosingWriter();
		try {
			out.write(name);
		} catch (IOException e) {
			LOG.error(e.getMessage());
			throw new JspTagException(e.getMessage());
		}
		return SKIP_BODY;
	}
}
