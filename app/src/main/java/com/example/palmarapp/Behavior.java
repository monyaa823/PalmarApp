package com.example.palmarapp;

public class Behavior {
    private String behaviorDate;
    private String description;

    // Constructor
    public Behavior(String behaviorDate, String description) {
        this.behaviorDate = behaviorDate;
        this.description = description;
    }

    // Getters and setters
    public String getBehaviorDate() {
        return behaviorDate;
    }

    public void setBehaviorDate(String behaviorDate) {
        this.behaviorDate = behaviorDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
