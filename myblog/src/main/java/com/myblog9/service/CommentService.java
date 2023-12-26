package com.myblog9.service;

import com.myblog9.payload.CommentDto;

import java.util.List;

public interface CommentService {

    public CommentDto createComment(long postId, CommentDto commentDto);

  public void deleteComment(long postId, long commentId);

    List<CommentDto> getcommentBypostId(long postId);

    CommentDto upadate(long commentId, CommentDto commentDto);
}
