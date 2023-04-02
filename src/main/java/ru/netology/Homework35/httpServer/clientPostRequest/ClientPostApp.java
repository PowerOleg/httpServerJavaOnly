package ru.netology.Homework35.httpServer.clientPostRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ClientPostApp {
    public static void main(String[] args) {
        String urlAddress = "http://127.0.0.1:9999/messages";
        URL url;
        HttpURLConnection httpURLConnection;

        OutputStream outputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            //body
            Map<String, String> postArgs = new HashMap<>();
            postArgs.put("user", "Bob");
            postArgs.put("password", "123");

            byte[] out = postArgs.toString().getBytes();


            url = new URL(urlAddress);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);            //позволяет отсылать сообщения
            httpURLConnection.setDoInput(true);             //позволяет принимать сообщения

            httpURLConnection.addRequestProperty("User-Agent", "Yandex/22.7.2.902");
            httpURLConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.connect();


            try {
                outputStream = httpURLConnection.getOutputStream();
                outputStream.write(out);
            } catch (Exception e) {
                System.err.print(e.getMessage());
            }
            if (HttpURLConnection.HTTP_OK == httpURLConnection.getResponseCode()) {
                inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

            }
            System.out.println(stringBuilder.toString().length());
            System.out.println(stringBuilder.substring(300, 345));

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            System.err.print(e.getMessage());

        } finally {
            try {
                inputStreamReader.close();
                bufferedReader.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
