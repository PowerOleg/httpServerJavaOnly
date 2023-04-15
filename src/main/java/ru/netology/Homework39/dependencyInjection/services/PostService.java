package ru.netology.Homework39.dependencyInjection.services;

import org.springframework.stereotype.Service;
import ru.netology.Homework39.dependencyInjection.exception.NotFoundException;
import ru.netology.Homework39.dependencyInjection.models.Post;
import ru.netology.Homework39.dependencyInjection.repositories.PostRepository;

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
