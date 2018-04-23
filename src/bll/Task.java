package bll;

import java.util.Date;

public class Task {
	private boolean done;
    private Categories category;
    private Subjects subject;
    private String description;
    private Date from;
    private Date until;

    public Task(Categories category, Subjects subject, String description, Date from, Date until) {
    	this.done = false;
        this.category = category;
        this.subject = subject;
        this.description = description;
        this.from = from;
        this.until = until;
    }

    public Task(boolean done, Categories category, Subjects subject, String description, Date from, Date until) {
    	this.done = done;
        this.category = category;
        this.subject = subject;
        this.description = description;
        this.from = from;
        this.until = until;
	}
    
    public boolean getDone() {
    	return done;
    }

	public Categories getCategorie() {
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

	@Override
	public String toString() {
		return (this.done ? "Y" : "N") + ", " + category + ", " + subject + ", " + description + ", " + from
				+ ", " + until;
	}
}
