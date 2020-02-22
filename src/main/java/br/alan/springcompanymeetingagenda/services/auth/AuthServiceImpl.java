package br.alan.springcompanymeetingagenda.services.auth;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.alan.springcompanymeetingagenda.domain.User;
import br.alan.springcompanymeetingagenda.repositories.UserRepository;
import br.alan.springcompanymeetingagenda.web.controllers.models.UserDto;
import br.alan.springcompanymeetingagenda.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;

/**
 * AuthServiceImpl
 */
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    // == fields ==
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // == public methods ==
    @Override
    public UserDto getLoggedInUser() throws NotFoundException {
        Object userPrincipal =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userPrincipal == null) {
            return null;
        }

        String username = userPrincipal.toString();
        User user =
                this.userRepository.findByUsername(username).orElseThrow(NotFoundException::new);
        UserDto userDto = this.userMapper.userToUserDto(user);

        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        userDto.setAuthorities(authorities);

        return userDto;
    }

    @Override
    public String getPasswordRecoveryToken(String username) throws NotFoundException {
        User user =
                this.userRepository.findByUsername(username).orElseThrow(NotFoundException::new);

        String forgotPasswordToken = RandomString.make(30);
        user.setForgotPasswordToken(forgotPasswordToken);
        // set expiration date to 30 minutes from now
        user.setForgotPasswordTokenExpirationDate(
                Timestamp.valueOf(LocalDateTime.now().plus(30, ChronoUnit.MINUTES)));

        this.userRepository.save(user);

        return forgotPasswordToken;
    }

    @Override
    public boolean resetPassword(String username, String newPassword, String forgotPasswordToken)
            throws AccessDeniedException, NotFoundException {
        User user =
                this.userRepository.findByUsername(username).orElseThrow(NotFoundException::new);

        if (!user.getForgotPasswordToken().equals(forgotPasswordToken)
                || user.getForgotPasswordTokenExpirationDate().getTime() < Timestamp
                        .valueOf(LocalDateTime.now()).getTime()) {
            throw new AccessDeniedException("Invalid token!");
        }
        
        user.setPassword(this.passwordEncoder.encode(newPassword));
        user.setForgotPasswordToken(null);
        user.setForgotPasswordTokenExpirationDate(null);

        this.userRepository.save(user);

        return true;
    }

    
}