package com.ufund.api.ufundapi.persistence;

import java.util.Map;
import java.util.TreeMap;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Admin;

@Component
public class AdminFileDAO implements AdminDAO{
    Map<Integer,Admin> admins;

    private ObjectMapper objectMapper;

    private static int nextId;
    private String filename;

    public AdminFileDAO(@Value("${admins.file}") String filename,ObjectMapper objectMapper) throws IOException{
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private Admin[] getAdminArray() {
        ArrayList<Admin> adminArrayList = new ArrayList<>();

        for (Admin admin : admins.values()) {
            adminArrayList.add(admin);
        }

        Admin[] adminArray = new Admin[adminArrayList.size()];
        adminArrayList.toArray(adminArray);
        return adminArray;
    }

    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    private boolean save() throws IOException {
        Admin[] adminArray = getAdminArray();
        objectMapper.writeValue(new File(filename),adminArray);
        return true;
    }

    private boolean load() throws IOException {
        admins = new TreeMap<>();
        nextId = 0;

        Admin[] adminArray = objectMapper.readValue(new File(filename),Admin[].class);

        // Add each admin to the tree map and keep track of the greatest id
        for (Admin admin : adminArray) {
            admins.put(admin.getId(),admin);
            if (admin.getId() > nextId)
                nextId = admin.getId();
        }
        // Make the next id one greater than the maximum from the file
        nextId++;
        return true;
    }

    public Admin[] getAdmins(){
        return getAdminArray();
    }

    public Admin getAdmin(int id)throws IOException{
        return admins.get(id);
    }


    public Admin createAdmin(Admin admin) throws IOException{
        synchronized(admins) {
            Admin newAdmin = new Admin(nextId(),admin.getUsername()) ;

            for (Admin currentAdmin : admins.values()) {
                if(currentAdmin.getUsername().equals(admin.getUsername())){
                    return null;
                }
            }
            admins.put(newAdmin.getId(),newAdmin);
            save();
            return newAdmin;
        }
    }

    @Override
    public Admin updateAdmin(Admin admin) throws IOException{
        synchronized(admins) {
            if (admins.containsKey(admin.getId()) == false)
                return null; 
            admins.put(admin.getId(),admin);
            save(); 
            return admin;
        }
    }

    @Override
    public boolean deleteAdmin(int id) throws IOException{
        synchronized(admins) {
            if (admins.containsKey(id)) {
                admins.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
