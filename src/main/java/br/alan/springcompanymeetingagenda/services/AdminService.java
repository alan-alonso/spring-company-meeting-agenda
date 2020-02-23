package br.alan.springcompanymeetingagenda.services;

import java.util.Set;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import br.alan.springcompanymeetingagenda.domain.Role;

public abstract class AdminService {

    /**
     * Promote the given user id to admin.
     * 
     * @param userId
     * @throws NotFoundException if user with given ID couldn't be found
     */
    abstract void promoteToAdmin(Long userId) throws NotFoundException;

    /**
     * Promote the administrator priveleges from the user with the given id.
     * 
     * @param userId
     * @throws NotFoundException if user with given ID couldn't be found
     */
    abstract void revokeAdmin(Long userId) throws NotFoundException;

    /**
     * Check if the user has admin role.
     * 
     * @param userRoles
     * @return true if user is an admin, false otherwise
     */
    public static boolean isAdmin(Set<Role> userRoles) {
        boolean admin = false;
        for (Role role : userRoles) {
            if (role.getName().equalsIgnoreCase("ADMIN")) {
                admin = true;
                break;
            }
        }
        return admin;
    }

}
