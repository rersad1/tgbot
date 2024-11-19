package com.example;

import java.util.Map;

public class GroupData {
    private String group;
    private Map<String, Day> days;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Map<String, Day> getDays() {
        return days;
    }

    public void setDays(Map<String, Day> days) {
        this.days = days;
    }
}