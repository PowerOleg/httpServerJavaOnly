package ru.netology.Homework35.httpServer;

import ru.netology.Homework35.httpServer.server.Server;

import java.util.List;

public class Main {
    public static final int THREADS_NUMBER = 64;

    public static void main(String[] args) {
        final var validPaths = List.of("/index.html", "/spring.svg", "/spring.png", "/resources.html",
                "/styles.css", "/app.js", "/links.html", "/forms.html", "/classic.html", "/events.html", "/events.js");

        Server server = new Server(validPaths);
        server.start();
    }
}
