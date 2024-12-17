package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FundingBasket {
    
    @JsonProperty("userId") int user_id;
    @JsonProperty("basket") List<Need> basket;
    @JsonProperty("totalCost") double total_cost;

    public FundingBasket(@JsonProperty("userId") int user_id){
        this.user_id=user_id;
        total_cost=0;
        basket=new ArrayList<Need>();
    }

    public double checkout(){
        double t=total_cost;
        basket=new ArrayList<Need>();
        total_cost=0;
        return t;
    }

    public boolean deleteNeed(int id){
        for(Need n:basket){
            if(n.getId()==id){
                total_cost-=n.getCost()*n.getQuantity();
                basket.remove(n);
                return true;
            }
            continue;
        }
        return false;
    }

    public void add_need(Need need){
        if(basket.contains(need)){
            for(Need n:basket){
                if(n.getId()==need.getId()){
                    n.setQuantity(n.getQuantity()+need.getQuantity());
                }
                continue;
            }
        }else{
            basket.add(need);
        }
        total_cost+=need.getCost()*need.getQuantity();
    }

    public List<Need> getBasket(){return basket;}

    public int getUserId(){return user_id;}

    public double getTotalCost(){return total_cost;}

    public int size(){
        int s=0;
        for(Need n:basket){
            s+=n.getQuantity();
        }
        return s;
    }
}
