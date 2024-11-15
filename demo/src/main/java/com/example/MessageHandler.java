package com.example;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

public class MessageHandler {
    public void handle(String message, long chatId, TelegramBot bot) {
        switch (message) {
            case "/start":
                bot.execute(new SendMessage(chatId, "Добро пожаловать!"));                
                break;
            case "/report":
                bot.execute(new SendMessage(chatId, "Опишите проблему возникшую при использовании бота."));
                break;
            case "/help":
                bot.execute(new SendMessage(chatId, "Список доступных команд:\n/start - начало работы с ботом\n/report - сообщить о проблеме\n/help - список доступных команд"));
                break;
        }
    }
}
