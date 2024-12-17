package com.ufund.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.User;

public class UserModelTest {
    
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(1, "bob");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(1, user.getId());
        assertEquals("bob", user.getUsername());
        assertEquals(false, user.getSignin());
    }

    @Test
    public void testSetUserame() {
        user.setUsername("acorn_gimblethorton");
        assertEquals("acorn_gimblethorton", user.getUsername());
    }

    @Test
    public void testSetId() {
        user.setId(99);
        assertEquals(99, user.getId());
    }

    @Test
    public void testSignIn() {
        user.signIn();
        assertEquals(true, user.getSignin());
    }

    @Test
    public void testSignOut() {
        user.signIn();
        user.signOut();
        assertEquals(false, user.getSignin());
    }

}
