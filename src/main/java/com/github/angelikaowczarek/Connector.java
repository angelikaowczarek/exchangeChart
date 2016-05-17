package com.github.angelikaowczarek;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

public class Connector {
    private HttpURLConnection connection;
    private URL url;
    private JsonFile jsonFile;
    private double rate;

    public void connect(LocalDate date) throws IOException {
        url = new URL("http://api.fixer.io/" + date.toString() + "?symbols=EUR,PLN");

        createConnection();
        connection.connect();

        throwExceptionIfFailed();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String json = bufferedReader.readLine();
        System.out.println(json);
        jsonFile = new ObjectMapper().readValue(json.getBytes(), JsonFile.class);
        rate = jsonFile.getRates().PLN;
        System.out.println(jsonFile.getRates().PLN);

        connection.disconnect();
    }

    public double getRate() {
        return rate;
    }

    private void throwExceptionIfFailed() throws IOException {
        if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED
                && connection.getResponseCode() != 200 ) {
            throw new RuntimeException("Failed: Server responsed HTTP error code: "
                    + connection.getResponseCode());
        }
    }

    private void createConnection() throws IOException {
        connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("GET");
    }

}
