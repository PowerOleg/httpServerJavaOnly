package ru.netology.Homework40.springMVC.models;

import com.google.gson.annotations.Expose;

public class Post {
    @Expose
    private long id;
    @Expose
    private String content;
    @Expose(serialize = false)
    private boolean removed;

    public Post() {
    }

    public Post(long id, String content) {
        this.id = id;
        this.content = content;
        removed = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
}
