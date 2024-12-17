package com.ufund.api.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.NeedFileDAO;

public class NeedFileDAOTest {
    private NeedFileDAO needFileDAO;
    private Need[] needs;
    private ObjectMapper objectMapper;
    private Need testNeed;

    @BeforeEach
    public void setupNeedFileDAO()throws IOException{
       objectMapper = mock(ObjectMapper.class);
       needs = new Need[3];
       needs[0] = new Need(1, "A", 1.1, 10, "Supply");
       needs[1] = new Need(2, "B", 2.2, 20, "Volunteer");
       needs[2] = new Need(3, "C", 3.3, 30, "Monetary");
       testNeed = new Need(4, "D", 4.4, 40, "Supply");
       when(objectMapper.readValue(new File("x"), Need[].class)).thenReturn(needs);
       needFileDAO = new NeedFileDAO("x", objectMapper);
    }

    @Test
    public void  testGetNeeds() throws IOException{
        Need[] new_needs =  needFileDAO.getNeeds();
        assertEquals(new_needs.length, needs.length);
        for(int i = 0; i < needs.length; i++){
            assertEquals(new_needs[i], needs[i]);
        }
    }

    @Test
    public void testFindNeeds() throws IOException{
        assertEquals(1, needFileDAO.findNeeds("A")[0].getId());
        assertEquals(0, needFileDAO.findNeeds("apple").length);
    }

    @Test
    public void  testGetNeed() throws IOException{
        assertEquals("A", needFileDAO.getNeed(1).getName());
        assertEquals("B", needFileDAO.getNeed(2).getName());
        assertEquals("C", needFileDAO.getNeed(3).getName());
        assertNull(needFileDAO.getNeed(99));
    }

    @Test
    public void  testCreateNeed() throws IOException{
        Need result = assertDoesNotThrow(() -> needFileDAO.createNeed(testNeed), "Exception Thrown");
        assertNotNull(result);
        Need actual = needFileDAO.getNeed(result.getId());
        assertEquals(actual.getId(), 4);
        assertEquals(actual.getName(), "D");
        assertNull(needFileDAO.createNeed(testNeed));
    }

    @Test
    public void  testUpdateNeed() throws IOException{
        Need newNeed = assertDoesNotThrow(() -> needFileDAO.updateNeed(new Need(3, "E", 5.5, 50, "Supply")));
        assertNotNull(newNeed);
        assertEquals(newNeed.getId(), 3);
        assertEquals(newNeed.getName(), "E");
        assertNull(needFileDAO.updateNeed(new Need(1000, null, 0, 0, null)));
    }

    @Test
    public void testDeleteNeed() throws IOException{
        assertTrue(needFileDAO.deleteNeed(3));
        assertNull(needFileDAO.getNeed(3));
        assertFalse(needFileDAO.deleteNeed(1000));
    }

    @Test
    public void testLoad()throws IOException{
        needs=new Need[2];
        needs[0]=new Need(1, "a", 0, 0, null);
        needs[1]=new Need(10, "b", 0, 0, null);
        when(objectMapper.readValue(new File("x"), Need[].class)).thenReturn(needs);
        needFileDAO = new NeedFileDAO("x", objectMapper);
        needFileDAO.createNeed(testNeed);
        assertNotNull(needFileDAO.getNeed(11));
    }
}
