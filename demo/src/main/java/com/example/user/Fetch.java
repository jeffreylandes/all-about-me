package com.example.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class Fetch {
    private Fetch() {}

    static public String getStringResponse(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) throw new Error("Unsuccessful request");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        return response.toString();
    }

    static public <T> T getGjsonClassResponse(URL url, Class<T> classType) throws IOException {
        String weatherResponse = getStringResponse(url);
        Gson gson = new Gson();
        T result = gson.fromJson(weatherResponse, classType);
        return result;
    }
}
