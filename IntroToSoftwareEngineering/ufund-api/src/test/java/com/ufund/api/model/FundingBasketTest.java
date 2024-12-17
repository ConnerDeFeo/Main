package com.ufund.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.FundingBasket;
import com.ufund.api.ufundapi.model.Need;

public class FundingBasketTest {

    private FundingBasket fb;

    @BeforeEach
    public void setUp(){
        fb=new FundingBasket(1);
    }

    @Test
    public void testConstructor(){
        assertEquals(0, fb.getBasket().size());
        assertEquals(1, fb.getUserId());
        assertEquals(0,fb.getTotalCost());
    }

    @Test
    public void addNeed(){
        fb.add_need(new Need(1, "Jack", 10, 2, "Cool"));

        assertEquals(20, fb.getTotalCost());
        assertEquals(2, fb.size());
        assertEquals(1, fb.getBasket().get(0).getId());

        fb.add_need(new Need(1, "Jack", 10, 2, "Cool"));
        assertEquals(4, fb.size());
    }

    @Test
    public void testDeleteNeed(){
        fb.add_need(new Need(1, "Jack", 0, 0, "null"));
        

        assertTrue(fb.deleteNeed(1));
        assertFalse(fb.deleteNeed(1));
    }

    @Test
    public void testCheckout(){
        fb.add_need(new Need(1, "Jack", 10, 2, "null"));
        double cost=fb.checkout();
        assertEquals(0, fb.getBasket().size());
        assertEquals(20,cost);
    }
}
