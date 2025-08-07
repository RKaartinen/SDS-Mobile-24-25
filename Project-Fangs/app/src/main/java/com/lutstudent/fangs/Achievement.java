package com.lutstudent.fangs;

public class Achievement {
    private String title;
    private String description;
    private boolean unlocked;

    public Achievement(String title, String description) {
        this.title = title;
        this.description = description;
        this.unlocked = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public boolean isUnlocked() {
        return unlocked;
    }

    public void unlock() {
        this.unlocked = true;
    }




}
