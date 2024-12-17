package com.ufund.api.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.WishList;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.WishListFileDAO;

public class WishListTest {
    private WishListFileDAO WLDAO;
    private ObjectMapper om;
    private WishList[] wls;
    private Need testNeed;

    @BeforeEach
    public void setUp() throws IOException{
        om=mock(ObjectMapper.class);
        wls=new WishList[3];
        wls[0]=new WishList(1);
        wls[1]=new WishList(2);
        wls[2]=new WishList(3);
        testNeed = new Need(4, "D", 4, 10, "Supply");
        when(om.readValue(new File("x"), WishList[].class)).thenReturn(wls);
        WLDAO=new WishListFileDAO("x", om);
    }

    @Test
    public void testGetWishList()throws IOException{
        assertEquals(0, WLDAO.getWishList(3).size());
    }

    @Test
    public void testCreateWishList()throws IOException{
        WLDAO.createWishList(4);
        assertNotNull(WLDAO.getWishList(4));
        assertFalse(WLDAO.createWishList(4));
    }

    @Test
    public void testDeleteWishList() throws IOException{
        WLDAO.deleteWishList(3);
        assertNull(WLDAO.getWishList(3));
        assertFalse(WLDAO.deleteWishList(1000));
    }

    @Test
    public void testAddNeed()throws IOException{
        WLDAO.addNeed(1, testNeed);
        assertEquals(10, WLDAO.getWishList(1).size());
        assertFalse(WLDAO.addNeed(10, testNeed));
    }

    @Test
    public void testDeleteNeed() throws IOException{
        WLDAO.addNeed(1, testNeed);
        assertTrue(WLDAO.deleteNeed(1, 4));
        assertEquals(0, WLDAO.getWishList(1).size());
        assertFalse(WLDAO.deleteNeed(10, 0));
    }

}
