package edu.duke.ece651.mp.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Unit implements Serializable{
    private String type;
    private int number;

    public Unit(String type, int number){
        this.type = type;
        this.number = number;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        //s.writeObject(unit);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        //unit = (String) s.readObject();
    }

    public String getUnitType(){
        return this.type;
    }

    public int getUnitNum(){
        return this.number;
    }

    public void updateUnit(int new_unit){
        this.number = new_unit;
    }
}


// unit upgrade path: Alevel, Blevel, Clevel, Dlevel, Elevel, Flevel
