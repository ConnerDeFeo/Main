package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Need;

public interface NeedDAO {
    Need[] getNeeds() throws IOException;
    
    /**
     * Finds all {@linkplain Need needs} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Need needs} whose names contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Need[] findNeeds(String containsText) throws IOException;

    Need getNeed(int id) throws IOException;

    Need createNeed(Need need) throws IOException;

    Need updateNeed(Need need) throws IOException;

    boolean deleteNeed(int id) throws IOException;
}
