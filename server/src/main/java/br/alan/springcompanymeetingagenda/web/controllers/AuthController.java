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
import br.alan.springcompanymeetingagenda.web.models.BaseErrorResponse;
import br.alan.springcompanymeetingagenda.web.models.InputDataValidationErrorResponse;
import br.alan.springcompanymeetingagenda.web.models.LoginDto;
import br.alan.springcompanymeetingagenda.web.models.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;


/**
 * AuthController
 */
@SuppressWarnings({"deprecation"})
@RequiredArgsConstructor
@RequestMapping(Mappings.AUTH_PATH)
@Api(description = "Auth Endpoints", tags = "Auth")
@RestController
public class AuthController {

    // == fields ==
    private final AuthService authService;

    // == public methods ==
    @GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("Get information about the current logged in user")
    @ApiResponse(code = 403, message = "Forbidden", response = BaseErrorResponse.class)
    public ResponseEntity<UserDto> getLoggedInUser() throws NotFoundException {
        return new ResponseEntity<>(this.authService.getLoggedInUser(), HttpStatus.OK);
    }

    @PostMapping(path = "/pw_recovery", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Request password reset token")
    @ApiImplicitParams({@ApiImplicitParam(name = "username", value = "Username", required = true,
            allowEmptyValue = false, paramType = "body", dataTypeClass = String.class,
            example = "username")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getPasswordRecoveryToken(@RequestBody Map<String, String> data)
            throws NotFoundException {
        String forgotPasswordToken =
                this.authService.getPasswordRecoveryToken(data.get("username"));
        System.out.println(forgotPasswordToken);

        // TODO: Could send e-mail with forgotten password token
    }

    @PostMapping(path = "/resetpassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Reset user password")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Access Token",
            required = true, allowEmptyValue = false, paramType = "header",
            dataTypeClass = String.class, example = "Bearer pw_recovery_token")})
    @ApiResponses({
            @ApiResponse(code = 403, message = "Invalid token", response = BaseErrorResponse.class)

    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION,
                    required = true) String forgotPasswordToken,
            @RequestBody LoginDto newCredentials) throws AccessDeniedException, NotFoundException {
        forgotPasswordToken = forgotPasswordToken.trim().replaceAll("Bearer ", "");
        this.authService.resetPassword(newCredentials.getUsername(), newCredentials.getPassword(),
                forgotPasswordToken);
    }

    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Create new user")
    @ApiResponses({@ApiResponse(code = 400, message = "Bad format for user",
            response = InputDataValidationErrorResponse.class)

    })
    public ResponseEntity<Object> signUp(@RequestBody @Validated UserDto userDto) {
        User createdUser = this.authService.signUp(userDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", Mappings.USERS_PATH + '/' + createdUser.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @ApiOperation("Login.")
    @ApiResponses({@ApiResponse(code = 400, message = "Bad credentials")})
    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void fakeLogin(@ApiParam(name = "User", value = "User") @RequestBody LoginDto loginDto) {
        throw new IllegalStateException(
                "This method shouldn't be called. It's implemented by Spring Security filters.");
    }
}
