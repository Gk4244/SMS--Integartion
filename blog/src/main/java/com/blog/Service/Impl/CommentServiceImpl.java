package com.blog.Service.Impl;

import com.blog.Service.CommentService;
import com.blog.entites.Comment;
import com.blog.entites.Post;
import com.blog.execption.BlogAPIException;
import com.blog.execption.ResourceNotFoundExecption;
import com.blog.payload.CommentDto;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;
import com.sun.xml.bind.v2.schemagen.episode.SchemaBindings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;

       private ModelMapper mapper;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @Override
        public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundExecption("Post not found with id :" +postId)
        );

        Comment comment =mapToEntity(commentDto);
        // set post to comment entity
        comment.setPost(post);
        //save comment..
        Comment newComment =  commentRepository.save(comment);
        //return entity to Dto save.
        return mapToDto(newComment);
        //  OR  CommentDto commentDto1 = mapToDto(newComment);   // return commentDto1;


    }

//  Get All Comment By PostId.....
    @Override
    public List<CommentDto> getCommentBypostId(long postId) {
        // retrieve Comment ByPostId..
        List<Comment> comments = commentRepository.findByPostId(postId);
        //convert List of all Comment entity to  CommentDto..
        List<CommentDto> collect = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        //retrieve post entity by id.

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundExecption("Post not found with id :" +postId)
        );
        //retrieve comment by id..
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundExecption("Comment not Found with id :" +commentId)
        );

        //CommentId belongs to PostId if not match then they will gives blog ApiException.
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("Comment does not belong to post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long Id, CommentDto commentDto) {
        // firstly we check weather post exist or not--------
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundExecption("post not found with Id :" +postId)
        );

        // Second check weather comment exist or not.------------
        Comment comment = commentRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundExecption("Comment not found with commentId:"+Id)
        );
       //  commentId and postId should be match if not match then gives execution..
        if(!comment.getPost().getId().equals(post.getId())
        ) {
            throw new BlogAPIException("Comment does not belongs to post");
        }
      // Now we will set and get for update data.-----------
            comment.setName(commentDto.getName());
            comment.setEmail(commentDto.getEmail());
            comment.setBody(commentDto.getBody());
      // now save comment..
        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment) ;
    }

    @Override
    public void deleteComment(Long postId, Long Id) {
        // firstly we check weather post exist or not.
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundExecption("post not found with id :" +postId)
        );

        // Second check weather comment exist or not.
        Comment comment = commentRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundExecption("Comment not found with commentId:"+Id)
        );
        //  commentId and postId should be match if not match then gives execution..
        if(!comment.getPost().getId().equals(post.getId())
        ) {
            throw new BlogAPIException("Comment does not belongs to post");
        }
        commentRepository.deleteById(Id);
    }

//convert Dto to Entity---
  private  Comment mapToEntity(CommentDto commentDto){


            //ModelMapper concept---
      Comment comment = mapper.map(commentDto, Comment.class);

//            Comment comment = new Comment();
//            comment.setName(commentDto.getName());
//            comment.setEmail(commentDto.getEmail());
//            comment.setBody(commentDto.getBody());
            return comment;
  }

    CommentDto mapToDto(Comment comment ){
        //ModelMapper concept--
         CommentDto dto = mapper.map(comment, CommentDto.class);
//        CommentDto dto = new CommentDto();
//        dto.setId(comment.getId());
//        dto.setName(comment.getName());
//        dto.setEmail(comment.getEmail());
//        dto.setBody(comment.getBody());
        return dto;
    }
}


