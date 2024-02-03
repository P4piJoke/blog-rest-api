package com.pp4jk.blogrestapi.service;

import com.pp4jk.blogrestapi.dto.CommentDto;
import com.pp4jk.blogrestapi.entity.Comment;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(Long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);

    void deleteComment(Long postId, Long commentId);
}
