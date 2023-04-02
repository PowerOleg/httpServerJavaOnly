package ru.netology.Homework35.httpServer;

import com.sun.net.httpserver.Headers;
import ru.netology.Homework35.httpServer.server.Request;
import ru.netology.Homework35.httpServer.server.handlers.Handler;
import ru.netology.Homework35.httpServer.server.Server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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


        server.addHandler("GET", "/messages", new Handler() {
            public void handle(Request request, BufferedOutputStream out) throws IOException {
                final var filePath = Path.of(".", "public", "/index.html");
                final var mimeType = Files.probeContentType(filePath);
                final var length = Files.size(filePath);
                out.write(("HTTP/1.1 200 OK\r\n" +
                        "Content-type: " + mimeType + "\r\n" +
                        "Content-Length: " + length + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n").getBytes());
                Files.copy(filePath, out);
                out.flush();
            }
        });

        server.addHandler("POST", "/messages", new Handler() {
            public void handle(Request request, BufferedOutputStream out) throws IOException {
                // TODO: handlers code
                String time = request.getBody();
//                System.out.println((LocalDateTime.now()).format(DateTimeFormatter.ofPattern("d.MM.uuuu  HH:mm:ss")));


                final var filePath = Path.of(".", "public", "/classic.html");
                final var template = Files.readString(filePath);
                final var content = template.replace("{time}", time);
                final var mimeType = Files.probeContentType(filePath);
                out.write(("HTTP/1.1 200 OK\r\n" +
                            "Content-type: " + mimeType + "\r\n" +
                            "Content-Length: " + content.length() + "\r\n" +
                            "Connection: close\r\n" +
                            "\r\n").getBytes());
                out.write(content.getBytes());
                    out.flush();
            }
        });
        server.listen(9999);
        server.start();
    }
}













