package com.myblog9.service.impl;

import com.myblog9.entity.Comment;
import com.myblog9.entity.Post;
import com.myblog9.exception.ResourceNotFound;
import com.myblog9.payload.CommentDto;
import com.myblog9.repository.CommentRepository;
import com.myblog9.repository.PostRepository;
import com.myblog9.service.CommentService;
import org.apache.catalina.Store;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepo;
    
    private PostRepository postRepo;

    private ModelMapper modelMapper;
    public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo,ModelMapper modelMapper) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFound("post not found" + postId)
        );

        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment c= commentRepo.save(comment);
        return mapToDto(c);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFound("post not found" + postId)
        );
        commentRepo.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getcommentBypostId(long postId) {
        List<Comment> comments = commentRepo.findByPostId(postId);
        List<CommentDto> dto = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    return  dto;
    }

    @Override
    public CommentDto upadate(long commentId, CommentDto commentDto) {
        Comment comment = commentRepo.findById(commentId).get();
        Post post = postRepo.findById(comment.getId()).get();
        Comment com = mapToEntity(commentDto);
        com.setId(commentId);
        com.setPost(post);
        Comment savedcomment = commentRepo.save(com);
        CommentDto dto = mapToDto(savedcomment);
        return dto;
    }


    Comment mapToEntity(CommentDto dto){
        Comment comment = modelMapper.map(dto, Comment.class);
//        Comment comment=new Comment();
//        comment.setName(dto.getName());
//        comment.setEmail(dto.getEmail());
//        comment.setBody(dto.getBody());
           return  comment;
    }

    CommentDto mapToDto(Comment comment ){
        CommentDto dto = modelMapper.map(comment, CommentDto.class);
//        CommentDto dto=new CommentDto();
//        dto.setName(comment.getName());
//        dto.setEmail(comment.getEmail());
//        dto.setBody(comment.getBody());
        return dto;
    }
}
