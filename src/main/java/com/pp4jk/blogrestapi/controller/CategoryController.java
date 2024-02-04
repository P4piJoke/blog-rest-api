package com.pp4jk.blogrestapi.controller;

import com.pp4jk.blogrestapi.dto.CategoryDto;
import com.pp4jk.blogrestapi.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(
        name = "CRUD REST APIs for Category Resource"
)
public class CategoryController {

    private final CategoryService service;

    @Operation(
            summary = "Create Category REST API",
            description = "Create Category REST API is used to save category into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(service.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all Categories REST API",
            description = "Get all Categories REST API is used to fetch all categories from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESSFUL"
    )
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(service.getAllCategories());
    }

    @Operation(
            summary = "Get by Id Category REST API",
            description = "Get by Id Category REST API is used to fetch a single category from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESSFUL"
    )
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long categoryId) {
        return ResponseEntity.ok(service.getCategory(categoryId));
    }

    @Operation(
            summary = "Update Category REST API",
            description = "Update Category REST API is used to save an updated category into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESSFUL"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PutMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long categoryId,
                                                      @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(service.updateCategory(categoryId, categoryDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Category REST API",
            description = "Delete Category REST API is used to delete a single category from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESSFUL"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId) {
        service.deleteCategory(categoryId);
        return new ResponseEntity<>("Category entity deleted successfully", HttpStatus.OK);
    }
}
