package br.alan.springcompanymeetingagenda.web.controllers;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.alan.springcompanymeetingagenda.services.AdminServiceImpl;
import br.alan.springcompanymeetingagenda.utils.Mappings;
import lombok.RequiredArgsConstructor;


/**
 * AdminController
 */
@RequiredArgsConstructor
@RequestMapping(Mappings.ADMIN_PATH)
@RestController
public class AdminController {

    // == fields ==
    private final AdminServiceImpl adminService;

    // == public methods ==
    @PostMapping(path = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void promoteToAdmin(@PathVariable Long userId) throws NotFoundException {
         this.adminService.promoteToAdmin(userId);
     }

    @DeleteMapping(path = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revokeAdmin(@PathVariable Long userId) throws NotFoundException {
         this.adminService.revokeAdmin(userId);
     }
}
