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
import br.alan.springcompanymeetingagenda.web.models.BaseErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;


/**
 * AdminController
 */
@RequiredArgsConstructor
@RequestMapping(Mappings.ADMIN_PATH)
@Api(description = "Admin Endpoints", tags = "Admin")
@RestController
public class AdminController {

    // == fields ==
    private final AdminServiceImpl adminService;

    // == public methods ==
    @PostMapping(path = "/{userId}")
    @ApiOperation("Grant admin permissions to user")
    @Authorization("ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({@ApiResponse(code = 204, message = "Test"),
            @ApiResponse(code = 403, message = "Forbidden", response = BaseErrorResponse.class)})
    public void promoteToAdmin(@PathVariable Long userId) throws NotFoundException {
        this.adminService.promoteToAdmin(userId);
    }

    @DeleteMapping(path = "/{userId}")
    @ApiOperation("Revoke admin permission from user")
    @Authorization("ADMIN")
    @ApiResponses({@ApiResponse(code = 204, message = "Test"),
            @ApiResponse(code = 403, message = "Forbidden", response = BaseErrorResponse.class)})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revokeAdmin(@PathVariable Long userId) throws NotFoundException {
        this.adminService.revokeAdmin(userId);
    }
}
