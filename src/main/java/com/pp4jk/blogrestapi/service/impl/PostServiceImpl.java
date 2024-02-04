package com.pp4jk.blogrestapi.service.impl;

import com.pp4jk.blogrestapi.dto.PostDto;
import com.pp4jk.blogrestapi.dto.PostResponse;
import com.pp4jk.blogrestapi.entity.Category;
import com.pp4jk.blogrestapi.entity.Post;
import com.pp4jk.blogrestapi.exception.ResourceNotFoundException;
import com.pp4jk.blogrestapi.repository.CategoryRepository;
import com.pp4jk.blogrestapi.repository.PostRepository;
import com.pp4jk.blogrestapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId())
                );
        Post post = mapToEntity(postDto);
        post.setCategory(category);

        Post newPost = postRepository.save(post);

        return mapToDto(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending()
                :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = mapToResponse(listOfPosts);

        return mapToPostResponse(content, posts);
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );
        return mapToDto(post);
    }

    @Override
    public List<PostDto> getPostsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category", "id", categoryId)
                );

        List<Post> postList = postRepository.findByCategoryId(categoryId);

        return mapToResponse(postList);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId())
                );
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );
        postRepository.delete(post);
    }

    private List<PostDto> mapToResponse(List<Post> postList) {
        return postList.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private PostResponse mapToPostResponse(List<PostDto> content, Page<Post> posts) {
        return new PostResponse(
                content,
                posts.getNumber(),
                posts.getSize(),
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.isLast()
        );
    }

    private Post mapToEntity(PostDto dto) {
        return mapper.map(dto, Post.class);
    }

    private PostDto mapToDto(Post entity) {
        return mapper.map(entity, PostDto.class);
    }
}
