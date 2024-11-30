package com.Handlers;

import com.example.CommandsStorage;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;

public class MessageHandler {
    public void handle(String message, long chatId, TelegramBot bot, CommandsStorage commandsStrorage, InlineKeyboardMarkup functionsKeyboard) {

        switch (message) { // обработка команд через /command
            case "/start":
                bot.execute(new SendMessage(chatId, "Добро пожаловать!"));
                bot.execute(new SendMessage(chatId, "Выберите функцию:").replyMarkup(functionsKeyboard));                
                return;
            case "/report":
                bot.execute(new SendMessage(chatId, "Опишите проблему возникшую при использовании бота."));
                commandsStrorage.addPartOfCommand(chatId, "REPORT");
                return;
            case "/help":
                bot.execute(new SendMessage(chatId, "Список доступных команд:\n/start - начало работы с ботом\n/report - сообщить о проблеме\n/help - список доступных команд"));
                return;
        }
        
        if (commandsStrorage.getCurrentCommand(chatId) == "REPORT") { // обработка текста отчета
            commandsStrorage.addPartOfCommand(chatId, message);
            commandsStrorage.addPartOfCommand(chatId, "END");
        }
        else if (message.matches("^\\d+$") && message.length() == 4 && commandsStrorage.getCurrentCommand(chatId) != null && commandsStrorage.isWaitingForGroup()) { 
            message += " END";
            commandsStrorage.addPartOfCommand(chatId, message);
            bot.execute(new SendMessage(chatId, "Выполняется запрос..."));
        }
        else {
            bot.execute(new SendMessage(chatId, "Введены неправильные данные. Попробуйте еще раз."));
            bot.execute(new SendMessage(chatId, "Выберите функцию:").replyMarkup(functionsKeyboard));
            commandsStrorage.clearCommand(chatId);
            commandsStrorage.setWaitingForGroup(false);
            System.out.println(message);
        }
    }
}
