package ru.netology.Homework40.springMVC.repositories;


import ru.netology.Homework40.springMVC.models.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    List<Post> all();

    Optional<Post> getById(long id);

    Post save(Post post);

    void removeById(long id);

}