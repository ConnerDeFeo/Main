package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.WishList;

@Component
public class WishListFileDAO implements WishListDAO{
    Map<Integer,WishList> wishLists;

    private ObjectMapper objectMapper;
    private String filename;

    public WishListFileDAO(@Value("${wishList.file}") String filename, ObjectMapper objectMapper)throws IOException{
        this.filename=filename;
        this.objectMapper=objectMapper;
        load();
    }

    public WishList getWishList(int user_id)throws IOException{
        synchronized(wishLists){
            return wishLists.get(user_id);
        }
    }

    public boolean createWishList(int user_id)throws IOException{
        synchronized(wishLists){
            if(!wishLists.containsKey(user_id)){
                wishLists.put(user_id, new WishList(user_id));
                return save();
            }
            return false;
        }
    }

    public boolean deleteWishList(int user_id)throws IOException{
        synchronized(wishLists){
            if(wishLists.containsKey(user_id)){
                wishLists.remove(user_id);
                return save();
            }
            return false;
        }
    }

    public boolean addNeed(int user_id,Need need) throws IOException{
        synchronized(wishLists){
            if(wishLists.containsKey(user_id)){
                wishLists.get(user_id).add_need(need);
                return save();
            }
            return false;
        }
    }

    public boolean deleteNeed(int user_id,int need_id) throws IOException{
        synchronized(wishLists){
            if(wishLists.containsKey(user_id)){
                wishLists.get(user_id).deleteNeed(need_id);
                return save();
            }
            return false;
        }
    }

    private boolean load() throws IOException {
        wishLists = new TreeMap<>();

        WishList[] lists = objectMapper.readValue(new File(filename),WishList[].class);

        for(WishList f:lists){
            wishLists.put(f.getUserId(), f);
        }

        return true;

    }

    private boolean save() throws IOException {
        WishList[] fundingBasketArray = getWishListArray();
        objectMapper.writeValue(new File(filename),fundingBasketArray);
        return true;
    }

    private WishList[] getWishListArray() { // if containsText == null, no filter
        ArrayList<WishList> wishListArrayList = new ArrayList<>();

        for(WishList b:wishLists.values()){
            wishListArrayList.add(b);
        }

        WishList[] wishListArray = new WishList[wishListArrayList.size()];
        wishListArrayList.toArray(wishListArray);
        return wishListArray;
    }
}
