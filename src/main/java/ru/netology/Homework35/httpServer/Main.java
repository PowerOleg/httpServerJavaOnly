package ru.netology.Homework35.httpServer;

import com.sun.net.httpserver.Headers;
import ru.netology.Homework35.httpServer.server.Request;
import ru.netology.Homework35.httpServer.server.handlers.Handler;
import ru.netology.Homework35.httpServer.server.Server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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


        
        String requestHeaders = "Accept: " + "text/html" + "\r\n" +
                "Accept-encoding: " + "gzip" + "\r\n" +
                "Accept-language: " + "en-US,en;q=0.9" + "\r\n" +
                "Connection: keep-alive\r\n" +
                "\r\n";

        final var request1 = new Request("GET", requestHeaders, "", "/index.html"); //убрать path














        server.addHandler("GET", "/messages", new Handler() {
            public void handle(Request request, BufferedOutputStream out) throws IOException {
                // TODO: handlers code
// переделать чтобы работать с Request а не создавать и передавать искусственный request1
                final var filePath = Path.of(".", "public", request.getPath());
                final var mimeType = Files.probeContentType(filePath);
                final var length = Files.size(filePath);
                out.write(("HTTP/1.1 200 OK\r\n" +
                        "Content-type: " + mimeType + "\r\n" +
                        "Content-Length: " + length + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n").getBytes());
                Files.copy(filePath, out);
                out.flush();
                //                responseStream.setContentType("text/html");
            }
        });

        server.listen(9999);
        server.start(request1);
    }
}














//        server.addHandler("POST", "/messages", new Handler() {
//            public void handle(Request request, BufferedOutputStream responseStream) {
//                // TODO: handlers code
//            }
//        });