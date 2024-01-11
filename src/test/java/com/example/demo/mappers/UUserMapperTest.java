package com.example.demo.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.builder.UUserBuilder;
import com.example.demo.entity.UUser;

@SpringBootTest
@Transactional
public class UUserMapperTest {
	
	@Autowired
	private UUserMapper userMapper;

	@Test
	void testFindUserByEmail_ValidEmail_ShouldReturnNonEmptyOptional() {
		// GIVEN
		String userEmail = "john@test.fr";

		// WHEN
		Optional<UUser> userOptional = userMapper.findUserByEmail(userEmail);

		// THEN
		assertThat(userOptional).isNotNull().isPresent();
	}

	@Test
	void testFindUserByEmail_InvalidEmail_ShouldReturnEmptyOptional() {
		// GIVEN
		String invalidUserEmail = "invalid@example.com";

		// WHEN
		Optional<UUser> userOptional = userMapper.findUserByEmail(invalidUserEmail);

		// THEN
		assertThat(userOptional).isNotNull().isEmpty();
	}
	
	@Test
	void testFindUserById_ValidId_ShouldReturnNonEmptyOptional() {
		// GIVEN
		int userId = 1;

		// WHEN
		Optional<UUser> userOptional = userMapper.findUserById(userId);

		// THEN
		assertThat(userOptional).isNotNull().isPresent();
	}

	@Test
	void testFindUserById_ValidId_ShouldReturnEmptyOptional() {
		// GIVEN
		int invalidUserId = -1;

		// WHEN
		Optional<UUser> userOptional = userMapper.findUserById(invalidUserId);

		// THEN
		assertThat(userOptional).isNotNull().isEmpty();
	}
	
	 @Test
    void testInsertUser_ShouldInsertUser() {
        // GIVEN
    	String dateString = "2024-01-11T16:14:26.537365700";
    	LocalDateTime dateTime = dateFormatter(dateString);
    	
        UUser userToInsert = new UUserBuilder()
        		.withUEmail("newuser@example.com")
        		.withUPassword("password123")
        		.withUFirstName("John")
        		.withULastName("Doe")
        		.withUStatus(true)
        		.withUInsertionDate(dateTime)
        		.withULastUpdate(dateTime)
        		.build();

        // WHEN
        userMapper.insertUser(userToInsert);

        // THEN
        assertThat(userToInsert.getUId()).isPositive();

        UUser retrievedUser = userMapper.findUserByEmail("newuser@example.com").orElse(null);
        assertNotNull(retrievedUser);
        assertEquals(userToInsert.getUId(), retrievedUser.getUId());
        assertEquals(userToInsert.getUEmail(), retrievedUser.getUEmail());
        assertEquals(userToInsert.getUFirstName(), retrievedUser.getUFirstName());
    }
	 
	 @Test
    void testInsertUser_DuplicateEmail_ShouldThrowException() {
        // GIVEN
        UUser userToInsert1 = new UUserBuilder()
        		.withUEmail("duplicate@example.com")
        		.withUPassword("password123")
        		.withUFirstName("John")
        		.withULastName("Doe")
        		.withUStatus(true)
        		.withUInsertionDate(LocalDateTime.now())
        		.withULastUpdate(LocalDateTime.now())
        		.build();

        UUser userToInsert2 = new UUserBuilder()
        		.withUEmail("duplicate@example.com").build();

        // WHEN
        userMapper.insertUser(userToInsert1);

        // THEN
        assertThrows(Exception.class, () -> userMapper.insertUser(userToInsert2));
    }
	 
	 @Test
	 public void testUpdatePassword_ShouldUpdatePassword() {
		// GIVEN
        UUser userToUpdate = new UUserBuilder()
        		.withUEmail("userToUpdate@example.com")
        		.withUPassword("password123")
        		.withUFirstName("John")
        		.withULastName("Doe")
        		.withUStatus(true)
        		.withUInsertionDate(LocalDateTime.now())
        		.withULastUpdate(LocalDateTime.now())
        		.build();
		
		// insere le user dans la bdd
		userMapper.insertUser(userToUpdate);
		
		// Mettre à jour le mot de passe de nouvel utilisateur
		String newPassword = "newPassword";
		userToUpdate.setUPassword(newPassword);
		int updateResult = userMapper.updatePassword(userToUpdate);
		
		// THEN
		assertEquals(1, updateResult);
		
		UUser retrievedUser = userMapper.findUserByEmail("userToUpdate@example.com").orElse(null);
		assertThat(retrievedUser).isNotNull();
		assertThat(newPassword).isEqualTo(retrievedUser.getUPassword());
	 }

 
    private LocalDateTime dateFormatter(String dateString) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
    	LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
    	return dateTime;
	}
}
