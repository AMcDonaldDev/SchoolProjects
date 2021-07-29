package org.cmsc495.bpo.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.cmsc495.bpo.dao.interfaces.User;
import org.junit.Before;
import org.junit.Test;


public class GuestUserTest {
    private GuestUser guestUser;
    private String ip = "192.178.67.50";

    @Before
    public void init() throws Exception {
        guestUser = new GuestUser();
    }


    public void testGetters() throws Exception {
        assertEquals(null, guestUser.getUserProfile());
        assertEquals(null, guestUser.getCredentials());
        assertEquals(User.Type.GUEST, guestUser.getType());
    }

    @Test
    public void testAllArgsConstructor() throws Exception {
        guestUser = new GuestUser(ip);
        testGetters();
    }

    @Test
    public void testSetters() throws Exception {
        guestUser.setIp(ip);
        testGetters();
    }

    @Test
    public void testEquals() throws Exception {
        GuestUser otherGuestUser  = new GuestUser("192.154.67.90");
        guestUser.setIp(ip);
        assertFalse(guestUser.equals(new Object()));
        assertFalse(otherGuestUser.equals(guestUser));
        assertTrue(guestUser.equals(guestUser));
    }

    @Test
    @SuppressWarnings("all")
    public void testHashCode() throws Exception {
    	guestUser.setIp(ip);
        assertTrue(new Integer(guestUser.hashCode()) != null);
    }
}
