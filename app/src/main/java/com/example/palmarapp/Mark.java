package com.example.palmarapp;

public class Mark {
    private String subject;
    private String mark;
    private String maxMark;

    // Constructor
    public Mark(String subject, String mark, String maxMark) {
        this.subject = subject;
        this.mark = mark;
        this.maxMark = maxMark;
    }

    // Getters and setters
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getMaxMark() {
        return maxMark;
    }

    public void setMaxMark(String maxMark) {
        this.maxMark = maxMark;
    }
}
