package com.example;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.DeleteWebhook;
import com.pengrad.telegrambot.UpdatesListener;

public class MyBot {
    public static void main(String[] args) {
        BotInitialization botInit = new BotInitialization();
        TelegramBot bot = new TelegramBot(botInit.getToken());
        Buttons buttons = new Buttons();
        InlineKeyboardMarkup functionsKeyboard = buttons.createFunctionsKeyboard();
        CommandsStrorage commandsStrorage = new CommandsStrorage();
        ButtonsHandler buttonsHandler = new ButtonsHandler();
        MessageHandler messageHandler = new MessageHandler();
        bot.execute(new DeleteWebhook());

        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                if (update.callbackQuery() != null) {
                    buttonsHandler.handleCallbackQuery(bot, update.callbackQuery(), commandsStrorage);
                }
                else if (update.message() != null && update.message().text() != null) {
                    long chatId = update.message().chat().id();
                    String messageText = update.message().text();
                    messageHandler.handle(messageText, chatId, bot, commandsStrorage, functionsKeyboard);                   

                    System.out.println(messageText);
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}