package com.myblog9.Controller;

import com.myblog9.payload.CommentDto;
import com.myblog9.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/comments?postId=1
    @PostMapping
    public ResponseEntity<CommentDto> createComment(
            @RequestParam long postId,@RequestBody CommentDto commentDto
    ){
        CommentDto commentdto = commentService.createComment(postId, commentDto);
   return  new ResponseEntity<>(commentdto, HttpStatus.CREATED);
    }

   //http://localhost:8080/api/comments?postId=1&commentId=1
    @DeleteMapping
    public ResponseEntity<String> deleteComment(
            @RequestParam long postId,@RequestParam long commentId
    ){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("comment is deleted",HttpStatus.OK);
    }

    //http://localhost:8080/api/comments?postId=1
    @GetMapping
    public List<CommentDto> findbyid(
        @RequestParam long postId){
   List<CommentDto> dto= commentService.getcommentBypostId(postId);
   return dto;
        }


        //http://localhost:8080/api/comments?commentId=1
        @PutMapping
        public ResponseEntity<CommentDto> updatecomment(
                @RequestParam long commentId,@RequestBody CommentDto commentDto
        ){
       CommentDto dto= commentService.upadate(commentId,commentDto);
       return  new ResponseEntity<>(dto,HttpStatus.OK);
        }
    }


