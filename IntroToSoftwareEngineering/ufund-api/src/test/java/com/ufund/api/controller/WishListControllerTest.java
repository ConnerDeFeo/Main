package com.ufund.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufund.api.ufundapi.controller.WishListController;
import com.ufund.api.ufundapi.model.WishList;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.WishListDAO;
import com.ufund.api.ufundapi.persistence.NeedDAO;

public class WishListControllerTest {
     private WishListController WishListController;
    private WishListDAO WishListDAO;
    private NeedDAO needDAO;

    @BeforeEach
    public void setupWishListController() {
        WishListDAO = mock(WishListDAO.class);
        needDAO = mock(NeedDAO.class);
        WishListController = new WishListController(WishListDAO, needDAO);
    }

    @Test
    public void testgetWishList_Success() throws IOException {
        int userId = 1;
        WishList mockBasket = new WishList(userId);
        when(WishListDAO.getWishList(userId)).thenReturn(mockBasket);

        ResponseEntity<WishList> response = WishListController.getWishList(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBasket, response.getBody());
    }

    @Test
    public void testgetWishList_NotFound() throws IOException {
        int userId = 1;
        when(WishListDAO.getWishList(userId)).thenReturn(null);

        ResponseEntity<WishList> response = WishListController.getWishList(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testgetWishList_InternalServerError() throws IOException {
        int userId = 1;
        when(WishListDAO.getWishList(userId)).thenThrow(new IOException("Database error"));

        ResponseEntity<WishList> response = WishListController.getWishList(userId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testAddNeed_Success() throws IOException {
        int userId = 1;
        int needId = 100;
        WishList mockBasket = new WishList(userId);
        Need mockNeed = new Need(1, "Egg", 5.0, 5, "Food");
        
        when(WishListDAO.getWishList(userId)).thenReturn(mockBasket);
        when(needDAO.getNeed(needId)).thenReturn(mockNeed);
        
        ResponseEntity<WishList> response = WishListController.addNeed(userId, needId, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(WishListDAO, times(1)).addNeed(userId, mockNeed);
    }

    @Test
    public void testAddNeed_NotFound() throws IOException {
        int userId = 1;
        int needId = 100;
        when(WishListDAO.getWishList(userId)).thenReturn(null);

        ResponseEntity<WishList> response = WishListController.addNeed(userId, needId, 5);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddNeed_InternalServerError() throws IOException {
        int userId = 1;
        int needId = 100;
        when(needDAO.getNeed(needId)).thenThrow(new IOException("Database error"));

        ResponseEntity<WishList> response = WishListController.addNeed(userId, needId, 5);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteNeed_Success() throws IOException {
        int userId = 1;
        int needId = 100;
        WishList mockBasket = new WishList(userId);
        
        when(WishListDAO.deleteNeed(userId, needId)).thenReturn(true);
        when(WishListDAO.getWishList(userId)).thenReturn(mockBasket);
        
        ResponseEntity<WishList> response = WishListController.deleteNeed(userId, needId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBasket, response.getBody());
    }

    @Test
    public void testDeleteNeed_NotFound() throws IOException {
        int userId = 1;
        int needId = 100;
        
        when(WishListDAO.deleteNeed(userId, needId)).thenReturn(false);

        ResponseEntity<WishList> response = WishListController.deleteNeed(userId, needId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteNeed_InternalServerError() throws IOException {
        int userId = 1;
        int needId = 100;
        
        when(WishListDAO.deleteNeed(userId, needId)).thenThrow(new IOException("Database error"));

        ResponseEntity<WishList> response = WishListController.deleteNeed(userId, needId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
