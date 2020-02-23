package br.alan.springcompanymeetingagenda.web.controllers;

import java.util.Map;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.alan.springcompanymeetingagenda.domain.User;
import br.alan.springcompanymeetingagenda.services.auth.AuthService;
import br.alan.springcompanymeetingagenda.utils.Mappings;
import br.alan.springcompanymeetingagenda.web.controllers.models.LoginDto;
import br.alan.springcompanymeetingagenda.web.controllers.models.UserDto;
import lombok.RequiredArgsConstructor;


/**
 * AuthController
 */
@SuppressWarnings({"deprecation"})
@RequiredArgsConstructor
@RequestMapping(Mappings.AUTH_PATH)
@RestController
public class AuthController {

    // == fields ==
    private final AuthService authService;

    // == public methods ==
    @GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDto> getLoggedInUser() throws NotFoundException {
        return new ResponseEntity<>(this.authService.getLoggedInUser(), HttpStatus.OK);
    }

    @PostMapping(path = "/pw_recovery", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getPasswordRecoveryToken(@RequestBody Map<String, String> data)
            throws NotFoundException {
        String forgotPasswordToken =
                this.authService.getPasswordRecoveryToken(data.get("username"));
        System.out.println(forgotPasswordToken);

        // TODO: Could send e-mail with forgotten password token
    }

    @PostMapping(path = "/resetpassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(
            @RequestHeader(value = "Authorization", required = true) String forgotPasswordToken,
            @RequestBody LoginDto newCredentials) throws AccessDeniedException, NotFoundException {
        forgotPasswordToken = forgotPasswordToken.trim().replaceAll("Bearer ", "");
        this.authService.resetPassword(newCredentials.getUsername(), newCredentials.getPassword(),
                forgotPasswordToken);
    }

    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> signUp(@RequestBody @Validated UserDto userDto) {
        User createdUser = this.authService.signUp(userDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", Mappings.USERS_PATH + '/' + createdUser.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
