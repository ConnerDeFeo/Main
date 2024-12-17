package com.ufund.api.controller;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufund.api.ufundapi.controller.UserController;
import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.FundingBasketDAO;
import com.ufund.api.ufundapi.persistence.SubscriptionsDAO;
import com.ufund.api.ufundapi.persistence.UserDAO;
import com.ufund.api.ufundapi.persistence.WishListDAO;

@Tag("Controller-tier")
public class UserControllerTest {
    private UserController userController ;
    private UserDAO userDAO ;
    private FundingBasketDAO fundingBasketDAO;
    private WishListDAO wlDAO;
    private SubscriptionsDAO subDAO;
    private User test_user ;
    private User mockUser;

    @BeforeEach
    public void setupUserController() {
        userDAO = mock(UserDAO.class) ;
        fundingBasketDAO = mock(FundingBasketDAO.class) ;
        subDAO=mock(SubscriptionsDAO.class);
        wlDAO=mock(WishListDAO.class);
        userController = new UserController(userDAO,fundingBasketDAO,wlDAO,subDAO);
        test_user = new User(1,"Tim") ;     
        mockUser=mock(User.class);   
    }

    @Test
    public void testExceptionHandling() throws IOException {
        doThrow(new IOException()).when(userDAO).getUserById(1);
        doThrow(new IOException()).when(userDAO).getAllUsers();
        doThrow(new IOException()).when(userDAO).updateUser(test_user);
        doThrow(new IOException()).when(userDAO).deleteUser(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, userController.getUser(1).getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, userController.getUsers().getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, userController.updateUser(test_user).getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, userController.deleteUser(1).getStatusCode());
    }

    @Test
    public void testGetUser() throws IOException {
        when(userDAO.getUserById(1)).thenReturn(test_user);
        ResponseEntity<User> response = userController.getUser(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(test_user, response.getBody());
    }

    @Test
    public void testGetUserFailure() throws IOException {
        when(userDAO.getUserById(1)).thenReturn(null);
        ResponseEntity<User> response = userController.getUser(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetUsers() throws IOException {
        User[] users = new User[] { test_user };
        when(userDAO.getAllUsers()).thenReturn(users);
        ResponseEntity<User[]> response = userController.getUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testCreateUser() throws IOException {
        when(userDAO.addUser(test_user)).thenReturn(test_user);
        ResponseEntity<User> response = userController.createUser(test_user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(test_user, response.getBody());
        when(userDAO.addUser(test_user)).thenThrow(new IOException());
        response=userController.createUser(test_user);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateUserFailure() throws IOException {
        when(userDAO.addUser(test_user)).thenReturn(null);
        ResponseEntity<User> response = userController.createUser(test_user);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testUpdateUser() throws IOException {
        when(userDAO.updateUser(test_user)).thenReturn(test_user);
        ResponseEntity<User> response = userController.updateUser(test_user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(test_user, response.getBody());
    }

    @Test
    public void testUpdateUserFailure() throws IOException {
        when(userDAO.updateUser(test_user)).thenReturn(null);
        ResponseEntity<User> response = userController.updateUser(test_user);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteUser() throws IOException {
        when(userDAO.deleteUser(1)).thenReturn(true);
        ResponseEntity<User> response = userController.deleteUser(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteUserFailure() throws IOException {
        when(userDAO.deleteUser(1)).thenReturn(false);
        ResponseEntity<User> response = userController.deleteUser(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSignIn() throws IOException{
        when(userDAO.getUserByUsername("Tim")).thenReturn(test_user);
        when(userDAO.signIn(1)).thenReturn(true);
        ResponseEntity<User> response=userController.signIn("Tim");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        when(userDAO.signIn(1)).thenReturn(false);
        assertEquals(HttpStatus.CONFLICT, userController.signIn("Tim").getStatusCode());
        when(userDAO.getUserByUsername("Tim")).thenReturn(null);
        response=userController.signIn("Tim");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        when(userDAO.getUserByUsername("Tim")).thenThrow(new IOException(""));
        ResponseEntity<User> response2=userController.signIn("Tim");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response2.getStatusCode());

    }

    @Test
    public void testSignOut() throws IOException{
        when(userDAO.getUserById(1)).thenReturn(test_user);
        when(userDAO.signOut(1)).thenReturn(true);
        ResponseEntity<User> response=userController.signOut(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        when(userDAO.signOut(1)).thenReturn(false);
        assertEquals(HttpStatus.CONFLICT, userController.signOut(1).getStatusCode());
        when(userDAO.getUserById(1)).thenReturn(null);
        response=userController.signOut(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        when(userDAO.getUserById(1)).thenThrow(new IOException());
        response=userController.signOut(1);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        
    }

    @Test
    public void testSignedIn()throws IOException{
        when(userDAO.getUserById(1)).thenReturn(mockUser);
        when(mockUser.getSignin()).thenReturn(false);
        ResponseEntity<Boolean> response=userController.signedIn(1);
        assertEquals(false, response.getBody());
        when(mockUser.getSignin()).thenReturn(true);
        response=userController.signedIn(1);
        assertEquals(true, response.getBody());
        when(userDAO.getUserById(1)).thenReturn(null);
        response=userController.signedIn(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        when(userDAO.getUserById(1)).thenThrow(new IOException());
        response=userController.signedIn(1);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
