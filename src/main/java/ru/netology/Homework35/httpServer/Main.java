package ru.netology.Homework35.httpServer;

import ru.netology.Homework35.httpServer.server.Request;
import ru.netology.Homework35.httpServer.server.Server;
import ru.netology.Homework35.httpServer.server.handlers.Handler;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static final int THREADS_NUMBER = 64;
    public static final String GET = "GET";
    public static final String POST = "POST";

    public static void main(String[] args) {
        final var server = new Server();
        server.addHandler(GET, "/messages", new Handler() {
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
                System.out.println("The server response's file: " + filePath.getFileName());
                out.flush();
            }
        });

        server.addHandler(POST, "/messages", new Handler() {
            public void handle(Request request, BufferedOutputStream out) throws IOException {
                String body = request.getBody();
                final var filePath = Path.of(".", "public", "/classic.html");
                final var template = Files.readString(filePath);
                final var content = template.replace("{time}", body);
                final var mimeType = Files.probeContentType(filePath);
                out.write(("HTTP/1.1 200 OK\r\n" +
                        "Content-type: " + mimeType + "\r\n" +
                        "Content-Length: " + content.length() + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n").getBytes());
                out.write(content.getBytes());
                System.out.println("The server response's file: " + filePath.getFileName());
                out.flush();
            }
        });
        server.listen(9999);
        server.start();
    }
}













