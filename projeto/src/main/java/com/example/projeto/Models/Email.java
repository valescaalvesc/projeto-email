package com.example.projeto.Models;

import java.util.Date;

public class Email {
    private String from;
    private Date receiveDate;
    private String subject;
    private String body;

    public Email() {
    }

    public Email(String from, Date receiveDate, String subject, String body) {
        this.from = from;
        this.receiveDate = receiveDate;
        this.subject = subject;
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("From: ").append(this.from).append("\n");
        sb.append("Date: ").append(this.receiveDate).append("\n");
        sb.append("Subject: ").append(this.subject).append("\n\n");
        sb.append(this.body);
        return sb.toString();
    }
}
