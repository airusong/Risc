package edu.duke.ece651.mp.client;

import java.io.Serializable;

import edu.duke.ece651.mp.common.Territory;

public class MoveTurn<T> extends Turn<T>{
    public String type;
    public Territory<T> dep;
    public Territory<T> des;
    public int num_unit;
    //public TextPlayer player;
    public int player_id;

    public MoveTurn(String type, Territory<T> dep, Territory<T> des, int num_unit, int player_id){
        this.type = type;
        this.dep = dep;
        this.des = des;
        this.num_unit = num_unit;
        this.player_id = player_id;
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
    public int getPlayerID(){
        return this.player_id;
    }
}