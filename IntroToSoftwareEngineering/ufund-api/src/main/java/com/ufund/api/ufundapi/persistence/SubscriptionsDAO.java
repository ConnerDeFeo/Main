package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.Subscriptions;

public interface SubscriptionsDAO {
    public boolean createSubscriptions(int userId) throws IOException;

    public boolean deleteSubscriptions(int userId) throws IOException;

    public boolean addSubscriptions(int userId, String date) throws IOException;

    public boolean addUnprocessedSubscription(int userId, Need need) throws IOException;

    public boolean deleteUnprocessedSubscription(int userId, int subId) throws IOException;

    public boolean deleteSubscription(int userId, int needId) throws IOException;

    public Subscriptions getSubscriptions(int userId) throws IOException;

    public boolean getUnprocessed(int userId, int needId) throws IOException;

}
