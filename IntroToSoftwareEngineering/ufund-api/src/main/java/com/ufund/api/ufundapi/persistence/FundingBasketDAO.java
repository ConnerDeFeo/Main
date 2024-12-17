package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.FundingBasket;
import com.ufund.api.ufundapi.model.Need;

public interface FundingBasketDAO {
    
    public FundingBasket getFundingBasket(int user_id)throws IOException;

    public boolean createFundingBasket(int user_id)throws IOException;

    public boolean deleateFundingBasket(int user_id)throws IOException;

    public boolean addNeed(int user_id,Need need) throws IOException;

    public boolean deleteNeed(int user_id,int need_id) throws IOException;

    public double checkout(int user_id) throws IOException;
}
