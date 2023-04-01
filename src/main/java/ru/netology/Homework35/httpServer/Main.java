package ru.netology.Homework35.httpServer;

import ru.netology.Homework35.httpServer.server.Handler;
import ru.netology.Homework35.httpServer.server.Server;

import java.io.BufferedOutputStream;
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
        // добавление хендлеров (обработчиков)
        server.addHandler("GET", "/messages", new Handler() {
            public void handle(Request request, BufferedOutputStream responseStream) {
                // TODO: handlers code
            }
        });
        server.addHandler("POST", "/messages", new Handler() {
            public void handle(Request request, BufferedOutputStream responseStream) {
                // TODO: handlers code
            }
        });

        server.listen(9999);
    }
}
