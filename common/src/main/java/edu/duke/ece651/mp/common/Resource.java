package edu.duke.ece651.mp.common;

public class Resource {
    private String type; // resources' type: food * tech
    private int amount; // production amount/per round game the territory produces.

    public Resource(String type, int amount){
        this.type = type;
        this.amount = amount;
    }

    public String getResourceType(){
        return this.type;
    }

    public int getResourceAmount(){
        return this.amount;
    }
}

