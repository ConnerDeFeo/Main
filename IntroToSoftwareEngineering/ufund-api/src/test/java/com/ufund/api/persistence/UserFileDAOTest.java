package com.ufund.api.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.UserFileDAO;

public class UserFileDAOTest {

    @Mock
    private ObjectMapper objectMapper;

    private UserFileDAO userFileDAO;
    private String filename = "test-users.json"; 
    private User newUser;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        when(objectMapper.readValue(any(File.class), eq(User[].class)))
            .thenReturn(new User[]{}); 

        userFileDAO = new UserFileDAO(filename, objectMapper);
        newUser = new User(1, "newuser");
    }

    @Test
    public void testAddUser() throws IOException {

        User newUser = new User(1, "newuser");

        doNothing().when(objectMapper).writeValue(any(File.class), any(User[].class));

        User addedUser = userFileDAO.addUser(newUser);
        User nullUser=userFileDAO.addUser(addedUser);

        assertNotNull(addedUser);
        assertNull(nullUser);
        assertEquals("newuser", addedUser.getUsername());
        assertEquals(1, addedUser.getId());
    }

    @Test
    public void testGetUserById() throws IOException {

        User user = new User(1, "testuser");
        userFileDAO.addUser(user); // You might need to add users for testing

        User foundUser = userFileDAO.getUserById(1);

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    public void testDeleteUser() throws IOException {

        User user = new User(1, "testuser");
        userFileDAO.addUser(user);

        doNothing().when(objectMapper).writeValue(any(File.class), any(User[].class));

        boolean result = userFileDAO.deleteUser(1);
        boolean nullResult=userFileDAO.deleteUser(1);

        assertTrue(result);
        assertNull(userFileDAO.getUserById(1));
        assertFalse(nullResult);
    }

    @Test
    public void testUpdateUser() throws IOException {

        User user = new User(1, "testuser");
        userFileDAO.addUser(user);

        doNothing().when(objectMapper).writeValue(any(File.class), any(User[].class));
        
        User updatedUser = new User(1, "updateduser");
        User nullUser=userFileDAO.updateUser(new User(100, "filename"));

        User result = userFileDAO.updateUser(updatedUser);

        assertNotNull(result);
        assertNull(nullUser);
        assertEquals("updateduser", result.getUsername());
    }

    @Test
    public void testSignIn() throws IOException {

        User user = new User(1, "testuser");
        userFileDAO.addUser(user);

        doNothing().when(objectMapper).writeValue(any(File.class), any(User[].class));

        boolean result = userFileDAO.signIn(1);
        boolean falseResult=userFileDAO.signIn(20);

        assertFalse(falseResult);

        assertTrue(result);
        assertFalse(user.getSignin()); // Check if the user is signed in
    }

    @Test
    public void testSignOut() throws IOException {

        User user = new User(1, "testuser");
        user.signIn(); // Simulate the user signing in
        userFileDAO.addUser(user);
        
        doNothing().when(objectMapper).writeValue(any(File.class), any(User[].class));

        boolean result = userFileDAO.signOut(1);
        boolean resultFalse = userFileDAO.signOut(10);

        assertFalse(resultFalse);
        assertTrue(result);
        assertTrue(user.getSignin()); // Check if the user is signed out
    }

    @Test
    public void testGetUserByUsername()throws IOException{
        userFileDAO.addUser(newUser);
        assertNotNull(userFileDAO.getUserByUsername(newUser.getUsername()));
        assertNull(userFileDAO.getUserByUsername("x"));
    }

    @Test
    public void testGetAllUsers()throws IOException{
        userFileDAO.addUser(newUser);
        assertEquals(1, userFileDAO.getAllUsers().length);
    }
}
