package ru.netology.Homework40.springMVC.controllers;

import org.springframework.web.bind.annotation.*;
import ru.netology.Homework40.springMVC.models.Post;
import ru.netology.Homework40.springMVC.services.PostService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
