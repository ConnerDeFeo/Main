package com.ufund.api.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.ufund.api.ufundapi.controller.FundingBasketController;
import com.ufund.api.ufundapi.model.FundingBasket;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.FundingBasketDAO;
import com.ufund.api.ufundapi.persistence.NeedDAO;

public class FundingBasketControllerTest {
    private FundingBasketController fundingBasketController;
    private FundingBasketDAO fundingBasketDAO;
    private NeedDAO needDAO;

    @BeforeEach
    public void setupfundingbasketcontroller() {
        fundingBasketDAO = mock(FundingBasketDAO.class);
        needDAO = mock(NeedDAO.class);
        fundingBasketController = new FundingBasketController(fundingBasketDAO, needDAO);
    }

    @Test
    public void testGetFundingBasket_Success() throws IOException {
        int userId = 1;
        FundingBasket mockBasket = new FundingBasket(userId);
        when(fundingBasketDAO.getFundingBasket(userId)).thenReturn(mockBasket);

        ResponseEntity<FundingBasket> response = fundingBasketController.getFundingBasket(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBasket, response.getBody());
    }

    @Test
    public void testGetFundingBasket_NotFound() throws IOException {
        int userId = 1;
        when(fundingBasketDAO.getFundingBasket(userId)).thenReturn(null);

        ResponseEntity<FundingBasket> response = fundingBasketController.getFundingBasket(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetFundingBasket_InternalServerError() throws IOException {
        int userId = 1;
        when(fundingBasketDAO.getFundingBasket(userId)).thenThrow(new IOException("Database error"));

        ResponseEntity<FundingBasket> response = fundingBasketController.getFundingBasket(userId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testAddNeed_Success() throws IOException {
        int userId = 1;
        int needId = 100;
        FundingBasket mockBasket = new FundingBasket(userId);
        Need mockNeed = new Need(1, "Egg", 5.0, 5, "Food");
        
        when(fundingBasketDAO.getFundingBasket(userId)).thenReturn(mockBasket);
        when(needDAO.getNeed(needId)).thenReturn(mockNeed);
        
        ResponseEntity<FundingBasket> response = fundingBasketController.addNeed(userId, needId, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(fundingBasketDAO, times(1)).addNeed(userId, mockNeed);
    }

    @Test
    public void testAddNeed_NotFound() throws IOException {
        int userId = 1;
        int needId = 100;
        when(fundingBasketDAO.getFundingBasket(userId)).thenReturn(null);

        ResponseEntity<FundingBasket> response = fundingBasketController.addNeed(userId, needId, 5);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddNeed_InternalServerError() throws IOException {
        int userId = 1;
        int needId = 100;
        when(needDAO.getNeed(needId)).thenThrow(new IOException("Database error"));

        ResponseEntity<FundingBasket> response = fundingBasketController.addNeed(userId, needId, 5);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteNeed_Success() throws IOException {
        int userId = 1;
        int needId = 100;
        FundingBasket mockBasket = new FundingBasket(userId);
        
        when(fundingBasketDAO.deleteNeed(userId, needId)).thenReturn(true);
        when(fundingBasketDAO.getFundingBasket(userId)).thenReturn(mockBasket);
        
        ResponseEntity<FundingBasket> response = fundingBasketController.deleteNeed(userId, needId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBasket, response.getBody());
    }

    @Test
    public void testDeleteNeed_NotFound() throws IOException {
        int userId = 1;
        int needId = 100;
        
        when(fundingBasketDAO.deleteNeed(userId, needId)).thenReturn(false);

        ResponseEntity<FundingBasket> response = fundingBasketController.deleteNeed(userId, needId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteNeed_InternalServerError() throws IOException {
        int userId = 1;
        int needId = 100;
        
        when(fundingBasketDAO.deleteNeed(userId, needId)).thenThrow(new IOException("Database error"));

        ResponseEntity<FundingBasket> response = fundingBasketController.deleteNeed(userId, needId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCheckout()throws IOException{
        when(fundingBasketDAO.checkout(1)).thenReturn(20.0);
        when(fundingBasketDAO.checkout(2)).thenReturn(-1.0);
        when(fundingBasketDAO.checkout(3)).thenThrow(new IOException(" "));
        ResponseEntity<Double> response=fundingBasketController.checkOut(1);
        ResponseEntity<Double> response2=fundingBasketController.checkOut(2);
        ResponseEntity<Double> response3=fundingBasketController.checkOut(3);
        assertEquals(20, response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response3.getStatusCode());
    }
}
