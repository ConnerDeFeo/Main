package com.ufund.api.model;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.NeedDatePair;
import com.ufund.api.ufundapi.model.Subscriptions;

public class SubscriptionsTest {

    private Subscriptions subscriptions;
    private Need need1;
    private Need need2;

    @BeforeEach
    public void setUp() {
        subscriptions = new Subscriptions(1);
        need1 = new Need(0, "Bee", 1, 20, "Supplies"); 
        need2 = new Need(1, "Tip", 1, 20, "Supplies");
    }

    @Test
    public void testGetUserId() {
        assertEquals(1, subscriptions.getUserId());
    }

    @Test
    public void testAddAndGetUnprocessedSubscription() {
        subscriptions.addUnprocessedSubscription(need1);
        subscriptions.addUnprocessedSubscription(need2);
        List<Need> unprocessed = subscriptions.getUnprocessedSubscriptions();
        
        assertTrue(unprocessed.contains(need1));
        assertTrue(unprocessed.contains(need2));
        assertEquals("Bee", unprocessed.get(0).getName());
        assertEquals("Supplies", unprocessed.get(1).getType());
    }

    @Test
    public void testGetUnprocessed() {
        subscriptions.addUnprocessedSubscription(need1);
        assertTrue(subscriptions.getUnprocessed(0)); 
        assertFalse(subscriptions.getUnprocessed(999)); 
    }


    @Test
    public void testDeleteUnprocessedSubscription() {
        assertFalse(subscriptions.deleteUnprocessedSubscription(999)); 

        subscriptions.addUnprocessedSubscription(need1);
        
        assertTrue(subscriptions.deleteUnprocessedSubscription(0)); 
        assertFalse(subscriptions.getUnprocessedSubscriptions().contains(need1)); 
        
        assertFalse(subscriptions.deleteUnprocessedSubscription(999)); 
    }

    @Test
    public void testAddSubscriptions() {
        subscriptions.addUnprocessedSubscription(need1);
        subscriptions.addUnprocessedSubscription(need2);
        subscriptions.addSubscriptions("2023-11-11");

        List<NeedDatePair> allSubscriptions = subscriptions.getSubscriptions();
        
        assertEquals(2, allSubscriptions.size());
        assertEquals("2023-11-11", allSubscriptions.get(0).getDate());
        
        assertTrue(allSubscriptions.get(0).getNeed().equals(need1) || allSubscriptions.get(1).getNeed().equals(need1));
        assertTrue(allSubscriptions.get(0).getNeed().equals(need2) || allSubscriptions.get(1).getNeed().equals(need2));

        assertTrue(subscriptions.getUnprocessedSubscriptions().isEmpty()); 
    }

    @Test
    public void testDeleteSubscription() {
        assertFalse(subscriptions.deleteSubscription(355));
        subscriptions.addUnprocessedSubscription(need1);
        subscriptions.addSubscriptions("2023-11-11");

        assertTrue(subscriptions.deleteSubscription(0)); 
        assertFalse(subscriptions.getSubscriptions().stream()
                                  .anyMatch(n -> n.getNeed().getId() == 0)); 
        assertFalse(subscriptions.deleteSubscription(355));
    }
}