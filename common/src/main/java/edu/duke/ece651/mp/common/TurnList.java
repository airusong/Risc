package edu.duke.ece651.mp.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class TurnList<T> implements Serializable{
    private String player_info;
    public ArrayList<Turn<T>> order_list;
    
    public TurnList(String player_info){
        this.player_info = player_info;
        this.order_list = new ArrayList<Turn<T>>();
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
    }
    
}
