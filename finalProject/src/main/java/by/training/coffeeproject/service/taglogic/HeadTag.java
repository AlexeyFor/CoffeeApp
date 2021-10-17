package by.training.coffeeproject.service.taglogic;

import java.io.IOException;
import java.util.Locale;
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

public class HeadTag extends BodyTagSupport {
	private static final Logger LOG = LogManager.getLogger(UserNameTag.class);

	@Override
	public int doAfterBody() throws JspException {

//		Locale.setDefault(new Locale("ru", "RU"));
		return SKIP_BODY;
	}
}
