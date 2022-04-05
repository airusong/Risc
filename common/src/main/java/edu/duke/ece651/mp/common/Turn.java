package edu.duke.ece651.mp.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Turn implements Serializable {
  public String type;
  public int num_unit;
  public String player_color;

  public Turn(String type, int num_unit, String player_color) {
    this.type = type;
    this.num_unit = num_unit;
    this.player_color = player_color;
  }

  private void writeObject(ObjectOutputStream s) throws IOException {
    s.defaultWriteObject();
  }

  private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    s.defaultReadObject();
  }

  public int getNumber() {
    return this.num_unit;
  }

  public String getPlayerColor() {
    return this.player_color;
  }

  public String getTurnType(){
    return this.type;
  }

  public void printTurn(){
    System.out.println("Turn: ");
    System.out.println(this.type);
    System.out.println(this.num_unit);
    System.out.println(this.player_color);
  }

}
