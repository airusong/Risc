package edu.duke.ece651.mp.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Turn<T> implements Serializable {
    public String type;
    public Territory<T> dep;
    public Territory<T> des;
    public int num_unit;
    //public TextPlayer player;
    //public int player_id;
    public String player_color;
    
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
    }

    public Territory<T> getDep(){
        return this.dep;
    }

    public Territory<T> getDes(){
        return this.des;
    }

    public int getNumber(){
        return this.num_unit;
    }

    /*
    public int getPlayerID(){
        return this.player_id;
    }
    */
    public String getPlayerColor(){
        return this.player_color;
    }
}
