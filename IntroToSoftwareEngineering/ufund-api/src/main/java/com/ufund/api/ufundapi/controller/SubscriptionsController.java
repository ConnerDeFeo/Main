package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.Subscriptions;
import com.ufund.api.ufundapi.persistence.NeedDAO;
import com.ufund.api.ufundapi.persistence.SubscriptionsDAO;

@RestController
@RequestMapping("subscriptions")
public class SubscriptionsController {
    private static final Logger LOG = Logger.getLogger(SubscriptionsController.class.getName()) ;
    private SubscriptionsDAO subDAO;
    private NeedDAO needDAO;

    public SubscriptionsController(SubscriptionsDAO subDAO, NeedDAO needDAO) {
        this.subDAO = subDAO;
        this.needDAO = needDAO;
    }

    @GetMapping("/{userID}")
    public ResponseEntity<Subscriptions> getSubscriptions(@PathVariable int userID){
        LOG.info("GET /subscriptions/" + userID);
        try{
            Subscriptions subs = subDAO.getSubscriptions(userID);
            if(subs!=null){
                return new ResponseEntity<Subscriptions>(subs,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userID}/{needID}")
    public ResponseEntity<Subscriptions> getUnprocessed(@PathVariable int userID, @PathVariable int needID){
        LOG.info("GET /subscription/" + userID + "/" + needID);
        try{
            if(subDAO.getUnprocessed(userID, needID)){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{userID}/{needID}/{quantity}")
    public ResponseEntity<Subscriptions> addUnprocessedSubscription(@PathVariable int userID,@PathVariable int needID, @PathVariable int quantity){
        LOG.info("PUT /subscriptions/"+userID+"/"+needID+"/"+quantity);
        try{
            Need n = needDAO.getNeed(needID);
            Subscriptions subs = subDAO.getSubscriptions(userID);
            if( n!=null && subs!=null ){
                n.setQuantity(quantity);
                subDAO.addUnprocessedSubscription(userID, n);
                return new ResponseEntity<Subscriptions>(subDAO.getSubscriptions(userID),HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userID}/unprocessed/{needID}")
    public ResponseEntity<Subscriptions> deleteUnprocessedSubscription(@PathVariable int userID,@PathVariable int needID){
        LOG.info("DELETE /subscriptions/"+userID+"/"+needID);
        try{
            if(subDAO.deleteUnprocessedSubscription(userID, needID)){
                return new ResponseEntity<Subscriptions>(subDAO.getSubscriptions(userID),HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{userID}/{date}")
    public ResponseEntity<Subscriptions> addSubscriptions(@PathVariable int userID,@PathVariable String date){
        LOG.info("PUT /subscriptions/"+userID+"/"+date);
        try{
            Subscriptions subs = subDAO.getSubscriptions(userID);
            if( subs!=null ){
                subDAO.addSubscriptions(userID, date);
                return new ResponseEntity<Subscriptions>(subDAO.getSubscriptions(userID),HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userID}/processed/{needID}")
    public ResponseEntity<Subscriptions> deleteSubscription(@PathVariable int userID, @PathVariable int needID){
        LOG.info("DELETE /subscriptions/"+userID+"/"+needID);
        try{
            if(subDAO.deleteSubscription(userID, needID)){
                return new ResponseEntity<Subscriptions>(subDAO.getSubscriptions(userID),HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
