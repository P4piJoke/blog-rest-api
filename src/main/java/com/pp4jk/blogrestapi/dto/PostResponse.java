package com.pp4jk.blogrestapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "PostResponse Model Information"
)
public class PostResponse {

    @Schema(
            description = "Blog Posts List"
    )
    private List<PostDto> content;

    @Schema(
            description = "Blog Posts Page Number"
    )
    private int pageNo;

    @Schema(
            description = "Blog Posts Page Size"
    )
    private int pageSize;

    @Schema(
            description = "Blog Post Elements Number"
    )
    private Long totalElements;

    @Schema(
            description = "Blog Post Page Count"
    )
    private int totalPages;

    @Schema(
            description = "Blog Is Last Page with Posts"
    )
    private boolean last;
}
