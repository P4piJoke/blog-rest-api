package com.pp4jk.blogrestapi.repository;

import com.pp4jk.blogrestapi.entity.Comment;
import com.pp4jk.blogrestapi.entity.Post;
import com.pp4jk.blogrestapi.exception.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.pp4jk.blogrestapi.util.TestConstants.COMMENT_BODY_FIRST;
import static com.pp4jk.blogrestapi.util.TestConstants.COMMENT_BODY_SECOND;
import static com.pp4jk.blogrestapi.util.TestConstants.COMMENT_EMAIL_FIRST;
import static com.pp4jk.blogrestapi.util.TestConstants.COMMENT_EMAIL_SECOND;
import static com.pp4jk.blogrestapi.util.TestConstants.COMMENT_NAME_FIRST;
import static com.pp4jk.blogrestapi.util.TestConstants.COMMENT_NAME_SECOND;
import static com.pp4jk.blogrestapi.util.TestConstants.COMMENT_OBJECT;
import static com.pp4jk.blogrestapi.util.TestConstants.DESCRIPTION_FIRST;
import static com.pp4jk.blogrestapi.util.TestConstants.ID_FIELD;
import static com.pp4jk.blogrestapi.util.TestConstants.POST_CONTENT_FIRST;
import static com.pp4jk.blogrestapi.util.TestConstants.POST_OBJECT;
import static com.pp4jk.blogrestapi.util.TestConstants.POST_TITLE_FIRST;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CommentRepositoryTest {

    private static final int ZERO_VALUE = 0;
    private static final int LENGTH_ONE_VALUE = 1;
    private static final int LENGTH_TWO_VALUE = 2;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private Post post;

    @BeforeEach
    public void createPost() {
        post = new Post();
        post.setTitle(POST_TITLE_FIRST.value());
        post.setDescription(DESCRIPTION_FIRST.value());
        post.setContent(POST_CONTENT_FIRST.value());

        postRepository.save(post);
    }

    @Test
    public void saveShouldReturnSavedComment() {
        // Arrange
        Comment comment = new Comment();
        comment.setName(COMMENT_NAME_FIRST.value());
        comment.setEmail(COMMENT_EMAIL_FIRST.value());
        comment.setBody(COMMENT_BODY_FIRST.value());
        comment.setPost(post);

        // Act
        Comment savedComment = commentRepository.save(comment);

        // Assert
        Assertions.assertThat(savedComment).isNotNull();
        Assertions.assertThat(savedComment.getId()).isGreaterThan(ZERO_VALUE);
    }

    @Test
    public void getAllShouldReturnMoreThanOneComment() {
        // Arrange
        Comment firstComment = new Comment();
        firstComment.setName(COMMENT_NAME_FIRST.value());
        firstComment.setEmail(COMMENT_EMAIL_FIRST.value());
        firstComment.setBody(COMMENT_BODY_FIRST.value());
        firstComment.setPost(post);

        Comment secondComment = new Comment();
        secondComment.setName(COMMENT_NAME_SECOND.value());
        secondComment.setEmail(COMMENT_EMAIL_SECOND.value());
        secondComment.setBody(COMMENT_BODY_SECOND.value());
        secondComment.setPost(post);

        // Act
        commentRepository.save(firstComment);
        commentRepository.save(secondComment);

        List<Comment> commentList = commentRepository.findAll();

        // Assert
        Assertions.assertThat(commentList).isNotNull();
        Assertions.assertThat(commentList.size()).isEqualTo(LENGTH_TWO_VALUE);
    }

    @Test
    public void getByIdShouldReturnComment() {
        // Arrange
        Comment comment = new Comment();
        comment.setName(COMMENT_NAME_FIRST.value());
        comment.setEmail(COMMENT_EMAIL_FIRST.value());
        comment.setBody(COMMENT_BODY_FIRST.value());
        comment.setPost(post);

        // Act
        commentRepository.save(comment);

        Comment retrievedComment = commentRepository.findById(comment.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                POST_OBJECT.value(), ID_FIELD.value(), comment.getId()
                        )
                );

        // Assert
        Assertions.assertThat(retrievedComment).isNotNull();
    }

    @Test
    public void getByPostIdShouldReturnMoreThanOneComment() {
        // Arrange
        Comment comment = new Comment();
        comment.setName(COMMENT_NAME_FIRST.value());
        comment.setEmail(COMMENT_EMAIL_FIRST.value());
        comment.setBody(COMMENT_BODY_FIRST.value());
        comment.setPost(post);

        // Act
        commentRepository.save(comment);

        List<Comment> commentList = commentRepository.findByPostId(post.getId());

        // Assert
        Assertions.assertThat(commentList).isNotNull();
        Assertions.assertThat(commentList.size()).isEqualTo(LENGTH_ONE_VALUE);
    }

    @Test
    public void updateShouldReturnUpdatedComment() {
        // Arrange
        Comment comment = new Comment();
        comment.setName(COMMENT_NAME_FIRST.value());
        comment.setEmail(COMMENT_EMAIL_FIRST.value());
        comment.setBody(COMMENT_BODY_FIRST.value());
        comment.setPost(post);

        // Act
        commentRepository.save(comment);
        Comment commentToUpdate = commentRepository.findById(comment.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                COMMENT_OBJECT.value(), ID_FIELD.value(), comment.getId()
                        )
                );
        commentToUpdate.setBody(COMMENT_BODY_SECOND.value());

        Comment updatedComment = commentRepository.save(commentToUpdate);

        // Assert
        Assertions.assertThat(updatedComment).isNotNull();
        Assertions.assertThat(updatedComment.getBody()).isEqualTo(COMMENT_BODY_SECOND.value());
    }

    @Test
    public void deleteShouldReturnNullComment() {
        // Arrange
        Comment comment = new Comment();
        comment.setName(COMMENT_NAME_FIRST.value());
        comment.setEmail(COMMENT_EMAIL_FIRST.value());
        comment.setBody(COMMENT_BODY_FIRST.value());
        comment.setPost(post);

        // Act
        Comment savedComment = commentRepository.save(comment);
        commentRepository.delete(savedComment);

        Optional<Comment> retrievedComment = commentRepository.findById(comment.getId());

        // Assert
        Assertions.assertThat(retrievedComment).isEmpty();
    }
}
