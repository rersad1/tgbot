package com.example;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

public class CommandsHandler {

    AnswerCreater answerCreater = new AnswerCreater();

    private String command;
    private String[] partsOfCommand;
    private String url;
    private String answer;
    private long weekNumber;
    StringBuilder futureAnswer = new StringBuilder();
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
                bot.execute(new SendMessage(chatId, "Репорт успешно отправлен"));
                return;

            case "day_lessons":
                url = "https://digital.etu.ru/api/mobile/schedule?weekDay=" + partsOfCommand[1] + "&subjectType=&groupNumber=" + partsOfCommand[partsOfCommand.length - 1] + "&joinWeeks=false";// Переделать под отправку в канал
                break;

            default:
                url = "https://digital.etu.ru/api/mobile/schedule?weekDay=&subjectType=&groupNumber=" + partsOfCommand[partsOfCommand.length - 1] + "&joinWeeks=false";
                break;
        }

        String response = requestCreator.searchResult(url);
        GroupData groupData = parser.parse(response, partsOfCommand[partsOfCommand.length - 1]);
        if (groupData == null) {
            bot.execute(new SendMessage(chatId, "Данные не найдены"));
            return;
        }
        switch (this.command) {
            case "day_lessons":
            case "week_lessons":
                weekNumber = Long.parseLong(partsOfCommand[partsOfCommand.length - 2]);
                answer = answerCreater.createStaticAnswer(groupData, partsOfCommand, weekNumber, futureAnswer);
                break;
            
            case "tommorow_lessons":
                answer = answerCreater.getNextDayLessons(groupData, partsOfCommand[0], futureAnswer);
                break;

            case "near_lesson":
                answer = answerCreater.getNearLesson(groupData, partsOfCommand[0], futureAnswer);
                break;    
        }
        
        bot.execute(new SendMessage(chatId, answer));
        futureAnswer.setLength(0);
    }
}