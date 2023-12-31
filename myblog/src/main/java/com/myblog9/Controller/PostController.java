package com.myblog9.Controller;

import com.myblog9.payload.PostDto;
import com.myblog9.payload.PostResponse;
import com.myblog9.service.PostService;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
       PostDto dto= postService.createPost(postDto);
       return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id){
     postService.deletebyid(id);
     return  new ResponseEntity<>("post is deleted",HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<PostDto> getpostbyid(@PathVariable long id){
       PostDto postdto= postService.findbyid(id);
       return new ResponseEntity<>(postdto,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<PostDto> updatepost(@PathVariable long id,@RequestBody PostDto postDto){
       PostDto dto= postService.update(id,postDto);
       return new ResponseEntity<>(dto,HttpStatus.OK);
    }


    //http://localhost:8080/api/posts?pageNo=1&pageSize=3?sortBy=title&sortDir=desc
    @GetMapping
    public PostResponse findalldata(
            @RequestParam(name = "pageNo",required = false,defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize",required = false,defaultValue = "5") int pagesize,
            @RequestParam(value="sortBy",required = false,defaultValue = "id") String sortBy,
            @RequestParam(value="sortDir",required = false,defaultValue ="asc") String sortDir

    ){
        PostResponse response=postService.findalldata(pageNo,pagesize,sortBy,sortDir);
        return response;
    }
}
