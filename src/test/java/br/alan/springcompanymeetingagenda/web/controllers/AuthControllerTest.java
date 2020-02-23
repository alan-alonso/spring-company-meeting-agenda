package br.alan.springcompanymeetingagenda.web.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import br.alan.springcompanymeetingagenda.domain.User;
import br.alan.springcompanymeetingagenda.services.auth.AuthService;
import br.alan.springcompanymeetingagenda.utils.Mappings;
import br.alan.springcompanymeetingagenda.web.controllers.models.LoginDto;
import br.alan.springcompanymeetingagenda.web.controllers.models.UserDto;

/**
 * AuthControllerTest
 */
@SuppressWarnings({"deprecation"})
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @MockBean
    AuthService authService;

    @MockBean
    UserDetailsService userDetailsService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    UserDto userDto;

    @BeforeEach
    void setUp() {
        this.userDto = UserDto.builder().id(1L).name("John Doe").username("johndoe")
                .password("password").email("test@test.com").build();
    }

    @AfterEach
    void tearDown() {
        reset(this.authService);
    }

    @DisplayName("getLoggedInUser should call service and return correct new stored user")
    @Test
    void getLoggedInUserTest() throws Exception {
        // arrange
        when(this.authService.getLoggedInUser()).thenReturn(this.userDto);

        // act / assert
        this.mockMvc.perform(get(Mappings.AUTH_PATH + "/me")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", Is.is(this.userDto.getId().intValue())))
                .andExpect(jsonPath("$.username", Is.is(this.userDto.getUsername())));
        verify(this.authService).getLoggedInUser();
    }

    @DisplayName("getPasswordRecoveryToken should call service and return 204")
    @Test
    void getPasswordRecoveryTokenTest() throws Exception {
        // arrange
        when(this.authService.getPasswordRecoveryToken(anyString())).thenReturn("");

        // act / assert
        this.mockMvc.perform(post(Mappings.AUTH_PATH + "/pw_recovery")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(("{\"username\": \"" + this.userDto.getUsername() + "\"}").getBytes()))
                .andExpect(status().isNoContent());
        verify(this.authService).getPasswordRecoveryToken(this.userDto.getUsername());
    }

    @DisplayName("resetPassword should call service and return 204")
    @Test
    void resetPasswordTest() throws Exception {
        // arrange
        LoginDto loginDto = LoginDto.builder().username(this.userDto.getUsername())
                .password(this.userDto.getPassword()).build();

        when(this.authService.resetPassword(loginDto.getPassword(), loginDto.getPassword(),
                "token")).thenReturn(true);

        // act / assert
        this.mockMvc
                .perform(post(Mappings.AUTH_PATH + "/resetpassword")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer token")
                        .content(this.objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isNoContent());
        verify(this.authService).resetPassword(loginDto.getUsername(), loginDto.getPassword(),
                "token");
    }

    @DisplayName("signUp should call service and return the newly created users ID location")
    @Test
    void signUpTest() throws JsonProcessingException, Exception {
        // arrange
        User user = User.builder().id(1L).name("John Doe").username("johndoe")
                .password("password").build();

        when(this.authService.signUp(any(UserDto.class))).thenReturn(user);
        
        String serializedWithoutPw = this.objectMapper.writeValueAsString(this.userDto);
        String serializedWithPw =
                serializedWithoutPw.substring(0, serializedWithoutPw.length() - 1) +
                        ",\"password\": \"" + this.userDto.getPassword() + "\"}";
                
        // act / assert
        this.mockMvc
                .perform(post(Mappings.AUTH_PATH + "/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(serializedWithPw.getBytes()))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", Mappings.USERS_PATH + '/' + user.getId()));
        verify(this.authService).signUp(any(UserDto.class));
    }
}