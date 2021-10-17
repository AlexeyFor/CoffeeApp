package by.training.coffeeproject.service;

import java.io.Serializable;

/**
 * 
 * @author AlexeySupruniuk
 * 
 *
 */
public class ServiceException extends Exception implements Serializable {

    private static final long serialVersionUID = -1678478441294921606L;

    public ServiceException() {
	super();
    }

    public ServiceException(String message) {
	super(message);
    }

    public ServiceException(Exception e) {
	super(e);
    }

    public ServiceException(String message, Exception e) {
	super(message, e);
    }

}
