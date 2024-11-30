package com.Handlers;

import com.example.Buttons;
import com.example.CommandsStorage;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;

public class ButtonsHandler {
    public void handleCallbackQuery(TelegramBot bot, CallbackQuery callbackQuery, CommandsStorage commandsStrorage) {
        @SuppressWarnings("deprecation")
        long chatId = callbackQuery.message().chat().id(); // ID чата
        @SuppressWarnings("deprecation")
        int messageId = callbackQuery.message().messageId();
        String callbackData = callbackQuery.data();

        Buttons buttons = new Buttons();
        InlineKeyboardMarkup daysOfWeak = buttons.createDaysOfWeekKeyboard();
        InlineKeyboardMarkup weekNumber = buttons.createWeekNumberkeyboard();
        commandsStrorage.addPartOfCommand(chatId, callbackData);

        // обработка нажатий на кнопки
        switch(callbackData) {
            case "day_lessons":
                bot.execute(new SendMessage(chatId, "Выберите день недели:").replyMarkup(daysOfWeak));
                break;
            case "week_lessons":
                bot.execute(new SendMessage(chatId, "Выбирете тип недели").replyMarkup(weekNumber));
                break;
            case "MON":
            case "TUE":
            case "WED":
            case "THU":
            case "FRI":
            case "SAT": 
                bot.execute(new SendMessage(chatId, "Выбирете тип недели").replyMarkup(weekNumber));
                break;   
            default:
                bot.execute(new SendMessage(chatId, "Введите номер группы, о которой хотите узнать информацию"));
                commandsStrorage.setWaitingForGroup(true);
                break;                                          
        }

        bot.execute(new DeleteMessage(chatId, messageId));        
        bot.execute(new AnswerCallbackQuery(callbackQuery.id()));
    }
}

