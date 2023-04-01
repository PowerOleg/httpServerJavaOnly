package ru.netology.Homework35.httpServer.server;

public class Request {
    private String methodType;
    private String headers;
    private String body;

    public Request(String methodType, String headers, String body) {
        this.methodType = methodType;
        this.headers = headers;
        this.body = body;
    }
}
