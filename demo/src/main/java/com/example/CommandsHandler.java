package com.example;

import java.util.Map;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import java.time.*;

public class CommandsHandler {
    private String command;
    private String[] partsOfCommand;
    private String url;
    private String answer;
    RequestCreator requestCreator = new RequestCreator();
    Parser parser = new Parser();
    public void parseCommand(String commandText) {
        int firstSpaceIndex = commandText.indexOf(' ');
        if (firstSpaceIndex != -1) {
            this.command = commandText.substring(0, firstSpaceIndex);
        } 
        else {
            this.command = commandText; // Если нет пробелов, вся строка является командой
        }
        // Удаление последнего элемента из строки перед разбиением
        int lastSpaceIndex = commandText.lastIndexOf(' ');
        if (lastSpaceIndex != -1) {
            this.partsOfCommand = commandText.substring(0, lastSpaceIndex).split(" ");
        } 
        else {
            this.partsOfCommand = new String[0]; // Если нет пробелов, массив будет пустым
        }
    }

    public String getCommand() {
        return this.command;
    }

    public String parseReportText(String reportText) {
        if ("REPORT".equals(this.command)) {
            return reportText.substring(7, reportText.length() - 4); // Удаление "REPORT " и " END" из строки
        }
        return "";
    }

    public void handleCommand(TelegramBot bot, String commandText, long chatId) {
        parseCommand(commandText);
        
        switch (this.command) {

            case "REPORT":
                String report = parseReportText(commandText);
                bot.execute(new SendMessage(MyBot.logsChannel, report));
                break;

            case "day_lessons":
                url = "https://digital.etu.ru/api/mobile/schedule?weekDay=" + partsOfCommand[1] + "&subjectType=&groupNumber=" + partsOfCommand[partsOfCommand.length - 1] + "&joinWeeks=false";// Переделать под отправку в канал
                break;

            default:
                url = "https://digital.etu.ru/api/mobile/schedule?weekDay=&subjectType=&groupNumber=" + partsOfCommand[partsOfCommand.length - 1] + "&joinWeeks=false";
                break;
        }
        String response = requestCreator.searchResult(url);
        GroupData groupData = parser.parse(response, partsOfCommand[partsOfCommand.length - 1]);
        switch (this.command) {
            case "day_lessons":
                answer = createStaticAnswer(groupData, partsOfCommand, 2);
                break;
        
            case "week_lessons":
                answer = createStaticAnswer(groupData, partsOfCommand, 1);
                break;
        }
        
        bot.execute(new SendMessage(chatId, answer));

    } 

    private String createStaticAnswer(GroupData groupData, String[] partsOfCommand, int indexOfWeek) {
        StringBuilder answer = new StringBuilder();
        for (Map.Entry<String, Day> entry : groupData.getDays().entrySet()) {
            Day day = entry.getValue();
            if (day.getName().equals("ВОСКРЕСЕНЬЕ")) {
                continue;
            }
            answer.append(day.getName().substring(0, 1))
                  .append(day.getName().substring(1).toLowerCase())
                  .append("\n\n");
            for (Lesson lesson : day.getLessons()) {
                String week = lesson.getWeek();
                if (week.equals(partsOfCommand[indexOfWeek])) {
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
        }
        return answer.toString();
    }

    private String createDynamicAnswer(GroupData groupData) {
        StringBuilder answer = new StringBuilder();
        LocalDate today = LocalDate.now();
        
    }

}