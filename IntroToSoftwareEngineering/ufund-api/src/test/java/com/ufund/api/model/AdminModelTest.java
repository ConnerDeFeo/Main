package com.ufund.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Admin;

@Tag("Model-Teir")
public class AdminModelTest {
    
    @Test
    public void testConstructor(){
        int ei=100;
        String ea="Jackery";
        Admin admin=new Admin(ei, ea);
        assertEquals(ei, admin.getId());
        assertEquals(ea, admin.getUsername());
    }

    @Test
    public void testSetName(){
        int id = 99;
        String name = "Dom";
        Admin admin = new Admin(id,name);

        String expected_name = "Dominator";

        admin.setUsername(expected_name);

        assertEquals(expected_name,admin.getUsername());
    }

    @Test
    public void testToString(){
        int ei=100;
        String ea="Jackery";
        Admin admin=new Admin(ei, ea);
        assertEquals("Admin [id=100, username=Jackery]", admin.toString());
    }
}
