package com.blog.payload;

import lombok.Data;

@Data
public class CommentDto {
    private long Id;
    private String name;
    private String email;
    private String body;

}


