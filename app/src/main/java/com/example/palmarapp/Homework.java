package com.example.palmarapp;

public class Homework {
    private String subject;
    private String title;
    private String description;
    private String dueDate;
    private String assignedDate;
    private int homeworkId;

    // Constructor
    public Homework(String subject, String title, String description, String dueDate, String assignedDate, int homeworkId) {
        this.subject = subject;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.assignedDate = assignedDate;
        this.homeworkId = homeworkId;
    }


    // Getters and Setters
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }

    public int getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
    }
}
