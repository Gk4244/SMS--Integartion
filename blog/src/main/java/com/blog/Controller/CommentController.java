package com.blog.Controller;

import com.blog.Service.CommentService;
import com.blog.payload.CommentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    // create Comment controller..

    // http://localhost:8080/api/posts/2/comments
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") long postId,
                                                    @RequestBody CommentDto commentDto)
    {
        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //Get all Comments By PostId.

    //http://localhost:8080/api/posts/1/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(
            @PathVariable("postId") long postId){
        List<CommentDto> commentDto = commentService.getCommentBypostId(postId);
        return  commentDto;
    }

    // Get all comments by commentId and PostId..
    //http:localhost:8080/api/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable("postId") long postId,
            @PathVariable("commentId") long commentId
           ){
        CommentDto dto = commentService.getCommentById(postId, commentId);
       return  new ResponseEntity<>(dto, HttpStatus.OK);
    }
    //Developed UpdateComment..

    // http://localhost:8080/api/posts/{postId}/comments/{Id}
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/posts/{postId}/comments/{Id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("postId") Long postId,
            @PathVariable("Id") Long Id,
            @RequestBody CommentDto commentDto
    ){
        CommentDto dto = commentService.updateComment(postId, Id, commentDto);
        return   new ResponseEntity<>(dto,HttpStatus.OK);
    }

    // Developed DeleteComment..

    //http://localhost:8080/api/posts/{postId}/comments/{Id}
      @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/posts/{postId}/comments/{Id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("postId") Long postId,
            @PathVariable("Id") Long Id
    ){
        commentService.deleteComment(postId,Id);
        return new ResponseEntity<>("comment deleted successfully",HttpStatus.OK);
    }

}
