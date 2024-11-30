package com.example;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Map;

import com.Parser.Day;
import com.Parser.GroupData;
import com.Parser.Lesson;

public class AnswerCreater {
    private LocalDate today;
    private LocalDate semStartDate;
    private String dayOfWeek;
    private long weeksBetween;
    private long currentWeek;

    // создание ответа для функций tommorow_lessons и week_lessons
    public String createStaticAnswer(GroupData groupData, String[] partsOfCommand, long weekNumber, StringBuilder answer) {  
        for (Map.Entry<String, Day> entry : groupData.getDays().entrySet()) {
            Day day = entry.getValue();
            if (day.getLessons().isEmpty()) {
                answer.append(day.getName().substring(0, 1))
                      .append(day.getName().substring(1).toLowerCase())
                      .append("\n\n")
                      .append("Выходной\n\n");
                continue;
            }
            answer = getDayLessons(groupData, day, weekNumber, answer);
        }
        return answer.toString();
    }

    // создание ответа для функции near_lesson
    public String getNearLesson(GroupData groupData, String command, StringBuilder answer) {
        setTodaySettings();
        ZonedDateTime timeNow = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
        int currentTime = timeNow.toLocalTime().toSecondOfDay();
        System.err.println(currentTime);
        boolean isReady = false;
        while (!isReady) { // пока не найдем подоходящий день
            for (Map.Entry<String, Day> entry : groupData.getDays().entrySet()) {
                Day day = entry.getValue();
                if (day.getLessons().isEmpty() || !day.getName().equals(dayOfWeek)) {
                    continue;
                }
                for (Lesson lesson : day.getLessons()) {
                    if (!lesson.getWeek().equals(String.valueOf(currentWeek))) {
                        continue;
                    }
                    else if (lesson.getForm().equals("distant") || lesson.getForm().equals("online")) {
                        continue;
                    }
                    if (lesson.getStart_time_seconds() < currentTime && currentTime < lesson.getEnd_time_seconds()) {
                        answer.append(day.getName().substring(0, 1))
                              .append(day.getName().substring(1).toLowerCase())
                              .append(" ")
                              .append(today)
                              .append("\n\n")
                              .append("Cейчас идет: ")
                              .append(" ")
                              .append(lesson.getSubjectType())
                              .append(" ")
                              .append(lesson.getName());
                        if (lesson.getTeacher().isEmpty()) {
                            answer.append("\n\n");
                            continue;
                        }
                        answer.append(", ")
                              .append(lesson.getTeacher());
                        if (lesson.getForm().equals("distant")) {
                            answer.append(", дистанционно");
                        } 
                        else {
                            answer.append(", ")
                                  .append(lesson.getRoom());
                        }
                        answer.append("\n\n");
                    }
                    else if (lesson.getStart_time_seconds() > currentTime) {
                        isReady = true;
                        answer.append(day.getName().substring(0, 1))
                              .append(day.getName().substring(1).toLowerCase())
                              .append(" ")
                              .append(today)
                              .append("\n\n")
                              .append("Следующее занятие: ")
                              .append(lesson.getStart_time())
                              .append(" ")
                              .append(lesson.getSubjectType())
                              .append(" ")
                              .append(lesson.getName());
                        if (!lesson.getTeacher().isEmpty()) {
                            answer.append(", ")
                            .append(lesson.getTeacher());
                        }
                        answer.append(", ")
                              .append(lesson.getRoom());
                        answer.append("\n\n");
                        break;
                    }
                }
                if (isReady) {
                    break;
                }
            }

            if (!isReady) {
                switchDay();
                currentTime = 0;
            }
            else if (weeksBetween > 52) {
                break;
            }
        }
        return answer.toString();
    }

    // создание ответа для функции tommorow_lessons
    public String getNextDayLessons(GroupData groupData, String command, StringBuilder answer) {
        setTodaySettings();
        switchDay();
        boolean isReady = false;
        while (true) {
            for (Map.Entry<String, Day> entry : groupData.getDays().entrySet()) {
                Day day = entry.getValue();
                if (day.getLessons().isEmpty() || !day.getName().equals(dayOfWeek)) {
                    continue;
                }
                answer = getDayLessons(groupData, day, currentWeek, answer);
                isReady = true;
            }    

            if (isReady) {
                break;
            }

            switchDay();
        }
        return answer.toString();
    }

    // получение все пар за день в хорошем виде
    private StringBuilder getDayLessons(GroupData groupData, Day day, long currentWeek, StringBuilder answer) {
        answer.append(day.getName().substring(0, 1))
              .append(day.getName().substring(1).toLowerCase())
              .append("\n\n");
        for (Lesson lesson : day.getLessons()) {
            if (lesson.getWeek().equals(String.valueOf(currentWeek))) {
                answer.append(lesson.getStart_time())
                      .append(" ")
                      .append(lesson.getSubjectType())
                      .append(" ")
                      .append(lesson.getName());
                if (lesson.getTeacher().isEmpty()) {
                    answer.append("\n\n");
                    continue;
                }
                answer.append(", ")
                      .append(lesson.getTeacher());
                if (lesson.getForm().equals("distant")) {
                    answer.append(", дистанционно");
                } else {
                    answer.append(", ")
                          .append(lesson.getRoom());
                }
                answer.append("\n\n");
            }
        }
        return answer;
    }
    
    // получение даты начала семестра
    private void setSemStartDate(LocalDate today) {
        if (today.getMonthValue() < 2) {
            semStartDate = LocalDate.of(today.getYear() - 1, 9, 1);
        } 
        else if (today.getMonthValue() > 9) {
            semStartDate = LocalDate.of(today.getYear(), 9, 1);
        } 
        else {
            semStartDate = LocalDate.of(today.getYear(), 2, 3);
        }
    }

    // переключение на следующий день
    private void switchDay() {
        today = today.plusDays(1);
        dayOfWeek = today.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("ru")).toUpperCase();
        weeksBetween = ChronoUnit.WEEKS.between(semStartDate, today);
        currentWeek = weeksBetween % 2 + 1;
    }

    // установка настроек для текущего дня
    private void setTodaySettings() {
        today = LocalDate.now();
        setSemStartDate(today);
        dayOfWeek = today.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("ru")).toUpperCase();
        weeksBetween = ChronoUnit.WEEKS.between(semStartDate, today);
        currentWeek = weeksBetween % 2 + 1;
    }
}