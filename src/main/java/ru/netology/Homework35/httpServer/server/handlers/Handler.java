package ru.netology.Homework35.httpServer.server.handlers;

import ru.netology.Homework35.httpServer.server.Request;

import java.io.BufferedOutputStream;
import java.io.IOException;

public interface Handler {
    void handle(Request request, BufferedOutputStream responseStream) throws IOException;
}
