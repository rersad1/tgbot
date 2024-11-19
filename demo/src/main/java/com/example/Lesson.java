package com.example;

import java.util.List;

public class Lesson {
    private String teacher;
    private String second_teacher;
    private String subjectType;
    private String week;
    private String name;
    private String start_time;
    private String end_time;
    private int start_time_seconds;
    private int end_time_seconds;
    private String room;
    private String comment;
    private String form;
    private List<String> temp_changes;
    private String url;

    // Getters and setters for all fields
    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSecond_teacher() {
        return second_teacher;
    }

    public void setSecond_teacher(String second_teacher) {
        this.second_teacher = second_teacher;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getStart_time_seconds() {
        return start_time_seconds;
    }

    public void setStart_time_seconds(int start_time_seconds) {
        this.start_time_seconds = start_time_seconds;
    }

    public int getEnd_time_seconds() {
        return end_time_seconds;
    }

    public void setEnd_time_seconds(int end_time_seconds) {
        this.end_time_seconds = end_time_seconds;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public List<String> getTemp_changes() {
        return temp_changes;
    }

    public void setTemp_changes(List<String> temp_changes) {
        this.temp_changes = temp_changes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}