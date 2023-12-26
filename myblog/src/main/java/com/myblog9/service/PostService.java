package com.myblog9.service;

import com.myblog9.payload.PostDto;
import com.myblog9.payload.PostResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface PostService {

    PostDto createPost(PostDto postDto);

    void deletebyid(long id);

    PostDto findbyid(long id);

    PostDto update(long id, PostDto postDto);

    PostResponse findalldata(int pageNo, int pagesize, String sortBy, String sortDir);


}
