package br.alan.springcompanymeetingagenda.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import br.alan.springcompanymeetingagenda.domain.Role;
import br.alan.springcompanymeetingagenda.domain.User;
import br.alan.springcompanymeetingagenda.repositories.RoleRepository;
import br.alan.springcompanymeetingagenda.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 * AdminServiceImpl
 */
@RequiredArgsConstructor
@Service
public class AdminServiceImpl extends AdminService {

    // == fields ==
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    // == public methods ==
    @Override
    public void promoteToAdmin(Long userId) throws NotFoundException {
        User user = this.userRepository.findById(userId).orElseThrow(NotFoundException::new);

        // if isn't admin, promote the user and save
        if (!isAdmin(user.getRoles())) {
            Role adminRole = this.roleRepository.findByName("ADMIN").get();
            user.getRoles().add(adminRole);
            this.userRepository.save(user);
        }
    }

    @Override
    public void revokeAdmin(Long userId) throws NotFoundException {
        User user = this.userRepository.findById(userId).orElseThrow(NotFoundException::new);

        // if it's an admin, revoke the permissions and save
        if (isAdmin(user.getRoles())) {
            Role adminRole = this.roleRepository.findByName("ADMIN").get();

            user.getRoles().remove(adminRole);
            this.userRepository.save(user);
        }

    }
}