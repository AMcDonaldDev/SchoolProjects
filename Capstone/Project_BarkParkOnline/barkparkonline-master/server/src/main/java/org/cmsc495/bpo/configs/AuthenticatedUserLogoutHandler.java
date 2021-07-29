package org.cmsc495.bpo.configs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.interfaces.User;
import org.cmsc495.bpo.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserLogoutHandler implements LogoutSuccessHandler {

    private UserService userService;

    @Autowired
    public AuthenticatedUserLogoutHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onLogoutSuccess(
        HttpServletRequest request, 
        HttpServletResponse response, 
        Authentication authentication
    ) throws IOException, ServletException {
        if (authentication == null) {
            return;
        }
        UsernamePasswordAuthenticationToken authToken = 
            (UsernamePasswordAuthenticationToken) authentication;

        // Logout of User Service
        User user = (BasicUser) authToken.getPrincipal();
        userService.logout(user);
    }
}