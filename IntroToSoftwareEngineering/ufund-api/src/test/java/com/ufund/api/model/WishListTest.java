package com.ufund.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.WishList;

public class WishListTest {
    private WishList wl;

    @BeforeEach
    public void setUp(){
        wl=new WishList(1);
    }

    @Test
    public void testConstructor(){
        assertEquals(0, wl.getWishList().size());
        assertEquals(1, wl.getUserId());
    }

    @Test
    public void addNeed(){
        wl.add_need(new Need(1, "Jack", 10, 2, "Cool"));

        assertEquals(2, wl.size());
        assertEquals(1, wl.getWishList().get(0).getId());

        wl.add_need(new Need(1, "Jack", 10, 2, "Cool"));
        assertEquals(4, wl.size());

    }

    @Test
    public void testDeleteNeed(){
        wl.add_need(new Need(1, "Jack", 0, 0, "null"));
        

        assertTrue(wl.deleteNeed(1));
        assertFalse(wl.deleteNeed(1));
    }

}
