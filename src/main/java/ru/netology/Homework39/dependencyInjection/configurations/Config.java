package ru.netology.Homework39.dependencyInjection.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.netology.Homework39.dependencyInjection.controllers.PostController;
import ru.netology.Homework39.dependencyInjection.repositories.PostRepository;
import ru.netology.Homework39.dependencyInjection.repositories.PostRepositoryImpl;
import ru.netology.Homework39.dependencyInjection.services.PostService;

//@Configuration
public class Config {
//    @Bean
    public PostController postController(PostService service) {
        return new PostController(service);
    }

//    @Bean
    public PostService postService(PostRepository repository) {
        return new PostService(repository);
    }

//    @Bean
    public PostRepository postRepository() {
        return new PostRepositoryImpl();
    }
}
