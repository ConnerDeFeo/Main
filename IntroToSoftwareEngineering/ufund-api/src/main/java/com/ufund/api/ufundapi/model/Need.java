package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Need entity
 * 
 * @author BMD
 */
public class Need {

    // Package private for tests
    public static final String STRING_FORMAT = "Need [name=%s]"; // FINISH LATER

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("cost") private double cost;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("type") private String type;

    /**
     * Create a need with the given name, cost, quantity, and type
     * @param id The id of the need
     * @param name The name of the need
     * @param cost The cost of the need
     * @param quantity The quantity of the need
     * @param type The type of the need
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Need(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("cost") double cost, @JsonProperty("quantity") int quantity, @JsonProperty("type") String type) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.type = type;
    }
    
    /**
     * Sets the name of the need - necessary for JSON object to Java object deserialization
     * @param name The name of the need
     */
    public void setName(String name) {this.name = name;}

    /**
     * Sets the cost of the need - necessary for JSON object to Java object deserialization
     * @param cost The cost of the need
     */
    public void setCost(double cost) {this.cost = cost;}

    /**
     * Sets the quantity of the need - necessary for JSON object to Java object deserialization
     * @param quantity The quantity of the need
     */
    public void setQuantity(int quantity) {this.quantity = quantity;}

    /**
     * Sets the type of the need - necessary for JSON object to Java object deserialization
     * @param type The type of the need
     */
    public void setType(String type) {this.type = type;}

    /**
     * Retrieves the name of the need
     * @return The name of the need
     */
    public String getName() {return name;}

    /**
     * Retrieves the id of the need
     * @return The id of the need
     */
    public int getId() {return id;}

    /**
     * Retrieves the cost of the need
     * @return The cost of the need
     */
    public double getCost() {return cost;}

    /**
     * Retrieves the quantity of the need
     * @return The quantity of the need
     */
    public int getQuantity() {return quantity;}

    /**
     * Retrieves the type of the need
     * @return The type of the need
     */
    public String getType() {return type;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,name,cost,quantity,type);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Need){
            Need n=(Need)o;
            if(n.getId()==this.id){
                return true;
            }
            return false;
        }
        return false;
    }
}