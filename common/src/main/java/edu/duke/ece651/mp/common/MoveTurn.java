package edu.duke.ece651.mp.common;

import java.io.Serializable;

public class MoveTurn<T> extends Turn<T>{
    public String type;
    public String dep;
    public String des;
    public int num_unit;
    public String player_color;

    public MoveTurn(String type, String dep, String des, int num_unit, String player_color){
        this.type = type;
        this.dep = dep;
        this.des = des;
        this.num_unit = num_unit;
        this.player_color = player_color;
    }

    @Override
    public String getDep(){
        return this.dep;
    }
    @Override
    public String getDes(){
        return this.des;
    }
    @Override
    public int getNumber(){
        return this.num_unit;
    }
    @Override
    public String getPlayerColor(){
        return this.player_color;
    }
    @Override
    public Object getType() {
        return this.type;
    }
}
