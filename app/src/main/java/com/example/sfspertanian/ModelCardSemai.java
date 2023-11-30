package com.example.sfspertanian;
public class ModelCardSemai {
    private String title;
    private String subtitle;
    private String time;
    private String date;

    public ModelCardSemai(String title, String subtitle, String time, String date) {
        this.title = title;
        this.subtitle = subtitle;
        this.time = time;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }
}
