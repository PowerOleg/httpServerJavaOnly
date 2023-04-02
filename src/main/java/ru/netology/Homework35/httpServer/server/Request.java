package ru.netology.Homework35.httpServer.server;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private String path;                                                                          //1
    private String methodType;
    private String headers;
    private String body;
    List<String> parameters = new ArrayList<>();                                                   //1
    List<String> attributes = new ArrayList<>();                                                   //1

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

    public void getParameter(String name) {                                                            //1
        parameters.add(name);
    }

    public void getAttribute(String name) {                                                            //1
        attributes.add(name);
    }

    public void read() {                                                                                //?

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
