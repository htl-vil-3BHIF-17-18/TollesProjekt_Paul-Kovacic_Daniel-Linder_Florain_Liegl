package bll;

import java.util.Date;

public class Task {
    private int id;
	private boolean done;
    private Categories category;
    private Subjects subject;
    private String description;
    private Date from;
    private Date until;

    public Task(boolean done, Categories category, Subjects subject, String description, Date from, Date until) {
        this.id = id;
    	this.done = done;
        this.category = category;
        this.subject = subject;
        this.description = description;
        this.from = from;
        this.until = until;
	}







	public int getId() { return id; }
    
    public boolean isDone() {
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

    public void setCategory(Categories category) {
        this.category = category;
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public void setUntil(Date until) {
        this.until = until;
    }

    @Override
	public String toString() {
		return (this.done ? "Y" : "N") + ", " + category + ", " + subject + ", " + description + ", " + from
				+ ", " + until;
	}
}
