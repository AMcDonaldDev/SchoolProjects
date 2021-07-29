package org.cmsc495.bpo.services;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticationServiceTest {
    private AuthenticationService auth;

    @Mock
    private UserDetailsService userDetails;

    @Mock
    private PasswordEncoder encoder;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        auth = new AuthenticationService(userDetails, encoder);
    }

    @Test
    public void testSupports() throws Exception {
        Class<?> authentication = UsernamePasswordAuthenticationToken.class;
        assertTrue(auth.supports(authentication));
    }
}
