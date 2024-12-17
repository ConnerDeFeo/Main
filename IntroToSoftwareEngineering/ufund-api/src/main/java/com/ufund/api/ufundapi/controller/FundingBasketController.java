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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.FundingBasket;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.FundingBasketDAO;
import com.ufund.api.ufundapi.persistence.NeedDAO;

@RestController
@RequestMapping("fundingBasket")
public class FundingBasketController {
    private static final Logger LOG = Logger.getLogger(FundingBasketController.class.getName());
    private FundingBasketDAO FBDAO;
    private NeedDAO NDAO;

    public FundingBasketController(FundingBasketDAO FBDAO, NeedDAO NDAO){
        this.FBDAO=FBDAO;
        this.NDAO=NDAO;
    }

    @GetMapping("/{userID}")
    public ResponseEntity<FundingBasket> getFundingBasket(@PathVariable int userID){
        LOG.info("GET /fundingBasket");
        try{
            FundingBasket fb=FBDAO.getFundingBasket(userID);
            if(fb!=null){
                return new ResponseEntity<FundingBasket>(fb,HttpStatus.OK);
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
    public ResponseEntity<FundingBasket> addNeed(@PathVariable int userID,@PathVariable int needID,@PathVariable int quantity){
        LOG.info("PUT /fundingBasket/"+userID+"/"+needID+"/"+quantity);
        try{
            Need n=NDAO.getNeed(needID);
            System.out.println(n);
            FundingBasket f=FBDAO.getFundingBasket(userID);
            System.out.println(f);
            if(n!=null && f!=null){
                n.setQuantity(quantity);
                System.out.println(n);
                FBDAO.addNeed(userID, n);
                return new ResponseEntity<FundingBasket>(FBDAO.getFundingBasket(userID),HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userID}/{needID}")
    public ResponseEntity<FundingBasket> deleteNeed(@PathVariable int userID,@PathVariable int needID){
        LOG.info("DELETE /fundingBasket/"+userID+"/"+needID);
        try{
            if(FBDAO.deleteNeed(userID, needID)){
                return new ResponseEntity<FundingBasket>(FBDAO.getFundingBasket(userID),HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Double> checkOut(@RequestBody int userID){
        LOG.info("PUT /fundingBasket/"+userID);
        try{
            Double result=FBDAO.checkout(userID);
            if(result!=-1){
                return new ResponseEntity<Double>(result,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
