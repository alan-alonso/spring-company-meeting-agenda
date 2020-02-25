package br.alan.springcompanymeetingagenda.services.auth;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import br.alan.springcompanymeetingagenda.domain.User;
import br.alan.springcompanymeetingagenda.web.models.UserDto;

public interface AuthService {

    /**
     * Return information about the current logged in user.
     * 
     * @return logged in user data
     * @throws NotFoundException if user couldn't be found using the current logged in username
     */
    UserDto getLoggedInUser() throws NotFoundException;

    /**
     * Generate and return password recovery token for the given username.
     * 
     * @param username
     * @return password recovery token
     * @throws NotFoundException if user couldn't be found with the given username
     */
    String getPasswordRecoveryToken(String username) throws NotFoundException;

    /**
     * Reset the password of the given username.
     * 
     * @param username
     * @param newPassword
     * @param forgotPasswordToken
     * @return true if successful, false otherwise
     * @throws AccessDeniedException if tokens don't match or has expired
     * @throws NotFoundException     if user couldn't be found with the given username
     */
    boolean resetPassword(String username, String newPassword, String forgotPasswordToken)
            throws AccessDeniedException, NotFoundException;

    /**
     * Create new user.
     * 
     * @param userDto
     * @return created user.
     */
    User signUp(UserDto userDto);

}
