package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.User;

@Component
public class UserFileDAO implements UserDAO {

    Map<Integer,User> users;

    private ObjectMapper objectMapper;
    private String filename;
    private static int nextId;

    public UserFileDAO (@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private User[] getUsersArray() { // if containsText == null, no filter
        ArrayList<User> userArrayList = new ArrayList<>();

        for (User user : users.values()) {
            userArrayList.add(user);
        }

        User[] userArray = new User[userArrayList.size()];
        userArrayList.toArray(userArray);
        return userArray;
    }

    private boolean load() throws IOException {
        users = new TreeMap<>();
        nextId = 0;

        User[] userArray = objectMapper.readValue(new File(filename),User[].class);

        for (User user : userArray) {
            users.put(user.getId(),user);
            if (user.getId() > nextId)
                nextId = user.getId();
        }
        
        nextId++;
        return true;
    }

    private boolean save() throws IOException {
        User[] userArray = getUsersArray();
        objectMapper.writeValue(new File(filename),userArray);
        return true;
    }

    private synchronized static int nextId() {
        int id = nextId;
        nextId++;
        return id;
    }
    @Override
    public User[] getAllUsers() {
        synchronized (users) {
            return getUsersArray();
        }
    }
    @Override
    public User getUserById(int id) {
        return users.get(id);
    }
    @Override
    public User addUser(User user) throws IOException{
        synchronized (users) {
            User newUser = new User(nextId(), user.getUsername());
            for (User currentUser : users.values()) {
                if(currentUser.getUsername().equals(user.getUsername())){
                    return null;
                }
                else{
                    continue;
                }
            }
            users.put(newUser.getId(),newUser);
            save();
            return newUser;
        }
        
    }
    @Override
    public boolean deleteUser(int id) throws IOException{
        synchronized(users) {
            if (users.containsKey(id)) {
                users.remove(id);
                return save();
            }
            else
                return false;
        }
    }
    @Override
    public User updateUser(User user) throws IOException{
        synchronized(users) {
            if (users.containsKey(user.getId()) == false)
                return null; 
            users.put(user.getId(),user);
            save(); 
            return user;
        }
    }  

    @Override
    public boolean signIn(int id)throws IOException{
        synchronized(users){
            User u=users.get(id);
            if(u!=null){
                u.signIn();
                return save();
            }
        }
        return false;
    }

    @Override
    public boolean signOut(int id)throws IOException{
        synchronized(users){
            User u=users.get(id);
            if(u!=null){
                u.signOut();
                return save();
            }
        }
        return false;
    }

    @Override
    public User getUserByUsername(String username){
        for(User u:users.values()){
            if(u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }
}