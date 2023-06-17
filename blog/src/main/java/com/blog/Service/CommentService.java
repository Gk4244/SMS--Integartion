package com.blog.Service;

import com.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {





    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentBypostId(long postId);
    //Get comment by id
    CommentDto getCommentById(Long postId, Long commentId);
// UpdateComment..

    CommentDto updateComment(Long postId, Long Id, CommentDto commentDto);

    void deleteComment(Long postId, Long Id);
}
