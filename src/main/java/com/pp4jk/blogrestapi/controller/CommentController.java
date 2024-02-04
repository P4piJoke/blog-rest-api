package com.pp4jk.blogrestapi.controller;

import com.pp4jk.blogrestapi.dto.CommentDto;
import com.pp4jk.blogrestapi.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(
        name = "CRUD REST APIs for Comment Resource"
)
public class CommentController {

    private final CommentService service;

    @Operation(
            summary = "Create Comment REST API",
            description = "Create Comment REST API is used to save comment into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long postId,
                                                    @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(service.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all by Post Id Comment REST API",
            description = "Get all by Post Id Comment REST API is used to fetch comments by post id from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESSFUL"
    )
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(service.getCommentsByPostId(postId));
    }

    @Operation(
            summary = "Get by Id Comment REST API",
            description = "Get by Id Comment REST API is used to fetch a single comment from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESSFUL"
    )
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long postId,
                                                     @PathVariable Long commentId) {
        return ResponseEntity.ok(service.getCommentById(postId, commentId));
    }

    @Operation(
            summary = "Update Comment REST API",
            description = "Update Comment REST API is used to save an updated comment into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESSFUL"
    )
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long postId,
                                                    @PathVariable Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(service.updateComment(postId, commentId, commentDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Comment REST API",
            description = "Delete Comment REST API is used to delete a single comment from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESSFUL"
    )
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId,
                                                @PathVariable Long commentId) {
        service.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }
}
