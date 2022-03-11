package edu.duke.ece651.mp.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import edu.duke.ece651.mp.common.Territory;

public abstract class Turn<T> implements Serializable {
    public String type;
    public Territory<T> dep;
    public Territory<T> des;
    public int num_unit;
    //public TextPlayer player;
    public int player_id;

    /*
    public Turn(String type, Territory<T> dep, Territory<T> des, int num_unit, int player_id) {
        this.type = type;
        this.dep = dep;
        this.des = des;
        this.num_unit = num_unit;
        this.player_id = player_id;
    }
    */

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
    }
}
