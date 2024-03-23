package com.pp4jk.blogrestapi.repository;

import com.pp4jk.blogrestapi.entity.Role;
import com.pp4jk.blogrestapi.exception.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.pp4jk.blogrestapi.util.TestConstants.ADMIN_ROLE;
import static com.pp4jk.blogrestapi.util.TestConstants.CATEGORY_NAME_FIRST;
import static com.pp4jk.blogrestapi.util.TestConstants.ID_FIELD;
import static com.pp4jk.blogrestapi.util.TestConstants.ROLE_OBJECT;
import static com.pp4jk.blogrestapi.util.TestConstants.USER_ROLE;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RoleRepositoryTest {
    private static final int ZERO_VALUE = 0;
    private static final int LENGTH_TWO_VALUE = 2;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void saveShouldReturnSavedRole() {
        // Arrange
        Role role = new Role();
        role.setName(ADMIN_ROLE.value());

        // Act
        Role savedRole = roleRepository.save(role);

        // Assert
        Assertions.assertThat(savedRole).isNotNull();
        Assertions.assertThat(savedRole.getId()).isGreaterThan(ZERO_VALUE);
    }

    @Test
    public void getAllShouldReturnMoreThanOneRole() {
        // Arrange
        Role admin = new Role();
        admin.setName(ADMIN_ROLE.value());

        Role user = new Role();
        user.setName(USER_ROLE.value());

        // Act
        roleRepository.save(admin);
        roleRepository.save(user);

        List<Role> roleList = roleRepository.findAll();

        // Assert
        Assertions.assertThat(roleList).isNotNull();
        Assertions.assertThat(roleList.size()).isEqualTo(LENGTH_TWO_VALUE);
    }

    @Test
    public void getByIdShouldReturnRole() {
        // Arrange
        Role role = new Role();
        role.setName(ADMIN_ROLE.value());

        // Act
        roleRepository.save(role);

        Role retrievedRole = roleRepository.findById(role.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                ROLE_OBJECT.value(), ID_FIELD.value(), role.getId()
                        )
                );

        // Assert
        Assertions.assertThat(retrievedRole).isNotNull();
    }

    @Test
    public void getByNameShouldReturnRole() {
        // Arrange
        Role role = new Role();
        role.setName(ADMIN_ROLE.value());

        // Act
        roleRepository.save(role);

        Role retrievedRole = roleRepository.findByName(role.getName())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                ROLE_OBJECT.value(), ID_FIELD.value(), role.getId()
                        )
                );

        // Assert
        Assertions.assertThat(retrievedRole).isNotNull();
    }

    @Test
    public void updateShouldReturnUpdatedRole() {
        // Arrange
        Role role = new Role();
        role.setName(USER_ROLE.value());

        // Act
        roleRepository.save(role);
        Role roleToUpdate = roleRepository.findById(role.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                ROLE_OBJECT.value(), ID_FIELD.value(), role.getId()
                        )
                );
        roleToUpdate.setName(ADMIN_ROLE.value());

        Role updatedRole = roleRepository.save(roleToUpdate);

        // Assert
        Assertions.assertThat(updatedRole).isNotNull();
        Assertions.assertThat(updatedRole.getName()).isEqualTo(ADMIN_ROLE.value());
    }

    @Test
    public void deleteShouldReturnNullRole() {
        // Arrange
        Role role = new Role();
        role.setName(CATEGORY_NAME_FIRST.value());

        // Act
        Role savedRole = roleRepository.save(role);
        roleRepository.delete(savedRole);

        Optional<Role> retrievedRole = roleRepository.findById(role.getId());

        // Assert
        Assertions.assertThat(retrievedRole).isEmpty();
    }
}
