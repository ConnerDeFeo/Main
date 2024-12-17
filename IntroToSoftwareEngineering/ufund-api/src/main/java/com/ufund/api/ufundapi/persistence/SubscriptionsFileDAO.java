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
import com.ufund.api.ufundapi.model.Subscriptions;

@Component
public class SubscriptionsFileDAO implements SubscriptionsDAO{

    Map<Integer,Subscriptions> subscriptionsMap;

    private ObjectMapper objectMapper;
    private String filename;

    public SubscriptionsFileDAO(@Value("${subscriptions.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }
    
    @Override
    public boolean createSubscriptions(int userId) throws IOException {
        try{
            synchronized(subscriptionsMap){
                if (!subscriptionsMap.containsKey(userId)) {
                    subscriptionsMap.put( userId, new Subscriptions(userId));
                    return save();   
                } else {
                    return false;
                }
                
            }
        }catch(IOException e){
            return false;
        }
    }

    @Override
    public boolean deleteSubscriptions(int userId) throws IOException {
        synchronized(subscriptionsMap){
            if(subscriptionsMap.containsKey(userId)){
                subscriptionsMap.remove(userId);
                return save();
            }
            return false;
        }
    }

    @Override
    public boolean addUnprocessedSubscription(int userId, Need need) throws IOException {
        synchronized(subscriptionsMap){
            if(subscriptionsMap.containsKey(userId)){
                subscriptionsMap.get(userId).addUnprocessedSubscription(need);
                return save();
            }      
        }
        return false;
    }

    @Override
    public boolean deleteUnprocessedSubscription(int userId, int subId) throws IOException {
        synchronized(subscriptionsMap){
            if(subscriptionsMap.containsKey(userId)){
                if(subscriptionsMap.get(userId).deleteUnprocessedSubscription(subId)){
                    return save();
                }

            }   
        }
        return false;
    }

    @Override
    public boolean addSubscriptions(int userId, String date) throws IOException {
        synchronized(subscriptionsMap){
            if(subscriptionsMap.containsKey(userId)){
                subscriptionsMap.get(userId).addSubscriptions(date);
                return save();
            }      
        }
        return false;
    }

    @Override
    public boolean deleteSubscription(int userId, int needId) throws IOException {
        synchronized(subscriptionsMap){
            if(subscriptionsMap.containsKey(userId)){
                subscriptionsMap.get(userId).deleteSubscription(needId);
                return save();
            }      
        }
        return false;
    }

    @Override
    public boolean getUnprocessed(int userId, int needId) throws IOException{
        synchronized (subscriptionsMap) {
            return subscriptionsMap.get(userId).getUnprocessed(needId);
        }
    }

    @Override
    public Subscriptions getSubscriptions(int userId) {
        synchronized (subscriptionsMap) {
            return subscriptionsMap.get(userId);
        }
    }

    private Subscriptions[] getSubscriptionsArray() {
        ArrayList<Subscriptions> subscriptionsArrayList = new ArrayList<>();

        for(Subscriptions sub:subscriptionsMap.values()){
            subscriptionsArrayList.add(sub);
        }

        Subscriptions[] subscriptionsArray = new Subscriptions[subscriptionsArrayList.size()];
        subscriptionsArrayList.toArray(subscriptionsArray);
        return subscriptionsArray;
    }

    private boolean load() throws IOException {
        subscriptionsMap = new TreeMap<>();

        Subscriptions[] subs = objectMapper.readValue(new File(filename),Subscriptions[].class);

        for(Subscriptions sub:subs){
            subscriptionsMap.put(sub.getUserId(), sub);
        }

        return true;
    }

    private boolean save() throws IOException {
        Subscriptions[] subscriptionsArray = getSubscriptionsArray();
        objectMapper.writeValue(new File(filename),subscriptionsArray);
        return true;
    }
    
}
