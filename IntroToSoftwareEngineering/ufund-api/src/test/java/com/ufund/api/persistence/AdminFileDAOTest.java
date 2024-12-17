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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Admin;
import com.ufund.api.ufundapi.persistence.AdminFileDAO;

@Tag("Persistence-teir")
public class AdminFileDAOTest {
    AdminFileDAO AFD;
    Admin[] admins;
    ObjectMapper MOB;
    Admin test_admin;
    
    @BeforeEach
    public void setupAdminFileDAO() throws IOException{
        MOB = mock(ObjectMapper.class);
        admins=new Admin[3];
        admins[0]=new Admin(1, "A");
        admins[1]=new Admin(2, "B");
        admins[2]=new Admin(3, "C");
        test_admin=new Admin(100, "D");

        when(MOB.readValue(new File("x"), Admin[].class)).thenReturn(admins);
        AFD=new AdminFileDAO("x", MOB);
    }

    @Test
    public void testGetAdmins(){
        Admin[] new_admins=AFD.getAdmins();
        assertEquals(new_admins.length,admins.length);
        for(int i=0; i<admins.length;i++)
            assertEquals(new_admins[i], admins[i]);
    }

    @Test
    public void testGetAdmin()throws IOException{
        assertEquals("A", AFD.getAdmin(1).getUsername());
        assertEquals("B", AFD.getAdmin(2).getUsername());
        assertEquals("C", AFD.getAdmin(3).getUsername());
        assertNull(AFD.getAdmin(4));
    }

    @Test
    public void testCreateAdmin()throws IOException{
        //()-> is used simply so it is an executable type of code. I.E, the code is executed in the assertDoesNotThrow()
        //function and not within the test instance
        Admin result=assertDoesNotThrow(() -> AFD.createAdmin(test_admin),"Exception Thrown you silly goose");
        assertNotNull(result);
        Admin actual=AFD.getAdmin(result.getId());
        //the id would be 4 and not 5 because the file generates a unique id for the admin given
        assertEquals(actual.getId(),4);
        assertEquals(actual.getUsername(), "D");
    }

    @Test
    public void testCreateDuplicateAdmin()throws IOException{
        Admin a1=AFD.createAdmin(test_admin);
        Admin a2=assertDoesNotThrow(() -> AFD.createAdmin(test_admin),"Exception Thrown you silly goose");
        assertNotNull(a1);
        assertNull(a2);
    }

    @Test
    public void testUpdateAdmin()throws IOException{
        Admin a1=assertDoesNotThrow(()->AFD.updateAdmin(new Admin(3, "E")));
        assertNotNull(a1);
        assertEquals(a1.getId(),3);
        assertEquals(a1.getUsername(), "E");

    }

    @Test
    public void testUpdateAdminFaliure(){
        Admin a1=assertDoesNotThrow(()->AFD.updateAdmin(test_admin));
        assertNull(a1);
    }

    @Test
    public void testDeleteAdmin()throws IOException{
        assertTrue(AFD.deleteAdmin(1));
        assertNull(AFD.getAdmin(1));
    }

    @Test
    public void DeleteAdminFaliure()throws IOException{
        assertFalse(AFD.deleteAdmin(4));
    }
}
