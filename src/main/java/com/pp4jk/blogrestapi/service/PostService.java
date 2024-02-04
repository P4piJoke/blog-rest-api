package com.pp4jk.blogrestapi.service;


import com.pp4jk.blogrestapi.dto.PostDto;
import com.pp4jk.blogrestapi.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);
    List<PostDto> getPostsByCategoryId(Long categoryId);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePost(Long id);
}
