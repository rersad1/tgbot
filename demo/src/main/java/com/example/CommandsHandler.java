package com.example;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

public class CommandsHandler {
    private String command;
    private String[] partsOfCommand;
    private String url;
    RequestCreator requestCreator = new RequestCreator();

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
                url = "https://digital.etu.ru/api/mobile/schedule?weekDay=" + partsOfCommand[1] + "&subjectType=&groupNumber=" + partsOfCommand[3] + "&joinWeeks=false";// Переделать под отправку в канал
                break;

            default:
                url = "https://digital.etu.ru/api/mobile/schedule?weekDay=&subjectType=&groupNumber=" + partsOfCommand[1] + "&joinWeeks=false";
                break;
        }
        requestCreator.searchResult(url);
    } 

}