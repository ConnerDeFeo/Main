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

import com.ufund.api.ufundapi.model.WishList;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.NeedDAO;
import com.ufund.api.ufundapi.persistence.WishListDAO;

@RestController
@RequestMapping("wishList")
public class WishListController {
    private static final Logger LOG = Logger.getLogger(WishListController.class.getName());
    private WishListDAO WLDAO;
    private NeedDAO NDAO;

    public WishListController(WishListDAO WLDAO, NeedDAO NDAO){
        this.WLDAO=WLDAO;
        this.NDAO=NDAO;
    }

    @GetMapping("/{userID}")
    public ResponseEntity<WishList> getWishList(@PathVariable int userID){
        LOG.info("GET /WishList");
        try{
            WishList fb=WLDAO.getWishList(userID);
            if(fb!=null){
                return new ResponseEntity<WishList>(fb,HttpStatus.OK);
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
    public ResponseEntity<WishList> addNeed(@PathVariable int userID,@PathVariable int needID,@PathVariable int quantity){
        LOG.info("PUT /WishList/"+userID+"/"+needID+"/"+quantity);
        try{
            Need n=NDAO.getNeed(needID);
            System.out.println(n);
            WishList f=WLDAO.getWishList(userID);
            System.out.println(f);
            if(n!=null && f!=null){
                n.setQuantity(quantity);
                System.out.println(n);
                WLDAO.addNeed(userID, n);
                return new ResponseEntity<WishList>(WLDAO.getWishList(userID),HttpStatus.OK);
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
    public ResponseEntity<WishList> deleteNeed(@PathVariable int userID,@PathVariable int needID){
        LOG.info("DELETE /WishList/"+userID+"/"+needID);
        try{
            if(WLDAO.deleteNeed(userID, needID)){
                return new ResponseEntity<WishList>(WLDAO.getWishList(userID),HttpStatus.OK);
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
