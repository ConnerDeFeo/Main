package com.ufund.api.controller;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ufund.api.ufundapi.UfundApiApplication;
import com.ufund.api.ufundapi.controller.SubscriptionsController;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.Subscriptions;
import com.ufund.api.ufundapi.persistence.NeedDAO;
import com.ufund.api.ufundapi.persistence.SubscriptionsDAO;

@WebMvcTest(SubscriptionsController.class)
@ContextConfiguration(classes = UfundApiApplication.class)
public class SubscriptionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriptionsDAO subDAO;

    @MockBean
    private NeedDAO needDAO;

    private final int testUserID = 1;
    private final int testNeedID = 2;
    private final String testNeedName = "Bees";
    private final int cost = 5;
    private final int quantity = 10;
    private final String testDate = "11/11/2024";
    private final String type = "Supplies";

    private SubscriptionsController subController;
    private SubscriptionsDAO mockSubDAO;
    private NeedDAO mockNeedDAO;

    @BeforeEach
    public void setUp(){
        mockSubDAO=mock(SubscriptionsDAO.class);
        mockNeedDAO=mock(NeedDAO.class);
        subController=new SubscriptionsController(mockSubDAO, mockNeedDAO);
    }

    @Test
    public void testGetSubscriptions() throws Exception {
        Subscriptions mockSubscriptions = new Subscriptions(testUserID);
        when(subDAO.getSubscriptions(testUserID)).thenReturn(mockSubscriptions);
                
        mockMvc.perform(get("/subscriptions/" + testUserID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        when(mockSubDAO.getSubscriptions(0)).thenReturn(null);
        ResponseEntity<Subscriptions> response=subController.getSubscriptions(0);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        when(mockSubDAO.getSubscriptions(0)).thenThrow(new IOException());
        response=subController.getSubscriptions(0);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetUnprocessedSubscription_NotFound() throws Exception {
        when(subDAO.getUnprocessed(testUserID, testNeedID)).thenReturn(false);

        mockMvc.perform(get("/subscriptions/" + testUserID + "/" + testNeedID))
                .andExpect(status().isNotFound());

        when(mockSubDAO.getUnprocessed(testUserID, testNeedID)).thenReturn(true);
        assertEquals(HttpStatus.OK, subController.getUnprocessed(testUserID, testNeedID).getStatusCode());

        when(mockSubDAO.getUnprocessed(testUserID, testNeedID)).thenThrow(new IOException());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, subController.getUnprocessed(testUserID, testNeedID).getStatusCode());
    }

    @Test
    public void testAddUnprocessedSubscription() throws Exception {
        Need mockNeed = new Need(testNeedID, testNeedName, cost, quantity, type);
        mockNeed.setQuantity(quantity);
        Subscriptions mockSubscriptions = new Subscriptions(testUserID);

        when(needDAO.getNeed(testNeedID)).thenReturn(mockNeed);
        when(subDAO.getSubscriptions(testUserID)).thenReturn(mockSubscriptions);

        mockMvc.perform(put("/subscriptions/" + testUserID + "/" + testNeedID + "/" + quantity))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        when(mockNeedDAO.getNeed(testNeedID)).thenReturn(null);
        assertEquals(HttpStatus.NOT_FOUND, subController.addUnprocessedSubscription(testUserID, testNeedID, quantity).getStatusCode());

        when(mockNeedDAO.getNeed(testNeedID)).thenThrow(new IOException());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, subController.addUnprocessedSubscription(testUserID, testNeedID, quantity).getStatusCode());
    }

    @Test
    public void testDeleteUnprocessedSubscription_Success() throws Exception {
        when(subDAO.deleteUnprocessedSubscription(testUserID, testNeedID)).thenReturn(true);
        
        mockMvc.perform(delete("/subscriptions/" + testUserID + "/unprocessed/" + testNeedID))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUnprocessedSubscription_NotFound() throws Exception {
        when(subDAO.deleteUnprocessedSubscription(testUserID, testNeedID)).thenReturn(false);
        
        mockMvc.perform(delete("/subscriptions/" + testUserID + "/unprocessed/" + testNeedID))
                .andExpect(status().isNotFound());  // Expecting 404 NOT FOUND
    }

    @Test
    public void testDeleteUnprocessedSubscription_InternalServerError() throws Exception {
        when(subDAO.deleteUnprocessedSubscription(testUserID, testNeedID)).thenThrow(new IOException("Database error"));

        mockMvc.perform(delete("/subscriptions/" + testUserID + "/unprocessed/" + testNeedID))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testAddSubscriptions()throws IOException{
        when(mockSubDAO.getSubscriptions(testUserID)).thenReturn(new Subscriptions(testUserID));
        assertEquals(HttpStatus.OK, subController.addSubscriptions(testUserID, testDate).getStatusCode());

        when(mockSubDAO.getSubscriptions(testUserID)).thenReturn(null);
        assertEquals(HttpStatus.NOT_FOUND, subController.addSubscriptions(testUserID, testDate).getStatusCode());

        when(mockSubDAO.getSubscriptions(testUserID)).thenThrow(new IOException());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, subController.addSubscriptions(testUserID, testDate).getStatusCode());
    }

    @Test
    public void testAddSubscriptions_UserNotFound() throws Exception {
        when(subDAO.getSubscriptions(testUserID)).thenReturn(null);

        mockMvc.perform(put("/subscriptions/" + testUserID + "/" + testDate)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());  // Expecting 404 NOT FOUND
    }

    @Test
    public void testDeleteSubscription() throws Exception {
        when(mockSubDAO.deleteSubscription(testUserID, testNeedID)).thenReturn(true);
        assertEquals(HttpStatus.OK,subController.deleteSubscription(testUserID, testNeedID).getStatusCode());

        when(mockSubDAO.deleteSubscription(testUserID, testNeedID)).thenReturn(false);
        assertEquals(HttpStatus.NOT_FOUND,subController.deleteSubscription(testUserID, testNeedID).getStatusCode());

        when(mockSubDAO.deleteSubscription(testUserID, testNeedID)).thenThrow(new IOException());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,subController.deleteSubscription(testUserID, testNeedID).getStatusCode());
    }
}