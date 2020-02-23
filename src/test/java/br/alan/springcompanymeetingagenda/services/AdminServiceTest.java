package br.alan.springcompanymeetingagenda.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import br.alan.springcompanymeetingagenda.domain.Role;
import br.alan.springcompanymeetingagenda.domain.User;
import br.alan.springcompanymeetingagenda.repositories.RoleRepository;
import br.alan.springcompanymeetingagenda.repositories.UserRepository;

/**
 * AdminServiceTest
 */
@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    AdminServiceImpl adminService;

    User user;
    Role adminRole = Role.builder().id(1L).name("ADMIN").build();

    @BeforeEach
    void setUp() {
        this.user = User.builder().id(1L).roles(new HashSet<>()).build();
    }

    @DisplayName("promoteToAdmin should add admin role to user")
    @Test
    void promoteToAdminTestNotAdmin() throws NotFoundException {
        // arrange
        when(this.userRepository.findById(this.user.getId())).thenReturn(Optional.of(
                this.user));
        when(this.roleRepository.findByName(anyString())).thenReturn(Optional.of(
                this.adminRole));
    
        // act
        this.adminService.promoteToAdmin(this.user.getId());
    
        // assert
        assertTrue(AdminService.isAdmin(user.getRoles()), "User should be an admin!");
        verify(this.userRepository).findById(this.user.getId());
        verify(this.userRepository).save(this.user);
    }

    @DisplayName("promoteToAdmin should NOT add admin role to user, as he's already an admin")
    @Test
    void promoteToAdminTestAlreadyAdmin() throws NotFoundException {
        // arrange
        this.user.getRoles().add(adminRole);
        when(this.userRepository.findById(this.user.getId()))
                .thenReturn(Optional.of(this.user));

        // act
        this.adminService.promoteToAdmin(this.user.getId());

        // assert
        verify(this.userRepository, times(0)).save(this.user);
    }
    
    @DisplayName("removeAdmin should remove admin role from user")
    @Test
    void revokeAdminTestShouldSave() throws NotFoundException {
        // arrange
        this.user.getRoles().add(adminRole);
        when(this.userRepository.findById(this.user.getId()))
                .thenReturn(Optional.of(this.user));
        when(this.roleRepository.findByName(anyString())).thenReturn(Optional.of(this.adminRole));

        // act
        this.adminService.revokeAdmin(this.user.getId());

        // assert
        assertFalse(AdminService
                .isAdmin(this.user.getRoles()), "User should no longer be an admin!");
        verify(this.userRepository).findById(this.user.getId());
    }
    
    @DisplayName("removeAdmin should not save user, as he doesn't isn't an admin")
    @Test
    void revokeAdminTestShouldntSave() throws NotFoundException {
        // arrange
        when(this.userRepository.findById(this.user.getId()))
                .thenReturn(Optional.of(this.user));

        // act
        this.adminService.revokeAdmin(this.user.getId());

        // assert
        verify(this.userRepository, times(0)).save(this.user);
    }

    @DisplayName("isAdmin should return false, as user doesn't have admin role")
    @Test
    void isAdminTestShouldReturnFalse() {
        // act
        boolean admin = AdminService.isAdmin(this.user.getRoles());
    
        // assert
        assertFalse(admin, "User should not be an admin!");
    }

    @DisplayName("isAdmin should return true, as user does have admin role")
    @Test
    void isAdminTestShouldReturnTrue() {
        // act
        this.user.getRoles().add(this.adminRole);
        boolean admin = AdminService.isAdmin(this.user.getRoles());
    
        // assert
        assertTrue(admin, "User should be an admin!");
    }
}