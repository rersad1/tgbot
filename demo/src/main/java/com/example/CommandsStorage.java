package com.example;

import java.util.HashMap;

public class CommandsStorage {

    private HashMap<Long, String> commands = new HashMap<Long, String>();
    
    public String getCurrentCommand(long chatId) {
        return commands.get(chatId);
    }

    public void addPartOfCommand(long chatId, String partOfCommand) { // добавление части команды
        if (commands.containsKey(chatId)) {
            commands.put(chatId, commands.get(chatId) + " " + partOfCommand);
        } 
        else {
            commands.put(chatId, partOfCommand);
        }
        System.out.println(commands.get(chatId));
    }

    public void clearCommand(long chatId) { // очистка команды
        commands.remove(chatId);
    }

    public boolean isCommandReady(long chatId) { // проверка на готовность команды
        String command = commands.get(chatId);
        if (command != null && command.length() >= 4) { 
            String lastFourChars = command.substring(command.length() - 4);
            return lastFourChars.equals(" END"); // проверка, что последние 4 символа равны " END"
        }
        return false;
    }
}
