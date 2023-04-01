package ru.netology.Homework35.httpServer;

import com.sun.net.httpserver.Headers;
import ru.netology.Homework35.httpServer.server.Request;
import ru.netology.Homework35.httpServer.server.handlers.Handler;
import ru.netology.Homework35.httpServer.server.Server;

import java.io.BufferedOutputStream;
import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

//        final var validPaths = List.of("/index.html", "/spring.svg", "/spring.png", "/resources.html",
//                "/styles.css", "/app.js", "/links.html", "/forms.html", "/classic.html", "/events.html", "/events.js");
//
//        Server server = new Server(validPaths);
//        server.start();
public class Main {
    public static final int THREADS_NUMBER = 64;

    public static void main(String[] args) {
        final var server = new Server();
        // код инициализации сервера (из вашего предыдущего ДЗ)
        server.start();
        
        String requestHeaders = "Accept: " + "text/html" + "\r\n" +
                "Accept-encoding: " + "gzip" + "\r\n" +
                "Accept-language: " + "en-US,en;q=0.9" + "\r\n" +
                "Connection: keep-alive\r\n" +
                "\r\n";


        final var request1 = new Request("GET", requestHeaders, "Hello World");



        // добавление хендлеров (обработчиков)
//        server.addHandler("GET", "/messages", new Handler() {
//            public void handle(Request request, BufferedOutputStream responseStream) {
//                // TODO: handlers code
////                BufferedOutputStream берётся путём заворачивания OutputStream socket:
////                new BufferedOutputStream(socket.getOutputStream()).
//                responseStream.write(" \r\n");
//                responseStream.flush();
//
//            }
//        });
//        server.addHandler("POST", "/messages", new Handler() {
//            public void handle(Request request, BufferedOutputStream responseStream) {
//                // TODO: handlers code
//            }
//        });
//
//        server.listen(9999);
    }
}
