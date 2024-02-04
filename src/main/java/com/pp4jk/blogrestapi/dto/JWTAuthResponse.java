package com.pp4jk.blogrestapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "JWTAuthResponse Model Information"
)
public class JWTAuthResponse {

    @Schema(
            description = "Blog JWT Token"
    )
    private String accessToken;

    @Schema(
            description = "Blog Token Type"
    )
    private String tokenType = "Bearer";
}
