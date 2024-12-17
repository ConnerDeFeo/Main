package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.WishList;

public interface WishListDAO {
    public WishList getWishList(int user_id)throws IOException;

    public boolean createWishList(int user_id)throws IOException;

    public boolean deleteWishList(int user_id)throws IOException;

    public boolean addNeed(int user_id,Need need) throws IOException;

    public boolean deleteNeed(int user_id,int need_id) throws IOException;
}
