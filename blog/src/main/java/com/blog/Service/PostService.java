package com.blog.Service;

import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);


    PostResponse getAllposts(int pageNo, int pageSize, String sortBy, String sortdir);

    PostDto getPostById(long id);


    PostDto updatePost(PostDto postDto, long id);

    void deletePost(long id);
}
