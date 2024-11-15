package com.example;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;

public class ButtonsHandler {
    public void handleCallbackQuery(TelegramBot bot, CallbackQuery callbackQuery) {
        @SuppressWarnings("deprecation")
        long chatId = callbackQuery.message().chat().id(); // ID чата
        String callbackData = callbackQuery.data();
        
        Buttons buttons = new Buttons();
        InlineKeyboardMarkup daysOfWeak = buttons.createDaysOfWeekKeyboard();
        InlineKeyboardMarkup weekNumber = buttons.createWeekNumberkeyboard();
        switch(callbackData) {
            case "near_lesson":
                bot.execute(new SendMessage(chatId, "Введите номер группы у которой хотите узнать следующую пару"));
                break;
            case "day_lessons":
                bot.execute(new SendMessage(chatId, "Расписание на день"));
                bot.execute(new SendMessage(chatId, "Выберите день недели:").replyMarkup(daysOfWeak));
                break;
            case "tommorow_lessons":
                bot.execute(new SendMessage(chatId, "Введите номер группы у которой хотите узнать расписание на следующий день"));
                break;
            case "week_lessons":
                bot.execute(new SendMessage(chatId, "Выбирете тип недели").replyMarkup(weekNumber));
                break;
            case "monday":
                bot.execute(new SendMessage(chatId, "Понедельник"));
                break;
            case "tuesday":
                bot.execute(new SendMessage(chatId, "Вторник"));
                break;
            case "wednesday":
                bot.execute(new SendMessage(chatId, "Среда"));
                break;    
            case "thursday":
                bot.execute(new SendMessage(chatId, "Четверг"));
                break;    
            case "friday":
                bot.execute(new SendMessage(chatId, "Пятница"));
                break;
            case "saturday":
                bot.execute(new SendMessage(chatId, "Суббота"));
                break;
            case "even":
                bot.execute(new SendMessage(chatId, "Четная неделя"));
                break;
            case "odd":
                bot.execute(new SendMessage(chatId, "Нечетная неделя"));
                break;                                      
        }
        // Ответ на нажатие кнопки
        bot.execute(new AnswerCallbackQuery(callbackQuery.id()));
    }
}

