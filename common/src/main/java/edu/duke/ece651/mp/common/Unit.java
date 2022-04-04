package edu.duke.ece651.mp.common;

public class Unit {
    private String type;
    private int number;

    public Unit(String type, int number){
        this.type = type;
        this.number = number;
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
