package ru.netology.Homework40.springMVC.configurations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
public class WebConfig {
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        final var bean = new RequestMappingHandlerAdapter();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        bean.getMessageConverters().add(new GsonHttpMessageConverter(gson));
        return bean;
    }
}
