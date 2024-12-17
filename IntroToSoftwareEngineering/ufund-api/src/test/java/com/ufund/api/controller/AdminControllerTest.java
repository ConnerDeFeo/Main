package com.ufund.api.controller;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufund.api.ufundapi.controller.AdminController;
import com.ufund.api.ufundapi.model.Admin;
import com.ufund.api.ufundapi.persistence.AdminDAO;

public class AdminControllerTest {
    private AdminController controller;
    private AdminDAO DAO;
    Admin test_admin;

    @BeforeEach
    public void setupAdminController(){
        DAO=mock(AdminDAO.class);
        controller=new AdminController(DAO);
        test_admin=new Admin(1, "A");
    }

    @Test
    public void testExceptionHandling()throws IOException{
        doThrow(new IOException()).when(DAO).getAdmin(1);
        doThrow(new IOException()).when(DAO).getAdmins();
        doThrow(new IOException()).when(DAO).updateAdmin(test_admin);
        doThrow(new IOException()).when(DAO).deleteAdmin(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, controller.getAdmin(1).getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, controller.getAdmins().getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, controller.updateAdmin(test_admin).getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, controller.deleteAdmin(1).getStatusCode());
    }

    @Test
    public void testGetAdmin()throws IOException{
        when(DAO.getAdmin(1)).thenReturn(test_admin);
        ResponseEntity<Admin> response=controller.getAdmin(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(test_admin,response.getBody());
    }

    @Test
    public void testGetAdminFaliure()throws IOException{
        when(DAO.getAdmin(1)).thenReturn(null);
        ResponseEntity<Admin> response=controller.getAdmin(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAdmins()throws IOException{
        Admin[] list=new Admin[1];
        list[0]=test_admin;
        when(DAO.getAdmins()).thenReturn(list);
        ResponseEntity<Admin[]> response=controller.getAdmins();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    public void testCreateAdmin()throws IOException{
        when(DAO.createAdmin(test_admin)).thenReturn(test_admin);
        ResponseEntity<Admin> response=controller.createAdmin(test_admin);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(test_admin, response.getBody());
    }

    @Test
    public void testCreateAdminFaliure()throws IOException{
        when(DAO.createAdmin(test_admin)).thenReturn(null);
        ResponseEntity<Admin> response=controller.createAdmin(test_admin);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testUpdateAdmin()throws IOException{
        when(DAO.updateAdmin(test_admin)).thenReturn(test_admin);
        ResponseEntity<Admin> response=controller.updateAdmin(test_admin);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(test_admin, response.getBody());
    }

    @Test
    public void testUpdateAdminFaliure()throws IOException{
        when(DAO.updateAdmin(test_admin)).thenReturn(null);
        ResponseEntity<Admin> response=controller.updateAdmin(test_admin);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteAdmin()throws IOException{
        when(DAO.deleteAdmin(1)).thenReturn(true);
        ResponseEntity<Admin> response=controller.deleteAdmin(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteAdminFaliure()throws IOException{
        when(DAO.deleteAdmin(1)).thenReturn(false);
        ResponseEntity<Admin> response=controller.deleteAdmin(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
