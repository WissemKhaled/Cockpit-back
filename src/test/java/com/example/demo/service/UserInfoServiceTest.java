package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dto.CreateUserDTO;
import com.example.demo.dto.CreateUserMapperEntityDTO;
import com.example.demo.dto.UUserDTO;
import com.example.demo.dto.UUserMapperEntityDTO;
import com.example.demo.entity.UUser;
import com.example.demo.mappers.UUserMapper;

@ExtendWith(MockitoExtension.class)
public class UserInfoServiceTest {
	@Mock
	private UUserMapper userMapper;
	
	@InjectMocks
	private UserInfoService userInfoService;
	
	@Mock
	UUserMapperEntityDTO userDtoMapper;
	
	@Mock
	CreateUserMapperEntityDTO createUserMapperEntityDTO;
	
	@Mock
    private PasswordEncoder encoder;
	
	/**
	 * test pour la méthode loadUserByUsername de spring security
     * Test le cas de figure où un utilisateur a bien été trouvé avec l'email renseigné
    */
	@Test
    void shouldReturnUserDetailsWhenUserIsFound() {
        // Données du Mock
        String userEmail = "test@example.com";
        UUser user = new UUser(1, userEmail, "password", "John", "Doe", true, LocalDateTime.now(), LocalDateTime.now());

        // Mock du userMapper pour renvoyer l'utilisateur lorsque findByEmail est appelé
        when(userMapper.findByEmail(userEmail)).thenReturn(Optional.of(user));

        // Appel de la méthode loadUserByUsername
        UserDetails userDetails = userInfoService.loadUserByUsername(userEmail);

        // Vérification que le UserDetails est créé avec l'e-mail attendu
        assertEquals(userEmail, userDetails.getUsername());
    }
	
	/**
	 * test pour la méthode loadUserByUsername de spring security
     * Test le cas de figure où un utilisateur n'a pas été trouvé avec l'email renseigné
     * Lance une exception UsernameNotFoundException
    */
    @Test
    void shouldThrowUsernameNotFoundExceptionWhenUserNotFound() {
        // Mock d'un email qui ne correspond à aucun utilisateur
        String userEmail = "nonexistent@example.com";

        // Mock du userMapper pour renvoyer un Optionnal vide, indiquant que l'utilisateur n'est pas trouvé
        when(userMapper.findByEmail(userEmail)).thenReturn(Optional.empty());

        // Appel de la méthode loadUserByUsername qui est censé lancer l'exception UsernameNotFoundException
        assertThrows(UsernameNotFoundException.class, () -> userInfoService.loadUserByUsername(userEmail));
    }
    
    /**
	 * test pour la méthode findUserByEmail
     * Test le cas de figure où un utilisateur a bien été trouvé avec l'email renseigné
     * 
    */
	@Test
    void shouldReturnUserDTOWhenUserIsFound() {
        // E-mail de test
        String userEmail = "test@example.com";

        // Mock d'un utilisateur avec l'e-mail spécifié
        UUser user = new UUser(1, userEmail, "password", "John", "Doe", true, LocalDateTime.now(), LocalDateTime.now());

        // Mock du résultat de findByEmail pour retourner l'utilisateur
        when(userMapper.findByEmail(userEmail)).thenReturn(Optional.of(user));

        // Mock de la conversion de l'entité utilisateur en DTO
        UUserDTO userDto = new UUserDTO(1, userEmail, "John", "Doe", true, LocalDateTime.now(), LocalDateTime.now());
        when(userDtoMapper.toDto(user)).thenReturn(userDto);

        // Appel de la méthode findUserByEmail
        UUserDTO result = userInfoService.findUserByEmail(userEmail);

        // Vérifie que le résultat est celui attendu
        assertEquals(userDto, result);
    }
	
	/**
	 * test pour la méthode findUserByEmail
     * Test le cas de figure où un utilisateur n'a pas été trouvé avec un email invalide renseigné 
     * Lance une exception IllegalArgumentException
    */
    @Test
    void shouldThrowIllegalArgumentExceptionWhenEmailIsInvalid() {
        // E-mail invalide
        String invalidEmail = null;

        // Appel de la méthode findUserByEmail et attente de l'exception IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> userInfoService.findUserByEmail(invalidEmail));
    }
    
    /**
	 * test pour la méthode findUserByEmail
     * Test le cas de figure où un utilisateur n'a pas été trouvé avec l'email renseigné
     * Lance une exception UsernameNotFoundException
    */
    @Test
    void shouldThrowUsernameNotFoundExceptionWhenUserIsNotFound() {
      // Mock d'un email qui ne correspond à aucun utilisateur
      String userEmail = "nonexistent@example.com";

      // Mock du userMapper pour renvoyer un Optionnal vide, indiquant que l'utilisateur n'est pas trouvé
      when(userMapper.findByEmail(userEmail)).thenReturn(Optional.empty());

      // Appel de la méthode loadUserByUsername qui est censé lancer l'exception UsernameNotFoundException
      assertThrows(UsernameNotFoundException.class, () -> userInfoService.loadUserByUsername(userEmail));
    }
    
    /**
     * Test le cas de figure où un utilisateur a bien été trouvé avec l'id renseigné
    */
    @Test
    public void testFindUserById_UserFound() {
        int userId = 1;
        UUser user = new UUser(userId, "test@example.com", "John", "Doe", true, null, null);
        when(userMapper.findById(userId)).thenReturn(Optional.of(user));

        UserDetails userDetails = userInfoService.findById(userId);

        assertNotNull(userDetails);
        assertEquals(user.getUEmail(), userDetails.getUsername());
    }
    
    /**
     * Test le cas de figure où un utilisateur n'a pas été trouvé avec l'id renseigné
     * Vérifie si l'exception UsernameNotFoundException a bien été lancée
    */
    @Test
    public void testFindUserById_UserNotFound() {
        int userId = 1;
        when(userMapper.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userInfoService.findById(userId);
        });
    }
    
    /**
     * Test le cas de figure où un utilisateur a bien été créé
    */
    @Test
    void shouldAddUserSuccessfully() {
        // Données de test pour le nouvel utilisateur
        CreateUserDTO userDTO = new CreateUserDTO(0, "test@example.com", "Password1!", "John", "Doe", true, LocalDateTime.now(), LocalDateTime.now());

        // Mock de la conversion de DTO en entité utilisateur
        UUser user = new UUser(userDTO.getUId(), userDTO.getUEmail(), userDTO.getUPassword(),
                userDTO.getUFirstName(), userDTO.getULastName(), userDTO.isUStatus(),
                userDTO.getInsertionDate(), userDTO.getLastUpdate());
        when(createUserMapperEntityDTO.toUser(userDTO)).thenReturn(user);

        // Mock de l'encodage du mot de passe
        String encodedPassword = "encodedPassword";
        when(encoder.encode(userDTO.getUPassword())).thenReturn(encodedPassword);

        // Utilisation de doAnswer pour simuler l'insertion de l'utilisateur
        doAnswer(invocation -> {
            // Vous pouvez mettre ici le code pour simuler l'insertion avec succès
            return null; // La méthode est de type void, donc renvoie null
        }).when(userMapper).insert(user);

        // Appel de la méthode addUser
        String result = userInfoService.addUser(userDTO);

        // Vérification que la méthode renvoie le message attendu
        assertEquals("Utilisateur '" + userDTO.getUEmail() + "' ajouté avec succès", result);
    }
    
    /**
     * Test le cas de figure où un utilisateur n'a pas pu être créé
     * Envoie une exception RuntimeException
    */
    @Test
    void shouldThrowExceptionOnAddUserFailure() {
        // Données de test pour le nouvel utilisateur
        CreateUserDTO userDTO = new CreateUserDTO(0, "test@example.com", "Password1!", "John", "Doe", true, LocalDateTime.now(), LocalDateTime.now());

        // Mock de la conversion de DTO en entité utilisateur
        UUser user = new UUser(userDTO.getUId(), userDTO.getUEmail(), userDTO.getUPassword(),
                userDTO.getUFirstName(), userDTO.getULastName(), userDTO.isUStatus(),
                userDTO.getInsertionDate(), userDTO.getLastUpdate());
        when(createUserMapperEntityDTO.toUser(userDTO)).thenReturn(user);

        // Mock de l'encodage du mot de passe
        String encodedPassword = "encodedPassword";
        when(encoder.encode(userDTO.getUPassword())).thenReturn(encodedPassword);

        // Utilisation de doAnswer pour simuler le comportement d'une méthode void
        doAnswer(invocation -> {
            // Vous pouvez mettre ici le code pour lancer l'exception ou effectuer d'autres actions
            throw new RuntimeException("Erreur lors de l'insertion");
        }).when(userMapper).insert(user);

        // Appel de la méthode addUser et vérification que cela lance une exception
        assertThrows(RuntimeException.class, () -> userInfoService.addUser(userDTO));
    }
	
}
