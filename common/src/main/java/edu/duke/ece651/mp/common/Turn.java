package edu.duke.ece651.mp.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Turn<T> implements Serializable {
    public String type;
    public String dep;
    public String des;
    public int num_unit;
    public String player_color;
    
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
    }

    public String getDep(){
        return this.dep;
    }

    public String getDes(){
        return this.des;
    }

    public int getNumber(){
        return this.num_unit;
    }

    public String getPlayerColor(){
        return this.player_color;
    }

    public Object getType() {
        return this.type;
    }
}
