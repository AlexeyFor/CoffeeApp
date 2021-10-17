package by.training.coffeeproject.entity;

import java.util.Date;
import java.util.Objects;

public class Comment extends Entity {

	private static final long serialVersionUID = 1693459484045200447L;

	private String text;
	private int authorUserId;
	private Date dateOfCreating;

	public Comment(Integer iD, String text, int authorUserId, Date dateOfCreating) {
		super(iD);
		this.text = text;
		this.authorUserId = authorUserId;
		this.dateOfCreating = dateOfCreating;
	}

	public Comment() {
		super();
	}

	public Comment(Integer iD) {
		super(iD);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getAuthorUserId() {
		return authorUserId;
	}

	public void setAuthorUserId(int authorUserId) {
		this.authorUserId = authorUserId;
	}

	public Date getDateOfCreating() {
		return dateOfCreating;
	}

	public void setDateOfCreating(Date dateOfCreating) {
		this.dateOfCreating = dateOfCreating;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(authorUserId, dateOfCreating, text);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		return authorUserId == other.authorUserId && Objects.equals(dateOfCreating, other.dateOfCreating)
				&& Objects.equals(text, other.text);
	}

	@Override
	public String toString() {
		return "Comment [text=" + text + ", authorUserId=" + authorUserId + ", dateOfCreating=" + dateOfCreating + "]";
	}

}
