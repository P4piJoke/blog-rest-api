package com.pp4jk.blogrestapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    private Long id;

    @NotBlank(message = "Name shouldn't be empty")
    private String name;

    @NotBlank(message = "Email shouldn't be empty")
    @Email(message = "Should looks like email pattern")
    private String email;

    @NotBlank(message = "Body shouldn't be empty")
    @Size(min = 10, message = "Body should have at leas 10 characters")
    private String body;
}
