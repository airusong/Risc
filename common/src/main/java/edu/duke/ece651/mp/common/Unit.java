package edu.duke.ece651.mp.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Unit implements Serializable{
    private String type;
    private int bonus;
    private int number;

    public Unit(String type, int number){
        this.type = type;
        switch (this.type) {
            case "Alevel":
                this.bonus = 0;
                break;
            case "Blevel":
                this.bonus = 1;
            case "Clevel":
                this.bonus = 3;
            case "Dlevel":
                this.bonus = 5;
            case "Elevel":
                this.bonus = 8;
            case "Flevel":
                this.bonus = 11;
            case "Glevel":
                this.bonus = 15;
        }
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

    public int getBonus(){return this.bonus;}

    public int getUnitNum(){
        return this.number;
    }

    public void updateUnit(int new_unit){
        this.number = new_unit;
    }
}


// unit upgrade path: Alevel, Blevel, Clevel, Dlevel, Elevel, Flevel
