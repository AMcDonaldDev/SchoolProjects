package org.cmsc495.bpo.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CredentialsTest {
    private Credentials credentials;

    private String username = "Emma Stone";
    private String password = "easy-@";
    private String email = "emmastone@easy-a.com";
    private String profileId = "4c27c0ef-1a92-443c-be28-62d164652b4a";
    private String id = "";

    @Before
    public void init() throws Exception {
        credentials = new Credentials();
    }

    public void testGetters() throws Exception {
        assertEquals(username, credentials.getUsername());
        assertEquals(password, credentials.getPassword());
        assertEquals(email, credentials.getEmail());
        assertEquals(profileId, credentials.getProfileId());
        assertEquals(id, credentials.getId());
    }

    @Test
    public void testAllArgsConstructor() throws Exception {
        credentials = new Credentials(id, username, email, profileId);
        credentials.setPassword(password);
        testGetters();
    }

    @Test
    public void testWithBuilders() throws Exception {
        credentials = new Credentials()
            .withEmail(email)
            .withPassword(password)
            .withUsername(username)
            .withProfileId(profileId);
        credentials.setId(id);
        testGetters();
    }

    @Test
    public void testSetters() throws Exception {
        credentials.setEmail(email);
        credentials.setUsername(username);
        credentials.setPassword(password);
        credentials.setProfileId(profileId);
        credentials.setId(id);
        testGetters();
    }

    @Test
    public void testEquals() throws Exception {
        Credentials otherCredentials = new Credentials("a", "b", "c", "d");

        assertFalse(credentials.equals(new Object()));
        assertFalse(otherCredentials.equals(credentials));
        assertTrue(credentials.equals(credentials));
    }

    @Test
    public void testToString() throws Exception {
        assertTrue(credentials.toString().contains("id="));
        assertTrue(credentials.toString().contains("username="));
        assertTrue(credentials.toString().contains("profileId="));
        assertTrue(credentials.toString().contains("email="));

        assertFalse(credentials.toString().contains("password"));
    }

    @Test
    @SuppressWarnings("all")
    public void testHashCode() throws Exception {
        assertTrue(new Integer(credentials.hashCode()) != null);
    }
}
