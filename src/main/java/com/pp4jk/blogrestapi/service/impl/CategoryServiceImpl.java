package com.pp4jk.blogrestapi.service.impl;

import com.pp4jk.blogrestapi.dto.CategoryDto;
import com.pp4jk.blogrestapi.entity.Category;
import com.pp4jk.blogrestapi.exception.ResourceNotFoundException;
import com.pp4jk.blogrestapi.repository.CategoryRepository;
import com.pp4jk.blogrestapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final ModelMapper mapper;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Category category = repository.save(mapToEntity(categoryDto));

        return mapToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = repository.findAll();

        return mapToResponse(categories);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {

        Category category = repository.findById(categoryId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category", "id", categoryId));

        return mapToDto(category);
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = repository.findById(categoryId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category", "id", categoryId));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        Category updatedCategory = repository.save(category);

        return mapToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = repository.findById(categoryId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category", "id", categoryId));

        repository.delete(category);
    }

    private CategoryDto mapToDto(Category category) {
        return mapper.map(category, CategoryDto.class);
    }

    private Category mapToEntity(CategoryDto categoryDto) {
        return mapper.map(categoryDto, Category.class);
    }

    private List<CategoryDto> mapToResponse(List<Category> categories) {
        return categories.stream().map(this::mapToDto).collect(Collectors.toList());
    }
}
