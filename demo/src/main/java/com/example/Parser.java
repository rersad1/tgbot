package com.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Parser {
    public GroupData parse(String jsonResponse, String groupNumber) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Парсинг JSON строки
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

        // Предположим, что номер группы известен
        JsonObject groupDataJson = jsonObject.getAsJsonObject(groupNumber);

        // Парсинг данных группы
        GroupData groupData = gson.fromJson(groupDataJson, GroupData.class);

        // Вывод данных группы в консоль
        // System.out.println("Parsed data for group " + groupNumber + ":");
        // System.out.println(gson.toJson(groupData));

        return groupData;

    }
}