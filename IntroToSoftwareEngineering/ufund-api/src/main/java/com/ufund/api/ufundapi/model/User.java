package com.ufund.api.ufundapi.model;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a User entity
 * 
 * @author BMD
 */
public class User {

    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("signin") boolean signin;

    public User(@JsonProperty("id") int id, @JsonProperty("username") String username) {
        this.id = id;
        this.username = username;
        this.signin = false; 
    }

    /**
     * Sets the id of the user - necessary for JSON object to Java object deserialization
     * @param id The id of the user
     */
    public void setId(int id) {this.id = id;}

    /**
     * Sets the username of the user - necessary for JSON object to Java object deserialization
     * @param username The username of the user
     */
    public void setUsername(String username) {this.username = username;}

    /**
     * Retrieves the id of the user
     * @return The id of the user
     */
    public int getId() {return id;}

    /**
     * Retrieves the username of the user
     * @return The username of the user
     */
    public String getUsername() {return username;}

    public boolean getSignin() {return signin;}

    public boolean signIn(){
        signin=true;
        return signin;
    }
    public boolean signOut(){
        signin=false;
        return signin;
    }
}
