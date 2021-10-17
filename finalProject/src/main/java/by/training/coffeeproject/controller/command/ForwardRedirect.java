package by.training.coffeeproject.controller.command;

import java.util.Objects;

/**
 * 
 * @author AlexeySupruniuk
 *
 * Contains address to response page and type of response (forward or redirect)
 */
public class ForwardRedirect {
	private String page;
	private boolean isRedirect;

	public ForwardRedirect() {
		super();
	}

	public ForwardRedirect(String page, boolean isRedirect) {
		super();
		this.page = page;
		this.isRedirect = isRedirect;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

	@Override
	public int hashCode() {
		return Objects.hash(isRedirect, page);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ForwardRedirect other = (ForwardRedirect) obj;
		return isRedirect == other.isRedirect && Objects.equals(page, other.page);
	}

	@Override
	public String toString() {
		return "ForwardRedirect [page=" + page + ", isRedirect=" + isRedirect + "]";
	}

}
