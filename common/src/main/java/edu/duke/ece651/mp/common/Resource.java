package edu.duke.ece651.mp.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Resource implements Serializable {
    private String type; // resources' type: food * tech
    private int amount; // production amount/per round game the territory produces.

    public Resource(String type, int amount){
        this.type = type;
        this.amount = amount;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
    }

    public String getResourceType(){
        return this.type;
    }

    public int getResourceAmount(){
        return this.amount;
    }
}

