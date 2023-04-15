package ru.netology.Homework39.dependencyInjection.controllers;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import ru.netology.Homework39.dependencyInjection.models.Post;
import ru.netology.Homework39.dependencyInjection.services.PostService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<Post> all() throws IOException {
        return service.all();
    }

    @GetMapping("/{id:[\\d]+}")
    public Post getById(@PathVariable long id) throws IOException {
        return service.getById(id);
    }

    @PostMapping
    public Post save(@RequestBody Post post) throws IOException {
        return service.save(post);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable long id) {
        service.removeById(id);
    }
}
