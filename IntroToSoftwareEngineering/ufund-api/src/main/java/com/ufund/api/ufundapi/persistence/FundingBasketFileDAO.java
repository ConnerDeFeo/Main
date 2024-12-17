package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.FundingBasket;
import com.ufund.api.ufundapi.model.Need;

@Component
public class FundingBasketFileDAO implements FundingBasketDAO {
    
    Map<Integer,FundingBasket> baskets;

    private ObjectMapper objectMapper;
    private String filename;

    public FundingBasketFileDAO(@Value("${funding_basket.file}") String filename, ObjectMapper objectMapper) throws IOException{
        this.objectMapper=objectMapper;
        this.filename=filename;
        load();
    }

    @Override 
    public FundingBasket getFundingBasket(int user_id)throws IOException{
        synchronized(baskets){
            return baskets.get(user_id);
        }
    }

    @Override
    public boolean createFundingBasket(int user_id)throws IOException{
        synchronized(baskets){
            if(!baskets.containsKey(user_id)){
                baskets.put(user_id, new FundingBasket(user_id));
                return save();
            }
            return false;
        }
    }

    @Override
    public boolean deleateFundingBasket(int user_id)throws IOException{
        synchronized(baskets){
            if(baskets.containsKey(user_id)){
                baskets.remove(user_id);
                return save();
            }
            return false;
        }
    }

    @Override
    public boolean addNeed(int user_id, Need need) throws IOException{
        synchronized(baskets){
            if(baskets.containsKey(user_id)){
                baskets.get(user_id).add_need(need);
                return save();
            }      
        }
        return false;
    }
    
    @Override
    public boolean deleteNeed(int user_id, int need_id) throws IOException{
        synchronized(baskets){
            if(baskets.containsKey(user_id)){
                baskets.get(user_id).deleteNeed(need_id);
                return save();
            }
        }
        return false;
    }
    
    @Override
    public double checkout(int user_id) throws IOException{
        synchronized(baskets){
            if(baskets.containsKey(user_id)){
                double t=baskets.get(user_id).checkout();
                save();
                return t;
            }
        }
        return -1;
    }

    private FundingBasket[] getFundingBasketArray() { // if containsText == null, no filter
        ArrayList<FundingBasket> fundingBasketArrayList = new ArrayList<>();

        for(FundingBasket b:baskets.values()){
            fundingBasketArrayList.add(b);
        }

        FundingBasket[] fundingBasketArray = new FundingBasket[fundingBasketArrayList.size()];
        fundingBasketArrayList.toArray(fundingBasketArray);
        return fundingBasketArray;
    }

    private boolean load() throws IOException {
        baskets = new TreeMap<>();

        FundingBasket[] fundingBaskets = objectMapper.readValue(new File(filename),FundingBasket[].class);

        for(FundingBasket f:fundingBaskets){
            baskets.put(f.getUserId(), f);
        }

        return true;

    }

    private boolean save() throws IOException {
        FundingBasket[] fundingBasketArray = getFundingBasketArray();
        objectMapper.writeValue(new File(filename),fundingBasketArray);
        return true;
    }
}
