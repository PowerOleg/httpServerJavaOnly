package ru.netology.Homework35.httpServer.server;

import ru.netology.Homework35.httpServer.server.handlers.Handler;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static ru.netology.Homework35.httpServer.Main.THREADS_NUMBER;

public class Server {
//    private ConcurrentMap<String, String> requestMethodType = new ConcurrentHashMap<>();                    //d
//    private List<String> validPaths = new ArrayList<>();                                                       //d
//    private ConcurrentMap<String, Object> validPaths = new ConcurrentHashMap<>();                          //d
    private ConcurrentMap<String, Handler> getHandlers = new ConcurrentHashMap<>();                          //1
    private ConcurrentMap<String, Handler> postHandlers = new ConcurrentHashMap<>();                          //1

    private int port = 8080;
    final ExecutorService threadPool = Executors.newFixedThreadPool(64);

    public Server() {
    }



    public boolean addHandler(String requestMethod, String path, Handler handler) {                    //0
        if (requestMethod.equalsIgnoreCase("get")) {
            getHandlers.put(path, handler);
            return true;
        }
        if (requestMethod.equalsIgnoreCase("post")) {
            postHandlers.put(path, handler);
            return true;
        }
        return false;
    }

    public Handler findHandler(String requestMethod, String path) {
        Handler handler = null;
        if (requestMethod.equalsIgnoreCase("get")) {
            handler = getHandlers.get(path);
        }
        if (requestMethod.equalsIgnoreCase("post")) {
            handler = postHandlers.get(path);
        }
        return handler;
    }

















    public void start() {
        try (final var serverSocket = new ServerSocket(port)) {
            System.out.println("The server has been started");

            IntStream.range(0, THREADS_NUMBER)
                    .forEach(i -> {
                        Runnable task = () ->
                                this.handle(serverSocket); //переделать без Request request, BufferedOutputStream bufferedOutputStream
                        threadPool.execute(task);
                        System.out.println("thread " + i + " run");
                    });

//currentThread - {Thread@1}"Thread[main,5,main]
            Thread.currentThread().join();
            threadPool.shutdown();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //1 переделать без Request request, BufferedOutputStream bufferedOutputStream
    public void handle(ServerSocket serverSocket) {
        while (true) {
            try (final var socket = serverSocket.accept();
                 final var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 final var out = new BufferedOutputStream(socket.getOutputStream())) {
                final var requestLine = in.readLine();
                final var parts = requestLine.split(" ");
                if (parts.length != 3) {
                    return;
                }

                final var requestMethod = parts[0];
                final var path = parts[1];
                String requestHeaders = "Accept: " + "text/html" + "\r\n" +
                        "Accept-encoding: " + "gzip" + "\r\n" +
                        "Accept-language: " + "en-US,en;q=0.9" + "\r\n" +
                        "Connection: keep-alive\r\n" +
                        "\r\n";
                StringBuilder stringBuilder = new StringBuilder();
                String body;
                String line;
                while ((line=in.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
                body = stringBuilder.toString();
                System.out.println(body);





                final var request = new Request(requestMethod, requestHeaders, in.toString(), path);   //body!!!!!!!



                var handler = findHandler(requestMethod, path);
                if (handler == null) {
                    out.write((
                            "HTTP/1.1 404 Not Found\r\n+" +
                                    "Content-Length: 0\r\n" +
                                    "Connection: close\r\n" +
                                    "\r\n").getBytes());
                    out.flush();
                    return;
                }
                handler.handle(request, out);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public int getPort() {
        return port;
    }

    public void listen(int port) {
        this.port = port;
    }
}
