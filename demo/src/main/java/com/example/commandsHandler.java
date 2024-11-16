package com.example;

public class commandsHandler {
    private String command;
    private String[] partsOfCommand;

    public void parseCommand(String command) {
        this.command = command;
        // Удаление последнего элемента из строки перед разбиением
        int lastSpaceIndex = command.lastIndexOf(' ');
        if (lastSpaceIndex != -1) {
            this.partsOfCommand = command.substring(0, lastSpaceIndex).split(" ");
        } 
        else {
            this.partsOfCommand = new String[0]; // Если нет пробелов, массив будет пустым
        }
    }

    public String getCommand() {
        return this.command;
    }

    
}
