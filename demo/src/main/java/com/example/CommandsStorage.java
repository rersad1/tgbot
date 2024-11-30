package com.example;

import java.util.HashMap;

public class CommandsStorage {

    private HashMap<Long, String> commands = new HashMap<Long, String>();
    private boolean isWaitingForGroup = false;

    public String getCurrentCommand(long chatId) {
        return commands.get(chatId);
    }

    // добавление части команды
    public void addPartOfCommand(long chatId, String partOfCommand) { 
        if (commands.containsKey(chatId)) {
            commands.put(chatId, commands.get(chatId) + " " + partOfCommand);
        } 
        else {
            commands.put(chatId, partOfCommand);
        }
        System.out.println(commands.get(chatId));
    }

    // очистка команды
    public void clearCommand(long chatId) { 
        commands.remove(chatId);
    }

    // проверка на готовность команды
    public boolean isCommandReady(long chatId) { 
        String command = commands.get(chatId);
        if (command != null && command.length() >= 4) { // проверка, что последние 4 символа равны " END"
            String lastFourChars = command.substring(command.length() - 4);
            return lastFourChars.equals(" END"); 
        }
        return false;
    }

    public void setWaitingForGroup(boolean isWaitingForGroup) {
        this.isWaitingForGroup = isWaitingForGroup;
    }

    public boolean isWaitingForGroup() {
        return isWaitingForGroup;
    }
}
