package com.ufund.api.ufundapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.FundingBasketDAO;
import com.ufund.api.ufundapi.persistence.UserDAO;
import com.ufund.api.ufundapi.persistence.WishListDAO;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ufund.api.ufundapi.persistence.SubscriptionsDAO;

@RestController
@RequestMapping("users")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private UserDAO userDAO;
    private FundingBasketDAO FBDAO;
    private WishListDAO WLDAO;
    private SubscriptionsDAO subDAO;

    public UserController(UserDAO userDAO, FundingBasketDAO FBDAO,WishListDAO WLDAO, SubscriptionsDAO subDAO){
        this.userDAO = userDAO;
        this.FBDAO=FBDAO;
        this.WLDAO=WLDAO;
        this.subDAO = subDAO;
    }
    
    @GetMapping("")
    public ResponseEntity<User[]> getUsers(){
        LOG.info("GET /needs");
        try {
            User[] users = userDAO.getAllUsers();
            return new ResponseEntity<User[]>(users,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id){
        LOG.info("GET /users " + id);
        try {
            User user = userDAO.getUserById(id);
            if(user != null){
                return new ResponseEntity<User>(user, HttpStatus.OK);
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
    public ResponseEntity<User> createUser(@RequestBody User user) {
        LOG.info("POST /users " + user);
        try {
            User new_user = userDAO.addUser(user);
            if (new_user != null) {
                FBDAO.createFundingBasket(new_user.getId());
                subDAO.createSubscriptions(new_user.getId());
                WLDAO.createWishList(new_user.getId());
                return new ResponseEntity<User>(new_user,HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT); 
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }   
    }
    
    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        LOG.info("PUT /users " + user);
        try {
            User updated_user = userDAO.updateUser(user);
            if (updated_user != null)
                return new ResponseEntity<User>(updated_user,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        LOG.info("DELETE /users " + id);
        try {
            FBDAO.deleateFundingBasket(id);
            WLDAO.createWishList(id);
            if (userDAO.deleteUser(id))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/signIn")
    public ResponseEntity<User> signIn(@RequestBody String username){
        LOG.info("PUT /users/signIn " + username);
        try{
            User u=userDAO.getUserByUsername(username);
            if(u!=null){
                if(userDAO.signIn(u.getId())){
                    return new ResponseEntity<User>(u, HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/signOut")
    public ResponseEntity<User> signOut(@RequestBody int id){
        LOG.info("PUT /users/signOut " + id);
        try{
            User u=userDAO.getUserById(id);
            if(u!=null){
                if(userDAO.signOut(id)){
                    return new ResponseEntity<User>(u,HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<User>(HttpStatus.CONFLICT);
                }
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("signedIn/{id}")
    public ResponseEntity<Boolean> signedIn(@PathVariable int id){
        LOG.info("GET /users/signedIn " + id);
        try{
            User u=userDAO.getUserById(id);
            if(u!=null){
                if(u.getSignin()){
                    return new ResponseEntity<Boolean>(true,HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<Boolean>(false,HttpStatus.OK);
                }
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
