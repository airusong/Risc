package edu.duke.ece651.mp.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Turn<T> implements Serializable {
  public String type;
  public Territory<T> fromTerritory;
  public Territory<T> toTerritory;
  public int num_unit;
  // public TextPlayer player;
  public int player_id;

  public Turn(String type, Territory<T> fromTerritory, Territory<T> toTerritory, int num_unit, int player_id) {
    this.type = type;
    this.fromTerritory = fromTerritory;
    this.toTerritory = toTerritory;
    this.num_unit = num_unit;
    this.player_id = player_id;
  }

  private void writeObject(ObjectOutputStream s) throws IOException {
    s.defaultWriteObject();
  }

  private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    s.defaultReadObject();
  }

  public Territory<T> getSource() {
    return this.fromTerritory;
  }

  public Territory<T> getDestination() {
    return this.toTerritory;
  }

  public int getNumber() {
    return this.num_unit;
  }

  public int getPlayerID() {
    return this.player_id;
  }
}
