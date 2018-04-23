package bll;

import java.util.Date;

public class Task {
	//TODO: private boolean done;
    private Categories category;
    private Subjects subject;
    private String description;
    private Date from;
    private Date until;

    public Task(Categories category, Subjects subject, String description, Date from, Date until) {
    	//TODO: this.done = false;
        this.category = category;
        this.subject = subject;
        this.description = description;
        this.from = from;
        this.until = until;
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
}
