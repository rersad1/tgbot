package com.Parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Parser {
    public GroupData parse(String jsonResponse, String groupNumber) {
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonObject groupDataJson = jsonObject.getAsJsonObject(groupNumber);
        GroupData groupData = gson.fromJson(groupDataJson, GroupData.class);

        return groupData;
    }
}