package com.example;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

public class Buttons {
    public InlineKeyboardMarkup createFunctionsKeyboard() {
        return new InlineKeyboardMarkup(
            new InlineKeyboardButton[]{
                new InlineKeyboardButton("Следующая пара").callbackData("near_lesson"),
                new InlineKeyboardButton("Расписание на день").callbackData("day_lessons")
            },
            new InlineKeyboardButton[]{
                new InlineKeyboardButton("Расписание на завтра").callbackData("tommorow_lessons"),
                new InlineKeyboardButton("Расписание на неделю").callbackData("week_lessons")
            }
        );
    }

    public InlineKeyboardMarkup createDaysOfWeekKeyboard() {
        return new InlineKeyboardMarkup(
            new InlineKeyboardButton[]{
                new InlineKeyboardButton("Понедельник").callbackData("MON"),
                new InlineKeyboardButton("Вторник").callbackData("TUE")
            },
            new InlineKeyboardButton[]{
                new InlineKeyboardButton("Среда").callbackData("WED"),
                new InlineKeyboardButton("Четверг").callbackData("THU")
            },
            new InlineKeyboardButton[]{
                new InlineKeyboardButton("Пятница").callbackData("FRI"),
                new InlineKeyboardButton("Суббота").callbackData("SAT")
            }
        );
    }

    public InlineKeyboardMarkup createWeekNumberkeyboard() {
        return new InlineKeyboardMarkup(
            new InlineKeyboardButton[]{
                new InlineKeyboardButton("Четная").callbackData("1"),
                new InlineKeyboardButton("Нечетная").callbackData("2")
            }
        );    
    }
}