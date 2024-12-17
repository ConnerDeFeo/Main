package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Subscriptions {
    @JsonProperty("userId") int userId;
    @JsonProperty("unprocessedSubscriptions") List<Need> unprocessedSubscriptions;
    @JsonProperty("subscriptions") List<NeedDatePair> subscriptions;

    public Subscriptions(@JsonProperty("userId") int userId) {
        this.userId = userId;
        this.unprocessedSubscriptions = new ArrayList<>();
        this.subscriptions = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public List<NeedDatePair> getSubscriptions() {
        return subscriptions;
    }

    public boolean getUnprocessed(int needId){
        for (Need need : unprocessedSubscriptions) {
            if(needId == need.getId()){
                return true;
            }
            continue;
        }
        return false;
    }

    public List<Need> getUnprocessedSubscriptions() {
        return unprocessedSubscriptions;
    }

    public void addUnprocessedSubscription(Need need) {
        unprocessedSubscriptions.add(need);
    }

    public boolean deleteUnprocessedSubscription(int subId){
        for (Need need : unprocessedSubscriptions) {
            if(subId == need.getId()){
                unprocessedSubscriptions.remove(need);
                return true;
            }
            continue;
        }
        return false;
    }

    public void addSubscriptions(String date) {
        for (Need need : unprocessedSubscriptions) {
            subscriptions.add(new NeedDatePair(need, date));
        }
        unprocessedSubscriptions.clear();
    }

    public boolean deleteSubscription(int needId) {
        for (NeedDatePair n: subscriptions) {
            if(needId == n.getNeed().getId()) {
                subscriptions.remove(n);
                return true;
            }
            continue;
        }
        return false;
    }

}
