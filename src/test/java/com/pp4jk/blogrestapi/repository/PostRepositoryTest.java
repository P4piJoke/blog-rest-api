package com.pp4jk.blogrestapi.repository;

import com.pp4jk.blogrestapi.entity.Category;
import com.pp4jk.blogrestapi.entity.Post;
import com.pp4jk.blogrestapi.exception.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.pp4jk.blogrestapi.util.TestConstants.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PostRepositoryTest {

    private static final int ZERO_VALUE = 0;
    private static final int LENGTH_ONE_VALUE = 1;
    private static final int LENGTH_TWO_VALUE = 2;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void saveShouldReturnSavedPost() {
        // Arrange
        Post post = new Post();
        post.setTitle(POST_TITLE_FIRST.value());
        post.setDescription(DESCRIPTION_FIRST.value());
        post.setContent(POST_CONTENT_FIRST.value());

        // Act
        Post savedPost = postRepository.save(post);

        // Assert
        Assertions.assertThat(savedPost).isNotNull();
        Assertions.assertThat(savedPost.getId()).isGreaterThan(ZERO_VALUE);
    }

    @Test
    public void getAllShouldReturnMoreThanOnePost(){
        // Arrange
        Post firstPost = new Post();
        firstPost.setTitle(POST_TITLE_FIRST.value());
        firstPost.setDescription(DESCRIPTION_FIRST.value());
        firstPost.setContent(POST_CONTENT_FIRST.value());

        Post secondPost = new Post();
        secondPost.setTitle(POST_TITLE_SECOND.value());
        secondPost.setDescription(DESCRIPTION_SECOND.value());
        secondPost.setContent(POST_CONTENT_SECOND.value());

        // Act
        postRepository.save(firstPost);
        postRepository.save(secondPost);

        List<Post> postList = postRepository.findAll();

        // Assert
        Assertions.assertThat(postList).isNotNull();
        Assertions.assertThat(postList.size()).isEqualTo(LENGTH_TWO_VALUE);
    }

    @Test
    public void getByIdShouldReturnPost(){
        // Arrange
        Post post = new Post();
        post.setTitle(POST_TITLE_FIRST.value());
        post.setDescription(DESCRIPTION_FIRST.value());
        post.setContent(POST_CONTENT_FIRST.value());

        // Act
        postRepository.save(post);

        Post retrievedPost = postRepository.findById(post.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                POST_OBJECT.value(), ID_FIELD.value(), post.getId()
                        )
                );

        // Assert
        Assertions.assertThat(retrievedPost).isNotNull();
    }

    @Test
    public void getByCategoryIdShouldReturnPost(){
        // Arrange
        Category category = new Category();
        category.setName(CATEGORY_NAME_FIRST.value());
        category.setDescription(DESCRIPTION_FIRST.value());

        Post post = new Post();
        post.setTitle(POST_TITLE_FIRST.value());
        post.setDescription(DESCRIPTION_FIRST.value());
        post.setContent(POST_CONTENT_FIRST.value());
        post.setCategory(category);

        // Act
        categoryRepository.save(category);
        postRepository.save(post);

        List<Post> postList = postRepository.findByCategoryId(category.getId());

        // Assert
        Assertions.assertThat(postList).isNotNull();
        Assertions.assertThat(postList.size()).isEqualTo(LENGTH_ONE_VALUE);
    }

    @Test
    public void updateShouldReturnUpdatedPost() {
        // Arrange
        Post post = new Post();
        post.setTitle(POST_TITLE_FIRST.value());
        post.setDescription(DESCRIPTION_FIRST.value());
        post.setContent(POST_CONTENT_FIRST.value());

        // Act
        postRepository.save(post);
        Post postToUpdate = postRepository.findById(post.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                POST_OBJECT.value(), ID_FIELD.value(), post.getId()
                        )
                );
        postToUpdate.setDescription(DESCRIPTION_SECOND.value());

        Post updatedPost = postRepository.save(postToUpdate);

        // Assert
        Assertions.assertThat(updatedPost).isNotNull();
        Assertions.assertThat(updatedPost.getDescription()).isEqualTo(DESCRIPTION_SECOND.value());
    }

    @Test
    public void deleteShouldReturnNullPost(){
        // Arrange
        Post post = new Post();
        post.setTitle(POST_TITLE_FIRST.value());
        post.setDescription(DESCRIPTION_FIRST.value());
        post.setContent(POST_CONTENT_FIRST.value());

        // Act
        Post savedPost = postRepository.save(post);
        postRepository.delete(savedPost);

        Optional<Post> retrievedPost = postRepository.findById(post.getId());

        // Assert
        Assertions.assertThat(retrievedPost).isEmpty();
    }
}
