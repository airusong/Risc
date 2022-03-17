package edu.duke.ece651.mp.common;

import java.io.Serializable;

public class MoveTurn<T> extends Turn<T>{
    public String type;
    public Territory<T> dep;
    public Territory<T> des;
    public int num_unit;
    public String player_color;

    public MoveTurn(String type, Territory<T> dep, Territory<T> des, int num_unit, String player_color){
        this.type = type;
        this.dep = dep;
        this.des = des;
        this.num_unit = num_unit;
        this.player_color = player_color;
    }

    /* Return the departure of move action. */
    public Territory<T> getDep(){
        return this.dep;
    }

    /* Return the destination of move action. */
    public Territory<T> getDes(){
        return this.des;
    }

    /* Return the owner of the move action. */
    /*
    public int getPlayerID(){
        return this.player_id;
    }*/

    public String getPlayerColor(){
        return this.player_color;
    }
}
