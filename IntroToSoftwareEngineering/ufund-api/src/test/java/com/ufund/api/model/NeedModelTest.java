package com.ufund.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.NeedDatePair;

public class NeedModelTest {

    private Need need;
    private NeedDatePair ndPair;

    @BeforeEach
    public void setUp() {
        need = new Need(1, "Gardening Kit", 20.0, 10, "Tools");
        ndPair=new NeedDatePair(need, "Today");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(1, need.getId());
        assertEquals("Gardening Kit", need.getName());
        assertEquals(20.0, need.getCost());
        assertEquals(10, need.getQuantity());
        assertEquals("Tools", need.getType());
    }

    @Test
    public void testSetName() {
        need.setName("Bucket");
        assertEquals("Bucket", need.getName());
    }

    @Test
    public void testSetCost() {
        need.setCost(10.0);
        assertEquals(10.0, need.getCost());
    }

    @Test
    public void testSetQuantity() {
        need.setQuantity(3);
        assertEquals(3, need.getQuantity());
    }

    @Test
    public void testSetType() {
        need.setType("Container");
        assertEquals("Container", need.getType());
    }

    @Test
    public void testToString() {
        String expectedString = String.format(Need.STRING_FORMAT, "Gardening Kit");
        assertEquals(expectedString, need.toString());
    }

    @Test
    public void testEquals(){
        Need n1=new Need(0, null, 0, 0, null);
        Need n2=new Need(0, null, 0, 0, null);
        Need n3=new Need(1, null, 0, 0, null);
        assertEquals(n1, n2);
        assertNotEquals(n1, n3);
        assertNotEquals(n2, "whatup");
    }

    @Test
    public void testNeedDatePair(){
        ndPair.setNeed(new Need(0, null, 0, 0, null));
        ndPair.setDate("NewDate");
        assertEquals(0, ndPair.getNeed().getCost());
        assertEquals("NewDate", ndPair.getDate());
    }
}
