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
import com.ufund.api.ufundapi.model.FundingBasket;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.FundingBasketFileDAO;

public class FundingBasketFileDAOTest {
    private FundingBasketFileDAO FBDAO;
    private ObjectMapper om;
    private FundingBasket[] fbs;
    private Need testNeed;

    @BeforeEach
    public void setUp() throws IOException{
        om=mock(ObjectMapper.class);
        fbs=new FundingBasket[3];
        fbs[0]=new FundingBasket(1);
        fbs[1]=new FundingBasket(2);
        fbs[2]=new FundingBasket(3);
        testNeed = new Need(4, "D", 4, 10, "Supply");
        when(om.readValue(new File("x"), FundingBasket[].class)).thenReturn(fbs);
        FBDAO=new FundingBasketFileDAO("x", om);
    }

    @Test
    public void testGetFundingBasket()throws IOException{
        assertEquals(0, FBDAO.getFundingBasket(3).size());
    }

    @Test
    public void testCreateFundingBasket()throws IOException{
        FBDAO.createFundingBasket(4);
        assertNotNull(FBDAO.getFundingBasket(4));
        assertFalse(FBDAO.createFundingBasket(4));
    }

    @Test
    public void testDeleteFundingBasket() throws IOException{
        FBDAO.deleateFundingBasket(3);
        assertNull(FBDAO.getFundingBasket(3));
        assertFalse(FBDAO.deleateFundingBasket(1000));
    }

    @Test
    public void testAddNeed()throws IOException{
        FBDAO.addNeed(1, testNeed);
        assertEquals(10, FBDAO.getFundingBasket(1).size());
        assertEquals(40, FBDAO.getFundingBasket(1).getTotalCost());
        assertFalse(FBDAO.addNeed(10, testNeed));
    }

    @Test
    public void testDeleteNeed() throws IOException{
        FBDAO.addNeed(1, testNeed);
        assertTrue(FBDAO.deleteNeed(1, 4));
        assertEquals(0, FBDAO.getFundingBasket(1).getTotalCost());
        assertEquals(0, FBDAO.getFundingBasket(1).size());
        assertFalse(FBDAO.deleteNeed(10, 0));
    }

    @Test
    public void testCheckOut()throws IOException{
        FBDAO.addNeed(1, testNeed);
        assertEquals(40, FBDAO.checkout(1));
        assertEquals(0, FBDAO.getFundingBasket(1).size());
        assertEquals(-1, FBDAO.checkout(10));
    }

}
