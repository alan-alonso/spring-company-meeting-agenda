package br.alan.springcompanymeetingagenda.services.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.alan.springcompanymeetingagenda.domain.User;
import br.alan.springcompanymeetingagenda.repositories.UserRepository;
import br.alan.springcompanymeetingagenda.web.controllers.models.UserPrincipal;
import lombok.RequiredArgsConstructor;

/**
 * UserPrincipalDetailsService
 */
@RequiredArgsConstructor
@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    // == fields ==
    private final UserRepository userRepository;

    // == public methods ==
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username couldn't be found!"));
        UserPrincipal userPrincipal = new UserPrincipal(user);

        return userPrincipal;
    }
}
