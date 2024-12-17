package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.User;

public interface UserDAO {

    User[] getAllUsers() throws IOException;

    User getUserById(int id) throws IOException;

    User getUserByUsername(String username) throws IOException;

    User addUser(User user) throws IOException;

    public boolean deleteUser(int id) throws IOException;

    User updateUser(User user) throws IOException;

    boolean signIn(int id) throws IOException;

    boolean signOut(int id) throws IOException;

}
