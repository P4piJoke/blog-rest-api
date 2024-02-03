package com.pp4jk.blogrestapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {

    private Long id;

    @NotBlank(message = "Must not be empty")
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @NotBlank(message = "Must not be empty")
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @NotBlank(message = "Must not be empty")
    private String content;
    private Set<CommentDto> comments;
}
