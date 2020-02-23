package br.alan.springcompanymeetingagenda.services.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import br.alan.springcompanymeetingagenda.domain.User;
import br.alan.springcompanymeetingagenda.repositories.UserRepository;
import br.alan.springcompanymeetingagenda.web.controllers.models.UserDto;
import br.alan.springcompanymeetingagenda.web.mappers.UserMapper;

/**
 * AuthServiceTest
 */
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    UserMapper userMapper;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AuthServiceImpl authService;

    User user;
    UserDto userDto;

    @BeforeEach
    void setUp() {
        this.user = User.builder().id(1L).name("John Doe").username("johndoe").password("password")
        .build();
        this.userDto = UserDto.builder().id(1L).name("John Doe").username("johndoe").build();
    }

    @DisplayName("getLoggedInUser should call repository and return user data")
    @Test
    void getLoggedInUserTest() throws NotFoundException {
        // arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("");
        SecurityContextHolder.setContext(securityContext);
        
        when(this.userRepository.findByUsername(anyString())).thenReturn(Optional
                .ofNullable(this.user));
        when(this.userMapper.userToUserDto(this.user)).thenReturn(this.userDto);

        // act
        UserDto loggedInUser = this.authService.getLoggedInUser();

        // assert
        assertEquals(this.userDto, loggedInUser);
        verify(this.userRepository).findByUsername(anyString());
        verify(this.userMapper).userToUserDto(this.user);
    }
    
    @DisplayName("getPasswordToken should set and save expiration date for password recovery, generate and return token.")
    @Test
    void getPasswordTokenTest() throws NotFoundException {
        // arrange
        when(this.userRepository.save(any())).thenReturn(this.user);

        // act
        when(this.userRepository.findByUsername(anyString()))
                .thenReturn(Optional.ofNullable(this.user));
        String passwordRecoveryToken = this.authService.getPasswordRecoveryToken("");
    
        // assert
        assertNotNull(this.user.getForgotPasswordToken());
        assertTrue(this.user.getForgotPasswordTokenExpirationDate().getTime() > Timestamp
                .valueOf(LocalDateTime.now()).getTime(), () -> "Expiration date should be greater than current time!");
        assertNotNull(passwordRecoveryToken);
        verify(this.userRepository).findByUsername(anyString());
        verify(this.userRepository).save(any());
    }

    @DisplayName("resetPassword should set new password and reset the token and its expiration date.")
    @Test
    void resetPasswordTest() throws AccessDeniedException, NotFoundException {
        // arrange
        String password = "12345";
        String token = password;

        this.user.setForgotPasswordToken(token);
        this.user.setForgotPasswordTokenExpirationDate(Timestamp.valueOf(LocalDateTime.now().plus(5, ChronoUnit.MINUTES)));
        when(this.userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(this.user));
        when(this.passwordEncoder.encode(password)).thenReturn(password);

        // act
        boolean result = this.authService.resetPassword(this.user.getUsername(), password, token);
    
        // assert
        assertNull(this.user.getForgotPasswordToken(), "Token should have been reset!");
        assertNull(this.user.getForgotPasswordTokenExpirationDate(), "Expiration date should have been reset!");
        assertEquals(password, this.user.getPassword(), "Password should have been changed!");
        assertTrue(result);
        verify(this.userRepository).findByUsername(anyString());
        verify(this.userRepository).save(any());
    }

    @DisplayName("resetPassword should throw NotFoundException, as user couldn't be found.")
    @Test
    void resetPasswordShouldThrowNotFoundException() throws AccessDeniedException, NotFoundException {
        // arrange
        String password = "12345";
        String token = password;

        this.user.setForgotPasswordToken(token);
        this.user.setForgotPasswordTokenExpirationDate(
                Timestamp.valueOf(LocalDateTime.now().minus(5, ChronoUnit.MINUTES)));
        
        when(this.userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));

        // act / assert
        assertThrows(NotFoundException.class,
                () -> this.authService.resetPassword(this.user.getUsername(), password, token));
    }

    @DisplayName("resetPassword should throw AccessDenied, as token is expired.")
    @Test
    void resetPasswordShouldThrowAccessDeniedTokenExpired() throws AccessDeniedException, NotFoundException {
        // arrange
        String password = "12345";
        String token = password;

        this.user.setForgotPasswordToken(token);
        this.user.setForgotPasswordTokenExpirationDate(Timestamp.valueOf(LocalDateTime.now().minus(5, ChronoUnit.MINUTES)));

        when(this.userRepository.findByUsername(anyString()))
                .thenReturn(Optional.ofNullable(this.user));

        // act / assert
        assertThrows(AccessDeniedException.class,
                () -> this.authService.resetPassword(this.user.getUsername(), password, token));
    }
    
    @DisplayName("resetPassword should throw AccessDenied, as tokens don't match.")
    @Test
    void resetPasswordShouldThrowAccessDeniedTokensDontMatch() throws AccessDeniedException, NotFoundException {
        // arrange
        String password = "12345";
        String token = password;

        this.user.setForgotPasswordToken(token);
        this.user.setForgotPasswordTokenExpirationDate(Timestamp.valueOf(LocalDateTime.now().plus(5, ChronoUnit.MINUTES)));

        when(this.userRepository.findByUsername(anyString()))
                .thenReturn(Optional.ofNullable(this.user));

        // act / assert
        assertThrows(AccessDeniedException.class,
                () -> this.authService.resetPassword(this.user.getUsername(), password, "123"));
    }

    @Test
    void userSignUpTest() {
        // arrange
        when(this.userRepository.save(this.user)).thenReturn(this.user);
        when(this.userMapper.userDtoToUser(this.userDto)).thenReturn(this.user);
    
        // act
        User user = this.authService.signUp(this.userDto);
    
        // assert
        assertEquals(this.user, user);
        verify(this.userMapper).userDtoToUser(this.userDto);
        verify(this.userRepository).save(this.user);
    }
}