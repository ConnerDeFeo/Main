package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an admin
 * 
 * @author BMD
 */
public class Admin{
    private static final String STRING_FORMAT="Admin [id=%s, username=%s]";

    @JsonProperty("username") private String username;
    @JsonProperty("id") private int id;

    /**
     * Create a admin with the id and username
     * @param id The id of the admin
     * @param username The name of the admin
     */
    public Admin(@JsonProperty("id") int id,@JsonProperty("username") String username){
        this.username=username;
        this.id=id;
    }

    /**
     * Sets the username of the admin
     * @param name The name of the admin
     */
    public void setUsername(String username) {this.username = username;}

    /**
     * Retrieves the username of the admin
     * @return The username of the admin
     */
    public String getUsername() {return username;}

    /**
     * Retrieves the id of the admin
     * @return The id of the admin
     */
    public int getId() {return id;}

    /**
     * Checks if user is an admin
     * @return true
     */
    public boolean isAdmin(){return true;}

    public String toString(){
        return String.format(STRING_FORMAT, id,username);
    }
}
