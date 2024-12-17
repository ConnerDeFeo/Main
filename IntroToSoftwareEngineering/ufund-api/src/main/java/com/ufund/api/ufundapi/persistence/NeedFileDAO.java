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


@Component
public class NeedFileDAO implements NeedDAO{
    Map<Integer,Need> needs;

    private ObjectMapper objectMapper;

    private static int nextId;
    private String filename;

    /**
     * Creates a Need File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public NeedFileDAO(@Value("${needs.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the needs from the file
    }

    private Need[] getNeedsArray() {
        return getNeedsArray(null);
    }

    private Need[] getNeedsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Need> needArrayList = new ArrayList<>();

        for (Need need : needs.values()) {
            if (containsText == null || need.getName().contains(containsText)) {
                needArrayList.add(need);
            }
        }

        Need[] needArray = new Need[needArrayList.size()];
        needArrayList.toArray(needArray);
        return needArray;
    }

    private boolean save() throws IOException {
        Need[] needArray = getNeedsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),needArray);
        return true;
    }

    /**
     * Generates the next id for a new {@linkplain Need need}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Loads {@linkplain Need needs} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        needs = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of needs
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Need[] needArray = objectMapper.readValue(new File(filename),Need[].class);

        // Add each need to the tree map and keep track of the greatest id
        for (Need need : needArray) {
            needs.put(need.getId(),need);
            if (need.getId() > nextId)
                nextId = need.getId();
        }
        // Make the next id one greater than the maximum from the file
        nextId++;
        return true;
    }
	
	@Override
    public Need[] getNeeds(){
        synchronized(needs){
            return getNeedsArray();
        }
    }
    

    /**
    ** {@inheritDoc}
    */
    @Override
    public Need[] findNeeds(String containsText) throws IOException {
        synchronized(needs) {
            return getNeedsArray(containsText);
        }
    }

    public Need getNeed(int id) throws IOException{
        return needs.get(id);
    }

    public Need createNeed(Need need) throws IOException{
        synchronized(needs) {
            Need newNeed = new Need(nextId(), need.getName(), need.getCost(), need.getQuantity(), need.getType()) ;
            // Checks for needs with duplicate names
            for (Need currentNeed : needs.values()) {
                if(currentNeed.getName().equals(need.getName())){
                    return null;
                }
            }
            needs.put(newNeed.getId(),newNeed);
            save();
            return newNeed;
        }
    }

    /*
     * updates need from map with a new one with the same id of an already existing one
     * @param need is the need object that will be replacing the need with the same id
     * returns the need if the update was succsesfull, else return null if no need with
     * the same id was found
     */
    public Need updateNeed(Need need) throws IOException{
        synchronized(needs) {
            if (needs.containsKey(need.getId()) == false)
                return null; 
            needs.put(need.getId(),need);
            save(); 
            return need;
        }
    }

    /*
     * deletes need from needs map with the given id
     * @param id is the id of the need that will be deleted
     * return true if need was deleated or false if it was not found
     */
    @Override
    public boolean deleteNeed(int id) throws IOException{
        synchronized(needs) {
            if (needs.containsKey(id)) {
                needs.remove(id);
                return save();
            }
            else
                return false;
        }
    }

}
