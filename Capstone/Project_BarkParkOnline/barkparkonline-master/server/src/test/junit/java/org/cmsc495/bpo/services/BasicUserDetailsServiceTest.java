package org.cmsc495.bpo.services;

import static org.junit.Assert.assertThrows;

import org.cmsc495.bpo.repositories.BasicUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BasicUserDetailsServiceTest {
    private BasicUserDetailsService basicUserDetailsService;
    private MongoTemplate mongo;
    private PasswordEncoder passwordEncoder;
    BasicUserRepository dummyRepository = new BasicUserRepository(mongo, passwordEncoder);

    @Before
    public void init() throws Exception {    	
        basicUserDetailsService = new BasicUserDetailsService(dummyRepository);
    }

    @Test
    public void testLoadUserByUserName() throws Exception {
    	assertThrows(RuntimeException.class, () -> {
    			basicUserDetailsService.loadUserByUsername("EMMA");
    	});
    }
}
