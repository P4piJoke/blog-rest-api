package com.pp4jk.blogrestapi.repository;

import com.pp4jk.blogrestapi.entity.User;
import com.pp4jk.blogrestapi.exception.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.pp4jk.blogrestapi.util.TestConstants.ID_FIELD;
import static com.pp4jk.blogrestapi.util.TestConstants.ROLE_OBJECT;
import static com.pp4jk.blogrestapi.util.TestConstants.USER_EMAIL_FIRST;
import static com.pp4jk.blogrestapi.util.TestConstants.USER_EMAIL_SECOND;
import static com.pp4jk.blogrestapi.util.TestConstants.USER_NAME_FIRST;
import static com.pp4jk.blogrestapi.util.TestConstants.USER_NAME_SECOND;
import static com.pp4jk.blogrestapi.util.TestConstants.USER_OBJECT;
import static com.pp4jk.blogrestapi.util.TestConstants.USER_PASSWORD_FIRST;
import static com.pp4jk.blogrestapi.util.TestConstants.USER_PASSWORD_SECOND;
import static com.pp4jk.blogrestapi.util.TestConstants.USER_USERNAME_FIRST;
import static com.pp4jk.blogrestapi.util.TestConstants.USER_USERNAME_SECOND;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    private static final int ZERO_VALUE = 0;
    private static final int LENGTH_TWO_VALUE = 2;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveShouldReturnSavedUser() {
        // Arrange
        User user = new User();
        user.setName(USER_NAME_FIRST.value());
        user.setUsername(USER_USERNAME_FIRST.value());
        user.setEmail(USER_EMAIL_FIRST.value());
        user.setPassword(USER_PASSWORD_FIRST.value());

        // Act
        User savedUser = userRepository.save(user);

        // Assert
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(ZERO_VALUE);
    }

    @Test
    public void getAllShouldReturnMoreThanOneUser() {
        // Arrange
        User admin = new User();
        admin.setName(USER_NAME_FIRST.value());
        admin.setUsername(USER_USERNAME_FIRST.value());
        admin.setEmail(USER_EMAIL_FIRST.value());
        admin.setPassword(USER_PASSWORD_FIRST.value());

        User user = new User();
        user.setName(USER_NAME_SECOND.value());
        user.setUsername(USER_USERNAME_SECOND.value());
        user.setEmail(USER_EMAIL_SECOND.value());
        user.setPassword(USER_PASSWORD_SECOND.value());

        // Act
        userRepository.save(admin);
        userRepository.save(user);

        List<User> userList = userRepository.findAll();

        // Assert
        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(LENGTH_TWO_VALUE);
    }

    @Test
    public void getByIdShouldReturnUser() {
        // Arrange
        User user = new User();
        user.setName(USER_NAME_FIRST.value());
        user.setUsername(USER_USERNAME_FIRST.value());
        user.setEmail(USER_EMAIL_FIRST.value());
        user.setPassword(USER_PASSWORD_FIRST.value());

        // Act
        userRepository.save(user);

        User retrievedUser = userRepository.findById(user.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                USER_OBJECT.value(), ID_FIELD.value(), user.getId()
                        )
                );

        // Assert
        Assertions.assertThat(retrievedUser).isNotNull();
    }

    @Test
    public void getByEmailShouldReturnUser() {
        // Arrange
        User user = new User();
        user.setName(USER_NAME_FIRST.value());
        user.setUsername(USER_USERNAME_FIRST.value());
        user.setEmail(USER_EMAIL_FIRST.value());
        user.setPassword(USER_PASSWORD_FIRST.value());

        // Act
        userRepository.save(user);

        User retrievedUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                ROLE_OBJECT.value(), ID_FIELD.value(), user.getId()
                        )
                );

        // Assert
        Assertions.assertThat(retrievedUser).isNotNull();
    }

    @Test
    public void getByUsernameShouldReturnUser() {
        // Arrange
        User user = new User();
        user.setName(USER_NAME_FIRST.value());
        user.setUsername(USER_USERNAME_FIRST.value());
        user.setEmail(USER_EMAIL_FIRST.value());
        user.setPassword(USER_PASSWORD_FIRST.value());

        // Act
        userRepository.save(user);

        User retrievedUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                USER_OBJECT.value(), ID_FIELD.value(), user.getId()
                        )
                );

        // Assert
        Assertions.assertThat(retrievedUser).isNotNull();
    }

    @Test
    public void getByUsernameOrEmailShouldReturnUser() {
        // Arrange
        User user = new User();
        user.setName(USER_NAME_FIRST.value());
        user.setUsername(USER_USERNAME_FIRST.value());
        user.setEmail(USER_EMAIL_FIRST.value());
        user.setPassword(USER_PASSWORD_FIRST.value());

        // Act
        userRepository.save(user);

        User retrievedUser = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                ROLE_OBJECT.value(), ID_FIELD.value(), user.getId()
                        )
                );

        // Assert
        Assertions.assertThat(retrievedUser).isNotNull();
    }

    @Test
    public void existsByUsernameShouldReturnTrueForNotNullUser() {
        // Arrange
        User user = new User();
        user.setName(USER_NAME_FIRST.value());
        user.setUsername(USER_USERNAME_FIRST.value());
        user.setEmail(USER_EMAIL_FIRST.value());
        user.setPassword(USER_PASSWORD_FIRST.value());

        // Act
        userRepository.save(user);

        Boolean existsByUsername = userRepository.existsByUsername(user.getUsername());

        // Assert
        Assertions.assertThat(existsByUsername).isTrue();
    }

    @Test
    public void existsByUsernameShouldReturnFalseForNullUser() {
        // Arrange
        User user = new User();
        user.setName(USER_NAME_FIRST.value());
        user.setUsername(USER_USERNAME_FIRST.value());
        user.setEmail(USER_EMAIL_FIRST.value());
        user.setPassword(USER_PASSWORD_FIRST.value());

        // Act
        Boolean existsByUsername = userRepository.existsByUsername(user.getUsername());

        // Assert
        Assertions.assertThat(existsByUsername).isFalse();
    }

    @Test
    public void existsByEmailShouldReturnTrueForNotNullUser() {
        // Arrange
        User user = new User();
        user.setName(USER_NAME_FIRST.value());
        user.setUsername(USER_USERNAME_FIRST.value());
        user.setEmail(USER_EMAIL_FIRST.value());
        user.setPassword(USER_PASSWORD_FIRST.value());

        // Act
        userRepository.save(user);

        Boolean existsByEmail = userRepository.existsByEmail(user.getEmail());

        // Assert
        Assertions.assertThat(existsByEmail).isTrue();
    }

    @Test
    public void existsByEmailShouldReturnFalseForNullUser() {
        // Arrange
        User user = new User();
        user.setName(USER_NAME_FIRST.value());
        user.setUsername(USER_USERNAME_FIRST.value());
        user.setEmail(USER_EMAIL_FIRST.value());
        user.setPassword(USER_PASSWORD_FIRST.value());

        // Act
        Boolean existsByEmail = userRepository.existsByEmail(user.getEmail());

        // Assert
        Assertions.assertThat(existsByEmail).isFalse();
    }

    @Test
    public void updateShouldReturnUpdatedUser() {
        // Arrange
        User user = new User();
        user.setName(USER_NAME_FIRST.value());
        user.setUsername(USER_USERNAME_FIRST.value());
        user.setEmail(USER_EMAIL_FIRST.value());
        user.setPassword(USER_PASSWORD_FIRST.value());

        // Act
        userRepository.save(user);
        User userToUpdate = userRepository.findById(user.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                ROLE_OBJECT.value(), ID_FIELD.value(), user.getId()
                        )
                );
        userToUpdate.setName(USER_NAME_SECOND.value());

        User updatedUser = userRepository.save(userToUpdate);

        // Assert
        Assertions.assertThat(updatedUser).isNotNull();
        Assertions.assertThat(updatedUser.getName()).isEqualTo(USER_NAME_SECOND.value());
    }

    @Test
    public void deleteShouldReturnNullUser() {
        // Arrange
        User user = new User();
        user.setName(USER_NAME_FIRST.value());
        user.setUsername(USER_USERNAME_FIRST.value());
        user.setEmail(USER_EMAIL_FIRST.value());
        user.setPassword(USER_PASSWORD_FIRST.value());

        // Act
        User savedUser = userRepository.save(user);
        userRepository.delete(savedUser);

        Optional<User> retrievedUser = userRepository.findById(user.getId());

        // Assert
        Assertions.assertThat(retrievedUser).isEmpty();
    }
}
