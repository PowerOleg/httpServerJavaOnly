package ru.netology.Homework35.httpServer.server;

import ru.netology.Homework35.httpServer.server.handlers.Handler;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static ru.netology.Homework35.httpServer.Main.THREADS_NUMBER;

public class Server {
    final ExecutorService threadPool = Executors.newFixedThreadPool(64);
    private final ConcurrentMap<String, Handler> getHandlers = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Handler> postHandlers = new ConcurrentHashMap<>();
    private int port = 8080;

    public boolean addHandler(String requestMethod, String path, Handler handler) {
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
                                this.handle(serverSocket);
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

    public void handle(ServerSocket serverSocket) {
        while (true) {
            try (final var socket = serverSocket.accept();
                 final var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 final var out = new BufferedOutputStream(socket.getOutputStream())) {
                final var requestLine = in.readLine();
                final var parts = requestLine.split(" ");
                if (parts.length != 3) {
                    System.out.println("A wrong request");
                    return;
                }
                final var requestMethod = parts[0];
                final var path = parts[1];
                System.out.println("The client request: " + requestMethod + " " + path);


                String body = null;
                String requestHeaders = null;
                String line;
                final var stringBuilder = new StringBuffer();
                while (in.ready()) {
                    line = in.readLine();
                    stringBuilder.append(line);
                }

                if (requestMethod.equalsIgnoreCase("get")) {
                    requestHeaders = stringBuilder.toString();
                }
                if (requestMethod.equalsIgnoreCase("post")) {
                    var requestToParse = stringBuilder.toString();
                    int bodyStart = requestToParse.indexOf("{");
                    int bodyEnd = requestToParse.indexOf("}");
                    requestHeaders = requestToParse.substring(0, bodyStart);
                    body = requestToParse.substring(bodyStart, bodyEnd + 1);
                    System.out.println("A client sent the body: " + body);
                }

                final var request = new Request(requestMethod, requestHeaders, body, path);
                var handler = findHandler(requestMethod, path);
                if (handler == null) {
                    out.write((
                            "HTTP/1.1 404 Not Found\r\n+" +
                                    "Content-Length: 0\r\n" +
                                    "Connection: close\r\n" +
                                    "\r\n").getBytes());
                    out.flush();
                    System.out.println("A handler is not found");
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
