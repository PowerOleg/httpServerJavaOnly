package ru.netology.Homework39.dependencyInjection.controllers;


import com.google.gson.Gson;

import org.springframework.stereotype.Controller;
import ru.netology.Homework39.dependencyInjection.models.Post;
import ru.netology.Homework39.dependencyInjection.services.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

@Controller
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;
    private final Gson gson;


    public PostController(PostService service) {
        this.service = service;
        gson = new Gson();
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = service.all();
        response.getWriter().print(gson.toJson(data));
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var post = service.getById(id);
        response.getWriter().println(gson.toJson(post));
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var post = gson.fromJson(body, Post.class);
        final var data = service.save(post);
        response.getWriter().print(gson.toJson(data));
    }

    public void removeById(long id, HttpServletResponse response) {
        response.setContentType(APPLICATION_JSON);
        service.removeById(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
