package ru.netology.Homework36.httpServer.server;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import ru.netology.Homework36.httpServer.server.handlers.Handler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static ru.netology.Homework36.httpServer.Main.*;

public class Server {
    final ExecutorService threadPool = Executors.newFixedThreadPool(64);
    final List<String> allowedMethods = List.of(GET, POST);
    private final ConcurrentMap<String, Handler> getHandlers = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Handler> postHandlers = new ConcurrentHashMap<>();
    private int port = 8080;

    private static int indexOf(byte[] array, byte[] target, int start, int max) {
        outer:
        for (int i = start; i < max - target.length + 1; i++) {
            for (int j = 0; j < target.length; j++) {
                if (array[i + j] != target[j]) {
                    continue outer;
                }
            }
            return i;
        }
        return -1;
    }

    public boolean addHandler(String requestMethod, String path, Handler handler) {
        if (requestMethod.equals(GET)) {
            getHandlers.put(path, handler);
            return true;
        }
        if (requestMethod.equals(POST)) {
            postHandlers.put(path, handler);
            return true;
        }
        return false;
    }

    public Handler findHandler(String requestMethod, String path) {
        Handler handler = null;
        if (requestMethod.equals(GET)) {
            handler = getHandlers.get(path);
        }
        if (requestMethod.equals(POST)) {
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
                 final var in = new BufferedInputStream(socket.getInputStream());
                 final var out = new BufferedOutputStream(socket.getOutputStream())) {

                final var limit = 4096;
                in.mark(limit);
                final var buffer = new byte[limit];
                final var read = in.read(buffer); //здесь, в in.read(buffer) заливаются данные в byte массив buffer. а read - цифра, сколько элементов массива заполнены

                final byte[] requestLineDelimiter = new byte[]{'\r', '\n'};

                final int requestLineEnd = indexOf(buffer, requestLineDelimiter, 0, read);
                if (requestLineEnd == -1) {
                    badRequest(out);
                    return;
                }

                final String[] requestLine = new String(Arrays.copyOf(buffer, requestLineEnd)).split(" ");
                if (requestLine.length != 3) {
                    badRequest(out);
                    System.out.println("A wrong request");
                    return;
                }
                //получаем метод из Request Line
                final String method = requestLine[0];
                if (!allowedMethods.contains(method)) {
                    badRequest(out);
                    System.out.println("A wrong method");
                    return;
                }

                //получаем путь с параметрами из Request Line
                var path = requestLine[1];
                if (!path.startsWith("/")) {
                    badRequest(out);
                    System.out.println("A wrong path");
                    return;
                }

                //получаем путь без параметров
                List<NameValuePair> paramList = Collections.emptyList();
                if (path.contains("?")) {
                    String[] newPath = path.split("\\?");
//                            path.substring(0, path.indexOf("?"));
                    paramList = URLEncodedUtils.parse(newPath[1], StandardCharsets.UTF_8);
                    path = newPath[0];
                    System.out.println("path: " + path);
                    System.out.println("path: " + paramList);
                }


                //получаем заголовки из Request
                final byte[] headersDelimiter = new byte[]{'\r', '\n', '\r', '\n'};
                final int headersStart = requestLineEnd + requestLineDelimiter.length;
                final int headersEnd = indexOf(buffer, headersDelimiter, headersStart, read);

                if (headersEnd == -1) {
                    badRequest(out);
                    System.out.println("Something wrong with headersEnd");
                    return;
                }


                in.reset();
                in.skip(headersStart);
                final byte[] headersBytes = in.readNBytes(headersEnd - headersStart);
                final List<String> headers = Arrays.asList(new String(headersBytes).split("\r\n"));
                System.out.println("Client's headers: " + headers);


                String body = null;
                if (!method.equals(GET)) {
                    final var contentType = extractHeader(headers, "Content-Type");
                    if (contentType.isPresent()) {
                        if (contentType.get().equals(URLEncodedUtils.CONTENT_TYPE)) {
                            in.skip(headersDelimiter.length); //тело находится после заголовков, => пропускаем \r\n\r\n
                            final var contentLength = extractHeader(headers, "Content-Length");
                            if (contentLength.isPresent()) {
                                final var length = Integer.parseInt(contentLength.get());
                                final byte[] bodyBytes = in.readNBytes(length);
                                body = new String(bodyBytes);
                                System.out.println("Client's body: " + body);
                            }
                        }
                    }
                }


                final var request = new Request(method, headers.toString(), body, path, paramList);
                var handler = findHandler(method, path);

                try {
                    handler.handle(request, out);
                } catch (NullPointerException e) {
                    System.out.println("There is no handler for this request: " + request.getPath());
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Optional<String> extractHeader(List<String> headers, String header) {
        return headers.stream()
                .filter(n -> n.startsWith(header))
                .map(n -> n.substring(n.indexOf(" ")))
                .map(String::trim).findFirst();
    }

    private void badRequest(BufferedOutputStream out) {
        try {
            out.write((
                    "HTTP/1.1 404 Not Found\r\n+" +
                            "Content-Length: 0\r\n" +
                            "Connection: close\r\n" +
                            "\r\n").getBytes());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("A handler is not found");
    }

    public int getPort() {
        return port;
    }

    public void listen(int port) {
        this.port = port;
    }
}
