package com.myblog9.service.impl;

import com.myblog9.entity.Post;
import com.myblog9.exception.ResourceNotFound;
import com.myblog9.payload.PostDto;
import com.myblog9.payload.PostResponse;
import com.myblog9.repository.PostRepository;
import com.myblog9.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedpost = postRepo.save(post);
        PostDto Dto = mapToDto(savedpost);
        return Dto;
    }

    @Override
    public void deletebyid(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("lead is not found id:" + id)
        );
        postRepo.deleteById(id);
    }

    @Override
    public PostDto findbyid(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("post not found:" + id)
        );
        return mapToDto(post);
    }

    @Override
    public PostDto update(long id, PostDto postDto) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("post not found:" + id)
        );
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post savedpost = postRepo.save(post);
        PostDto dto = mapToDto(savedpost);
        return dto;
    }

    @Override
    public PostResponse findalldata(int pageNo, int pagesize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pagesize, sort);
        Page<Post> pagealldata = postRepo.findAll(pageable);
        List<Post> posts = pagealldata.getContent();
        List<PostDto> dto = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse response = new PostResponse();
        response.setDto(dto);
        response.setPageNo(pagealldata.getNumber());
        response.setTotalpages(pagealldata.getTotalPages());
        response.setPageSize(pagealldata.getSize());
        response.setLastpage(pagealldata.isLast());

        return response;
    }


    Post mapToEntity(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        //Post post = new Post();
        //post.setTitle(postDto.getTitle());
        //post.setContent(postDto.getContent());
        //post.setDescription(postDto.getDescription());
        return post;
    }
    PostDto mapToDto(Post savedPost) {
        PostDto postDto = modelMapper.map(savedPost, PostDto.class);
        //PostDto postDto = new PostDto();
        //postDto.setId(savedPost.getId());
        //postDto.setTitle(savedPost.getTitle());
        //postDto.setContent(savedPost.getContent());
        //postDto.setDescription(savedPost.getDescription());
        return postDto;
    }
}
