package com.pp4jk.blogrestapi.repository;

import com.pp4jk.blogrestapi.entity.Category;
import com.pp4jk.blogrestapi.exception.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.pp4jk.blogrestapi.util.TestConstants.CATEGORY_NAME_FIRST;
import static com.pp4jk.blogrestapi.util.TestConstants.CATEGORY_NAME_SECOND;
import static com.pp4jk.blogrestapi.util.TestConstants.CATEGORY_OBJECT;
import static com.pp4jk.blogrestapi.util.TestConstants.DESCRIPTION_FIRST;
import static com.pp4jk.blogrestapi.util.TestConstants.DESCRIPTION_SECOND;
import static com.pp4jk.blogrestapi.util.TestConstants.ID_FIELD;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CategoryRepositoryTest {

    private static final int ZERO_VALUE = 0;
    private static final int LENGTH_TWO_VALUE = 2;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void saveShouldReturnSavedCategory() {
        // Arrange
        Category category = new Category();
        category.setName(CATEGORY_NAME_FIRST.value());
        category.setDescription(DESCRIPTION_FIRST.value());

        // Act
        Category savedCategory = categoryRepository.save(category);

        // Assert
        Assertions.assertThat(savedCategory).isNotNull();
        Assertions.assertThat(savedCategory.getId()).isGreaterThan(ZERO_VALUE);
    }

    @Test
    public void getAllShouldReturnMoreThanOneCategory() {
        // Arrange
        Category firstCategory = new Category();
        firstCategory.setName(CATEGORY_NAME_FIRST.value());
        firstCategory.setDescription(DESCRIPTION_FIRST.value());

        Category secondCategory = new Category();
        secondCategory.setName(CATEGORY_NAME_SECOND.value());
        secondCategory.setDescription(DESCRIPTION_SECOND.value());

        // Act
        categoryRepository.save(firstCategory);
        categoryRepository.save(secondCategory);

        List<Category> categoryList = categoryRepository.findAll();

        // Assert
        Assertions.assertThat(categoryList).isNotNull();
        Assertions.assertThat(categoryList.size()).isEqualTo(LENGTH_TWO_VALUE);
    }

    @Test
    public void getByIdShouldReturnCategory() {
        // Arrange
        Category category = new Category();
        category.setName(CATEGORY_NAME_FIRST.value());
        category.setDescription(DESCRIPTION_FIRST.value());

        // Act
        categoryRepository.save(category);

        Category retrievedCategory = categoryRepository.findById(category.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                CATEGORY_OBJECT.value(), ID_FIELD.value(), category.getId()
                        )
                );

        // Assert
        Assertions.assertThat(retrievedCategory).isNotNull();
    }

    @Test
    public void updateShouldReturnUpdatedCategory() {
        // Arrange
        Category category = new Category();
        category.setName(CATEGORY_NAME_FIRST.value());
        category.setDescription(DESCRIPTION_FIRST.value());

        // Act
        categoryRepository.save(category);
        Category categoryToUpdate = categoryRepository.findById(category.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                CATEGORY_OBJECT.value(), ID_FIELD.value(), category.getId()
                        )
                );
        categoryToUpdate.setDescription(DESCRIPTION_SECOND.value());

        Category updatedCategory = categoryRepository.save(categoryToUpdate);

        // Assert
        Assertions.assertThat(updatedCategory).isNotNull();
        Assertions.assertThat(updatedCategory.getDescription()).isEqualTo(DESCRIPTION_SECOND.value());
    }

    @Test
    public void deleteShouldReturnNullCategory() {
        // Arrange
        Category category = new Category();
        category.setName(CATEGORY_NAME_FIRST.value());
        category.setDescription(DESCRIPTION_FIRST.value());

        // Act
        Category savedCategory = categoryRepository.save(category);
        categoryRepository.delete(savedCategory);

        Optional<Category> retrievedCategory = categoryRepository.findById(category.getId());

        // Assert
        Assertions.assertThat(retrievedCategory).isEmpty();
    }
}
