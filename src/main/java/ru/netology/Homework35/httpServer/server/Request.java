package ru.netology.Homework35.httpServer.server;

public class Request {
    private String path;
    private String methodType;
    private String headers;
    private String body;

    public Request(String methodType, String headers, String body,String path) {

        this.methodType = methodType;
        this.headers = headers;
        this.body = body;
        this.path = path;
    }
    public Request(String methodType, String headers, String body) {
        this.methodType = methodType;
        this.headers = headers;
        this.body = body;
    }

    public String getPath() {
        return path;
    }

    public String getMethodType() {
        return methodType;
    }

    public String getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
