package ru.netology.Homework36.httpServer.server;

import java.util.List;

public class Request {
    private String path;
    private String methodType;
    private String headers;
    private String body;
//  ЗАДАЧА! 1 из объекта типа Request нужно отдельно получать и путь запроса, и параметры из Query String.
    // 2 доработайте функциональность поиска хендлера так, чтобы учитывался только путь без Query,
// т. е. хендлер, зарегистрированный на "/messages", обрабатывал и запросы "/messages?last=10".
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


//    public List<String> getQueryParam(String name) {                                                                 //1
//
//    }
//
//    public List<String> getQueryParams() {                                                                           //1
//
//    }







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
