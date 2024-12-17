package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Admin;

public interface AdminDAO {
    Admin getAdmin(int id) throws IOException;
    
    Admin[] getAdmins() throws IOException;

    Admin createAdmin(Admin admin) throws IOException;

    Admin updateAdmin(Admin admin) throws IOException;

    boolean deleteAdmin(int id) throws IOException;
}
