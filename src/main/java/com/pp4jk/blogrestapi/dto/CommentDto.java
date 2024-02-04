package com.pp4jk.blogrestapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        description = "CommentDto Model Information"
)
public class CommentDto {

    private Long id;

    @Schema(
            description = "Blog Comment Sender Name"
    )
    @NotBlank(message = "Name shouldn't be empty")
    private String name;

    @Schema(
            description = "Blog Comment Sender Email"
    )
    @NotBlank(message = "Email shouldn't be empty")
    @Email(message = "Should looks like email pattern")
    private String email;

    @Schema(
            description = "Blog Comment Body"
    )
    @NotBlank(message = "Body shouldn't be empty")
    @Size(min = 10, message = "Body should have at leas 10 characters")
    private String body;
}
