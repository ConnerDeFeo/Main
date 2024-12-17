package com.ufund.api.controller;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufund.api.ufundapi.controller.NeedController;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.NeedDAO;

@Tag("Controller-tier")
public class NeedControllerTest {
    private NeedController mockNeedController;
    private NeedDAO mockNeedDAO;
    
    @BeforeEach
    public void setupNeedController() {
        mockNeedDAO = mock(NeedDAO.class);
        mockNeedController = new NeedController(mockNeedDAO);
    }

    @Test
    public void testGetNeed() throws IOException {  // getNeed may throw IOException
        // Setup
        Need need = new Need(99,"Gardening Kit", 20.00, 1, "Tools");
        // When the same id is passed in, our mock Need DAO will return the Need object
        when(mockNeedDAO.getNeed(need.getId())).thenReturn(need);

        // Invoke
        ResponseEntity<Need> response = mockNeedController.getNeed(need.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    @Test
    public void testGetNeeds() throws IOException {  // getNeed may throw IOException
        // When the same id is passed in, our mock Need DAO will return the Need object
        Need[] needs=new Need[3];
        when(mockNeedDAO.getNeeds()).thenReturn(needs);

        // Invoke
        ResponseEntity<Need[]> response = mockNeedController.getNeeds();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());

        when(mockNeedDAO.getNeeds()).thenThrow(new IOException());
        ResponseEntity<Need[]> badResponse=mockNeedController.getNeeds();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, badResponse.getStatusCode());
    }


    @Test
    public void testGetNeedNotFound() throws Exception { // createNeed may throw IOException
        // Setup
        int needId = 99;
        // When the same id is passed in, our mock Need DAO will return null, simulating
        // no need found
        when(mockNeedDAO.getNeed(needId)).thenReturn(null);

        // Invoke
        ResponseEntity<Need> response = mockNeedController.getNeed(needId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetNeedHandleException() throws Exception { // createNeed may throw IOException
        // Setup
        int needId = 99;
        // When getNeed is called on the Mock Need DAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDAO).getNeed(needId);

        // Invoke
        ResponseEntity<Need> response = mockNeedController.getNeed(needId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testCreateNeed() throws IOException {  // createNeed may throw IOException
        // Setup
        Need need = new Need(99,"Gardening Kit", 20.00, 1, "Tools");
        // when createHero is called, return true simulating successful
        // creation and save
        when(mockNeedDAO.createNeed(need)).thenReturn(need);

        // Invoke
        ResponseEntity<Need> response = mockNeedController.createNeed(need);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    @Test
    public void testCreateHeroFailed() throws IOException {  // createNeed may throw IOException
        // Setup
        Need need = new Need(99,"Gardening Kit", 20.00, 1, "Tools");
        // when createNeed is called, return null simulating failed
        // creation and save
        when(mockNeedDAO.createNeed(need)).thenReturn(null);

        // Invoke
        ResponseEntity<Need> response = mockNeedController.createNeed(need);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateHeroHandleException() throws IOException {  // createHero may throw IOException
        // Setup
        Need need = new Need(99,"Gardening Kit", 20.00, 1, "Tools");

        // When createNeed is called on the Mock Need DAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDAO).createNeed(need);

        // Invoke
        ResponseEntity<Need> response = mockNeedController.createNeed(need);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchNeeds() throws IOException { // findNeeds may throw IOException
        // Setup
        String searchString = "la";
        Need[] needs = new Need[2];
        needs[0] = new Need(99, "Spider-man", 1.1, 5, "Guy");
        needs[1] = new Need(100,"Spider-Woman", 2.5, 10, "Girl");
        // When findNeed is called with the search string, return the two
        /// Needs above
        when(mockNeedDAO.findNeeds(searchString)).thenReturn(needs);

        // Invoke
        ResponseEntity<Need[]> response = mockNeedController.searchNeeds(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());
    }

    @Test
    public void testSearchNeedsHandleException() throws IOException { // findNeeds may throw IOException
        // Setup
        String searchString = "an";
        // When createNeed is called on the Mock Need DAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDAO).findNeeds(searchString);

        // Invoke
        ResponseEntity<Need[]> response = mockNeedController.searchNeeds(searchString);
        System.out.println(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateNeed() throws IOException { // updateNeed may throw IOException
        // Setup
        Need need = new Need(0, "Saturo Gojo", 100, 1, "good kind");
        // when updateNeed is called, return true simulating successful
        // update and save
        when(mockNeedDAO.updateNeed(need)).thenReturn(need);
        ResponseEntity<Need> response = mockNeedController.updateNeed(need);
        need.setName("Sukuna");

        // Invoke
        response = mockNeedController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    @Test
    public void testUpdateNeedFailed() throws IOException { // updateNeed may throw IOException
        // Setup
        Need need = new Need(100, "Saturo Gojo", 100, 1, "bad");
        // when updateNeed is called, return true simulating successful
        // update and save
        when(mockNeedDAO.updateNeed(need)).thenReturn(null);

        // Invoke
        ResponseEntity<Need> response = mockNeedController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateNeedHandleException() throws IOException { // updateNeed may throw IOException
        // Setup
        Need need = new Need(100, "Saturo Gojo", 100, 1, "bad");
        // When updateNeed is called on the Mock Need DAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDAO).updateNeed(need);

        // Invoke
        ResponseEntity<Need> response = mockNeedController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteNeed() throws IOException { // deleteNeed may throw IOException
        // Setup
        int id = 99;
        // when deleteNeed is called return true, simulating successful deletion
        when(mockNeedDAO.deleteNeed(id)).thenReturn(true);

        // Invoke
        ResponseEntity<Need> response = mockNeedController.deleteNeed(id);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteNeedNotFound() throws IOException { // deleteNeed may throw IOException
        // Setup
        int id = 99;
        // when deleteNeed is called return false, simulating failed deletion
        when(mockNeedDAO.deleteNeed(id)).thenReturn(false);

        // Invoke
        ResponseEntity<Need> response = mockNeedController.deleteNeed(id);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteNeedHandleException() throws IOException { // deleteNeed may throw IOException
        // Setup
        int id = 99;
        // When deleteNeed is called on the Mock Need DAO, throw an IOException
        doThrow(new IOException()).when(mockNeedDAO).deleteNeed(id);

        // Invoke
        ResponseEntity<Need> response = mockNeedController.deleteNeed(id);


        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
