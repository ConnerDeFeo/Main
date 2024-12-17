package com.ufund.api.persistence;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.Subscriptions;
import com.ufund.api.ufundapi.persistence.SubscriptionsFileDAO;

public class SubscriptionsFileDAOTest {
    private SubscriptionsFileDAO subsDAO;
    private ObjectMapper mockObjectMapper;
    private Need mockNeed;
    private Subscriptions[] mockSubsArray;
    
    @BeforeEach
    public void setUp() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        mockNeed = new Need(1, "Bees", 1, 1000, "Supplies");
        mockSubsArray = new Subscriptions[] {
            new Subscriptions(1), new Subscriptions(2), new Subscriptions(3)
        };
        
        when(mockObjectMapper.readValue(new File("testFile.json"), Subscriptions[].class))
            .thenReturn(mockSubsArray);

        subsDAO = new SubscriptionsFileDAO("testFile.json", mockObjectMapper);
    }

    @Test
    public void testCreateSubscriptions_NewUserId_ShouldCreateAndSaveSubscription() throws IOException {
        assertTrue(subsDAO.createSubscriptions(4));
        assertNotNull(subsDAO.getSubscriptions(4));
        when(subsDAO.createSubscriptions(4)).thenThrow(new IOException());
        assertFalse(subsDAO.createSubscriptions(4));
    }

    @Test
    public void testCreateSubscriptions_ExistingUserId_ShouldReturnFalse() throws IOException {
        assertFalse(subsDAO.createSubscriptions(1));  // 1 is already in the mock array
    }

    @Test
    public void testDeleteSubscriptions_ExistingUserId_ShouldDeleteAndSaveSubscription() throws IOException {
        assertTrue(subsDAO.deleteSubscriptions(1));
        assertNull(subsDAO.getSubscriptions(1));
    }

    @Test
    public void testDeleteSubscriptions_NonExistingUserId_ShouldReturnFalse() throws IOException {
        assertFalse(subsDAO.deleteSubscriptions(99));
    }

    @Test
    public void testAddUnprocessedSubscription_ExistingUser_ShouldAddNeedAndSave() throws IOException {
        assertTrue(subsDAO.addUnprocessedSubscription(1, mockNeed));
        assertTrue(subsDAO.getSubscriptions(1).getUnprocessed(mockNeed.getId()));
    }

    @Test
    public void testAddUnprocessedSubscription_NonExistingUser_ShouldReturnFalse() throws IOException {
        assertFalse(subsDAO.addUnprocessedSubscription(99, mockNeed));
    }

    @Test
    public void testDeleteUnprocessedSubscription_ExistingUserAndSubId_ShouldDeleteAndSave() throws IOException {
        subsDAO.addUnprocessedSubscription(1, mockNeed);
        assertTrue(subsDAO.deleteUnprocessedSubscription(1, mockNeed.getId()));
    }

    @Test
    public void testDeleteUnprocessedSubscription_NonExistingUserOrSubId_ShouldReturnFalse() throws IOException {
        assertFalse(subsDAO.deleteUnprocessedSubscription(1, 99));
        assertFalse(subsDAO.deleteUnprocessedSubscription(99, mockNeed.getId()));
    }

    @Test
    public void testAddSubscriptions_ExistingUser_ShouldTransferNeedsAndSave() throws IOException {
        subsDAO.addUnprocessedSubscription(1, mockNeed);
        assertTrue(subsDAO.addSubscriptions(1, "2024-01-01"));
        assertFalse(subsDAO.getSubscriptions(1).getUnprocessed(mockNeed.getId()));
        assertFalse(subsDAO.addSubscriptions(10000, "x"));
    }

    @Test
    public void testDeleteSubscription_ExistingUserAndNeedId_ShouldDeleteAndSave() throws IOException {
        subsDAO.addUnprocessedSubscription(1, mockNeed);
        subsDAO.addSubscriptions(1, "11/11/2024");
        assertTrue(subsDAO.deleteSubscription(1, mockNeed.getId()));
        assertFalse(subsDAO.deleteSubscription(10000, 1));
    }

    @Test
    public void testGetUnprocessed_ExistingUserAndNeedId_ShouldReturnTrueIfExists() throws IOException {
        subsDAO.addUnprocessedSubscription(1, mockNeed);
        assertTrue(subsDAO.getUnprocessed(1, mockNeed.getId()));
    }

    @Test
    public void testGetSubscriptions_ExistingUser_ShouldReturnSubscriptions() {
        Subscriptions result = subsDAO.getSubscriptions(1);
        assertNotNull(result);
    }
}