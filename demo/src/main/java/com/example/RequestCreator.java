package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class RequestCreator {

    

    public String searchResult(String url) { // получение результата поиска
    try {
        URI uri = new URI(url); // Создание URI вместо URL
        URL obj = uri.toURL(); 
        System.out.println("URL: " + obj.toString());
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection(); 
        int responseCode = connection.getResponseCode(); // получение ответа от сервера
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream())); // буфер для чтения ответа
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) { // чтение ответа
                response.append(inputLine);
            }
            in.close();
            
            // System.out.println(response.toString());
            return response.toString(); // Возврат результата поиска
            
        } 
        else {
            System.out.println("Ответ сервера: " + responseCode);
            return "";      
        }              
    }
    catch (Exception e) {
        System.out.println("Ошибка при создании URL"); // обработка ошибки
        return "";
    }
}
}
