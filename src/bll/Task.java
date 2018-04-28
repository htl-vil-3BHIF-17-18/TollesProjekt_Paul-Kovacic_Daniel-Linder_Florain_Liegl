package bll;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
	private boolean done;
	private Categories category;
	private Subjects subject;
	private String description;
	private Date from;
	private Date until;

	public Task(boolean done, Categories category, Subjects subject, String description, Date from, Date until) {
		this.done = done;
		this.category = category;
		this.subject = subject;
		this.description = description;
		this.from = from;
		this.until = until;
	}

	public boolean isDone() {
		return done;
	}

	public Categories getCategory() {
		return category;
	}

	public Subjects getSubject() {
		return subject;
	}

	public String getDescription() {
		return description;
	}

	public Date getFrom() {
		return from;
	}

	public Date getUntil() {
		return until;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public String toString() {
		return (this.done ? "Y" : "N") + ", " + category + ", " + subject + ", " + description + ", " + from + ", "
				+ until;
	}
}
