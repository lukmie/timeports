package com.lukmie.timeports.config;

import com.lukmie.timeports.controller.HomeController;
import com.lukmie.timeports.controller.ReportController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HomeController.class)
public class LdapLoginAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginWithValidCredentialsThenAuthenticateUser() throws Exception {
        FormLoginRequestBuilder login = formLogin()
                .user("ben")
                .password("benspassword");

        mockMvc.perform(login)
                .andExpect(authenticated().withUsername("ben"));
    }

    @Test
    void loginWithInvalidCredentialsThenNoAuthenticateUser() throws Exception {
        FormLoginRequestBuilder login = formLogin()
                .user("zxc")
                .password("zxc");

        mockMvc.perform(login)
                .andExpect(unauthenticated());
    }
}
