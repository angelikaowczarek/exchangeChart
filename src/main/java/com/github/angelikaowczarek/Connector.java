package com.github.angelikaowczarek;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Connector {
    private HttpURLConnection connection;
    private String output;
    private URL url;
    private double rate;

    public void connect(String date) throws IOException {
        url = new URL("http://api.fixer.io/" + date + "?symbols=EUR,PLN");

        createConnection();
        connection.connect();

        throwExceptionIfFailed();
        printOutputIfSuccess();

//        JSONObject actionsObject = createActionsObject();
//        findActions(actionsObject);

        connection.disconnect();
    }

//    private JSONObject createActionsObject() throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        String actionsLine = bufferedReader.readLine();
//        return new JSONObject(actionsLine);
//    }

    private void printOutputIfSuccess() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                (connection.getInputStream())));

        String output;
        System.out.println("Output from server... \n");
        while ((output = bufferedReader.readLine()) != null) {
            System.out.println(output);
        }
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

    public double getRate() {
        return rate;
    }

    //    private void findActions(JSONObject obj) {
//        JSONArray actions = obj.getJSONArray("actions");
//        boolean isAnyActionFound = false;
//        JSONObject action;
//        JSONArray devicesArray;
//
//        for (int i = 0; i < actions.length(); i++) {
//
//            action = actions.getJSONObject(i);
//            devicesArray = action.getJSONArray("deviceUniqueIds");
//
//            for (int j = 0; j < devicesArray.length(); j++) {
//
//                if (devicesArray.getString(j).equals(uniqueId)) {
//                    isAnyActionFound = true;
//                    printAction(action);
//                    break;
//                }
//            }
//        }
//        if (!isAnyActionFound) {
//            System.out.println("No actions assigned to this device found");
//            System.exit(1);
//        }
//    }
}
