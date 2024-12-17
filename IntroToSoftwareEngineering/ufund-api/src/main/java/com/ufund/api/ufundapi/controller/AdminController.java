package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.Admin;
import com.ufund.api.ufundapi.persistence.AdminDAO;

@RestController
@RequestMapping("admins")
public class AdminController {
    private static final Logger LOG = Logger.getLogger(NeedController.class.getName());
    private AdminDAO adminDao;

    public AdminController(AdminDAO adminDao){
        this.adminDao=adminDao;
    }

    @RequestMapping("")
    public ResponseEntity<Admin[]> getAdmins(){
        LOG.info("GET/admins");
        try{
            return new ResponseEntity<Admin[]>(adminDao.getAdmins(),HttpStatus.OK);
        }catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("{id}")
    public ResponseEntity<Admin> getAdmin(@PathVariable int id){
        LOG.info("GET/admins/" + id);
        try{
            Admin admin=adminDao.getAdmin(id);
            if(admin!=null){
                return new ResponseEntity<Admin>(admin, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin){
        LOG.info("POST/admins/"+admin);
        try{
            Admin a=adminDao.createAdmin(admin);
            if(a!=null){
                return new ResponseEntity<Admin>(a,HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }catch(IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Admin> deleteAdmin(@PathVariable int id){
        LOG.info("DELETE/admins/" + id);
        try{
            if(adminDao.deleteAdmin(id)){
                return new ResponseEntity<Admin>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Admin> updateAdmin(@RequestBody Admin admin){
        LOG.info("PUT/admins/"+admin);
        try{
            Admin a=adminDao.updateAdmin(admin);
            if(a!=null){
                return new ResponseEntity<Admin>(a,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
