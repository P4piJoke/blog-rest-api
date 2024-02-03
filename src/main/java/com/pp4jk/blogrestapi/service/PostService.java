package com.pp4jk.blogrestapi.service;


import com.pp4jk.blogrestapi.dto.PostDto;
import com.pp4jk.blogrestapi.dto.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePost(Long id);
}
