package com.example;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.DeleteWebhook;
import com.pengrad.telegrambot.UpdatesListener;

public class MyBot {
    public static Long logsChannel;
    public static void main(String[] args) {
        BotInitialization botInit = new BotInitialization();
        TelegramBot bot = new TelegramBot(botInit.getToken());
        logsChannel = botInit.getLogsChatId();
        Buttons buttons = new Buttons();
        InlineKeyboardMarkup functionsKeyboard = buttons.createFunctionsKeyboard();
        CommandsStorage commandsStorage = new CommandsStorage();
        ButtonsHandler buttonsHandler = new ButtonsHandler();
        MessageHandler messageHandler = new MessageHandler();
        CommandsHandler commandsHandler = new CommandsHandler();
        bot.execute(new DeleteWebhook());

        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                if (update.callbackQuery() != null) {
                    buttonsHandler.handleCallbackQuery(bot, update.callbackQuery(), commandsStorage);
                }
                else if (update.message() != null && update.message().text() != null) {
                    long chatId = update.message().chat().id();
                    String messageText = update.message().text();
                    messageHandler.handle(messageText, chatId, bot, commandsStorage, functionsKeyboard);                   
                        
                    if (commandsStorage.isCommandReady(chatId)) {
                        String commandText = commandsStorage.getCurrentCommand(chatId);
                        commandsHandler.handleCommand(bot, commandText, chatId);
                        commandsStorage.clearCommand(chatId);
                    }
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}