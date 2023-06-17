package com.blog.execption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BlogAPIException extends RuntimeException {
    public BlogAPIException(String message) {
        super(message);
    }
}
