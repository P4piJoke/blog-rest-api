package com.pp4jk.blogrestapi.service.impl;

import com.pp4jk.blogrestapi.dto.PostDto;
import com.pp4jk.blogrestapi.dto.PostResponse;
import com.pp4jk.blogrestapi.entity.Post;
import com.pp4jk.blogrestapi.exception.ResourceNotFoundException;
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

    private final PostRepository repository;
    private final ModelMapper mapper;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post newPost = repository.save(mapToEntity(postDto));

        return mapToDto(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending()
                :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = repository.findAll(pageable);

        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.
                stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return mapToPostResponse(content, posts);
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = repository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        Post post = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );
        repository.delete(post);
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
        Post converted = mapper.map(dto, Post.class);

//        Post converted = new Post();
//        converted.setTitle(dto.getTitle());
//        converted.setDescription(dto.getDescription());
//        converted.setContent(dto.getContent());
        return converted;
    }

    private PostDto mapToDto(Post entity) {
        PostDto converted = mapper.map(entity, PostDto.class);

//        PostDto converted = new PostDto();
//        converted.setId(entity.getId());
//        converted.setTitle(entity.getTitle());
//        converted.setDescription(entity.getDescription());
//        converted.setContent(entity.getContent());
        return converted;
    }
}
