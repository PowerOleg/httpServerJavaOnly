package ru.netology.Homework36.httpServer.server;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class Request {
    private String path;
    private String methodType;

    public List<NameValuePair> getParamsList() {
        return paramsList;
    }

    public List<NameValuePair> getQueryBodyParams() {
        return queryBodyParams;
    }

    private String headers;
    private String body;
    private List<NameValuePair> paramsList;
    private List<NameValuePair> queryBodyParams;

    public Request(String methodType, String headers, String body, String path, String queryFromPath) {
        this.path = path;
        this.methodType = methodType;
        this.headers = headers;
        this.body = body;
        this.paramsList = parseQuery(queryFromPath);
        this.queryBodyParams = parseQuery(body);
    }

    private List<NameValuePair> parseQuery(String query) {
        return URLEncodedUtils.parse(query, StandardCharsets.UTF_8);
    }

    public List<String> getPostParam(String name) {
        return queryBodyParams.stream().filter(n -> n.getName().equalsIgnoreCase(name))
                .map(NameValuePair::getValue).collect(Collectors.toList());
    }

    public List<String> getPostParams() {
        return queryBodyParams.stream().map(n -> {
            String param = n.getName();
            String value = n.getValue();
            return new String(param + "=" + value);
        }).collect(Collectors.toList());
    }

    public List<String> getQueryParam(String name) {
        return paramsList.stream().filter(n -> n.getName().equalsIgnoreCase(name))
                .map(NameValuePair::getValue).collect(Collectors.toList());
    }

    public List<String> getQueryParams() {
        return paramsList.stream().map(n -> {
            String param = n.getName();
            String value = n.getValue();
            return new String(param + "=" + value);
        }).collect(Collectors.toList());
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
