package ru.netology.Homework35.httpServer;

import java.io.*;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class HttpServer {
    public static void main(String[] args) {
        final var validPaths = List.of("/index.html", "/spring.svg", "/spring.png", "/resources.html",
                 "/styles.css", "/app.js", "/links.html", "/forms.html", "/classic.html", "/events.html", "/events.js");

        try(final var serverSocket = new ServerSocket(9999)) {
            while (true) {
                try (final var socket = serverSocket.accept();
                    final var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    final var out = new BufferedOutputStream(socket.getOutputStream());
                ) {
                    final var requestLine = in.readLine();
                    final var parts = requestLine.split(" ");
                    if (parts.length != 3) {
                        continue;
                    }

                    final var path = parts[1];
                    if (!validPaths.contains(path)) {
                        out.write((
                                "HTTP/1.1 404 Not Found\r\n+" +
                                        "Content-Length: 0\r\n" +
                                        "Connection: close\r\n" +
                                        "\r\n").getBytes());
                        out.flush();
                        continue;
                    }


                    final var filePath = Path.of(".", "public", path);
                    final var mimeType = Files.probeContentType(filePath);
                    final var length = Files.size(filePath);


//пример как своими руками сделать шаблонизатор
                    if (path.equals("/classic.html")) {
                        final var template = Files.readString(filePath);
                        final var content = template.replace("{time}", LocalDate.now().toString());
                        out.write(("HTTP/1.1 200 OK\r\n" +
                                "Content-type: " + mimeType + "\r\n" +
                                "Content-Length: " + content.length() + "\r\n" +
                                "Connection: close\r\n" +
                                "\r\n").getBytes());
                        out.write(content.getBytes());
                        out.flush();
                        continue;
                    }



                    out.write(("HTTP/1.1 200 OK\r\n" +
                            "Content-type: " + mimeType + "\r\n" +
                            "Content-Length: " + length + "\r\n" +
                            "Connection: close\r\n" +
                            "\r\n").getBytes());
                    Files.copy(filePath, out);
                    out.flush();


                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
