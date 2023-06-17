package com.blog.Controller;

import com.blog.Service.PostService;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;


import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    //http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto,BindingResult result)
    {
        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().
                        getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto , HttpStatus.CREATED);
    }
    // Getall concept and pagination concept..

    //http:localhost:8080/api/posts?pageNo=1&pageSize=5&sortBy=title&sortDir=asc

    @GetMapping
    public PostResponse getAllposts(
            //pageNo...
            @RequestParam(value = "pageNo", defaultValue = "0", required = false)  int pageNo,

            //pageize...
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,

            //sortBy title,id,content,description
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,

            // sort Diraction by asc and Dsc..
             @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortdir

    ){
        PostResponse postResponse = postService.getAllposts(pageNo,pageSize,sortBy,sortdir);
        return postResponse;
    }
    // Now WE create Get mapping ByID:-
    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=id&sortDir=dsc
    @GetMapping("/{id}")
   public ResponseEntity<PostDto> getPostById(@PathVariable ("id") long id) {
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    //Create Update Controller
   // http:localhost:8080/api/posts/{id}
   @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
                                               @PathVariable("id") long id){
        postService.updatePost(postDto,id);
        return  new ResponseEntity<>(postDto ,HttpStatus.OK);
    }
    //create Delete controller..

    // http://localhost:8080/api/posts/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deletePost(
            @PathVariable("id") long id){
        postService.deletePost(id);
        return  new ResponseEntity<>("Post is deleted", HttpStatus.OK);
    }
    }



