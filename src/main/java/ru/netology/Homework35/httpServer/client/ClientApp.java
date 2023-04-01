package ru.netology.Homework35.httpServer.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (Socket clientSocket = new Socket("localhost", 9999);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            out.println("GET /spring.png 1.1");
            String serverResponse2 = in.readLine();
            System.out.println(serverResponse2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
