package com.blog.Service.Impl;

import com.blog.Service.PostService;
import com.blog.entites.Post;
import com.blog.execption.ResourceNotFoundExecption;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
   // @Autowired
private PostRepository postRepository;

// Using Model Mapper-----
private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // convert DTO to entity
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);

        // convert entity to DTO
        PostDto postResponse = mapToDTO(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllposts(int pageNo, int pageSize, String sortBy, String sortdir) {
        //it is just like if else condition  ,, if it is true then sort by asc and false then print dsc..
        Sort sort = sortdir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending()   : Sort.by(sortBy).descending();

        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Post> content = postRepository.findAll(pageable);
        List<Post> posts = content.getContent();
        List<PostDto> dtos= posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        //Pageno in order way.
        PostResponse postResponse= new PostResponse();
        postResponse.setContent(dtos);
        postResponse.setPageNo(content.getNumber());
        postResponse.setPageSize(content.getSize());
        postResponse.setTotalPages(content.getTotalPages());
        postResponse.setTotalElement(content.getTotalElements());
        postResponse.setLast(content.isLast());
        return postResponse;

    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundExecption("Post not found with is:"+id)

        );
        // convert Entity to Dto
        PostDto postDto = mapToDTO(post);
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundExecption("Post not found with id:" + id)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        // the the post..
        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundExecption("Post not found with id:" + id)
        );
        postRepository.deleteById(id);

    }


    // convert  Dto to Entity
    private Post mapToEntity(PostDto postDto){

        // now work ModelMapper---
       Post post = mapper.map(postDto, Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
    // convert DTO to entity
    private PostDto mapToDTO(Post post){
        //now ModelMapper---
       PostDto Dto = mapper.map(post, PostDto.class);
//        PostDto Dto = new PostDto();
//        Dto.setId(post.getId());
//        Dto.setTitle(post.getTitle());
//        Dto.setDescription(post.getDescription());
//        Dto.setContent(post.getContent());
        return Dto;
    }



}



