package ru.netology.Homework40.springMVC.services;

import org.springframework.stereotype.Service;
import ru.netology.Homework40.springMVC.exception.NotFoundException;
import ru.netology.Homework40.springMVC.models.Post;
import ru.netology.Homework40.springMVC.repositories.PostRepository;

import java.util.List;
@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public Post save(Post post) {
        return repository.save(post);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}
