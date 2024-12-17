package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WishList{
    @JsonProperty("userId") int userId;
    @JsonProperty("wishList") List<Need> wishList;

    public WishList(@JsonProperty("userId") int userId){
        this.userId=userId;
        this.wishList=new ArrayList<Need>();
    }

    public boolean deleteNeed(int id){
        for(Need n:wishList){
            if(n.getId()==id){
                wishList.remove(n);
                return true;
            }
            continue;
        }
        return false;
    }

    public void add_need(Need need){
        if(wishList.contains(need)){
            for(Need n:wishList){
                if(n.getId()==need.getId()){
                    n.setQuantity(n.getQuantity()+need.getQuantity());
                }
                continue;
            }
        }else{
            wishList.add(need);
        }
    }

    public int getUserId(){return userId;}

    public List<Need> getWishList(){return wishList;}

    public int size(){
        int c=0;
        for(Need n:wishList){
            c+=n.getQuantity();
        }
        return c;
    }
}
