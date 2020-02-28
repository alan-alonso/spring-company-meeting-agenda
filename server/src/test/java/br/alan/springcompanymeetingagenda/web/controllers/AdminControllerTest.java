package br.alan.springcompanymeetingagenda.web.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import br.alan.springcompanymeetingagenda.services.AdminServiceImpl;
import br.alan.springcompanymeetingagenda.utils.Mappings;

/**
 * AdminControllerTest
 * 
 * Test class for {@link AdminController}.
 * 
 * @author Alan Alonso
 */
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @MockBean
    AdminServiceImpl adminService;

    @MockBean
    UserDetailsService userDetailsService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        reset(this.adminService);
    }

    @DisplayName("promoteToAdmin should call service and return no content response")
    @Test
    void promoteToAdminTest() throws Exception {
        // arrange
        doNothing().when(this.adminService).promoteToAdmin(anyLong());
    
        // act / assert
        this.mockMvc.perform(post(Mappings.ADMIN_PATH + "/{userId}", 1L))
                .andExpect(status().isNoContent());
        verify(this.adminService).promoteToAdmin(anyLong());
    }

    @DisplayName("revokeAdmin should call service and return no content response")
    @Test
    void revokeAdminTest() throws Exception {
        // arrange
        doNothing().when(this.adminService).revokeAdmin(anyLong());
    
        // act / assert
        this.mockMvc.perform(delete(Mappings.ADMIN_PATH + "/{userId}", 1L))
                .andExpect(status().isNoContent());
        verify(this.adminService).revokeAdmin(anyLong());
    }
}