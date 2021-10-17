package by.training.coffeeproject.dao;

public class DaoException extends Exception {

	private static final long serialVersionUID = 6633018500532350117L;

	public DaoException() {
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}
}
