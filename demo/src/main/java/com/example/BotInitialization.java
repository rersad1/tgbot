package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.MyBot;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetMe;
import com.pengrad.telegrambot.response.GetMeResponse;

public class BotInitialization {

    private String token;
    private Long logsChatId;
    
    // проверка токена на валидность
    private void validateToken() { 
        TelegramBot bot = new TelegramBot(token);
        GetMeResponse getMeResponse = bot.execute(new GetMe());
        if (!getMeResponse.isOk()) {
            System.out.println("Invalid token.");
            System.exit(1);
        }
    }    

    // загрузка токена из файла конфигурации
    public BotInitialization() { 
        Properties props = new Properties();
        try (InputStream input = MyBot.class.getClassLoader().getResourceAsStream("config.properties")) {
            props.load(input);  
        } 
        catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Unable to load config file.");
            return;
        }

        setLogsChatId(Long.parseLong(props.getProperty("logsChannel")));
        setToken(props.getProperty("token"));
        
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        validateToken();
    }
    
    public void setLogsChatId(Long reportChatId) {
        this.logsChatId = reportChatId;
    }

    public Long getLogsChatId() {
        return logsChatId;
    }
}
