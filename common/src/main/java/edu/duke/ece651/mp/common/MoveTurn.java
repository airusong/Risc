package edu.duke.ece651.mp.common;

public class MoveTurn<T> extends Turn<T> {
  public MoveTurn(Territory<T> fromTerritory, Territory<T> toTerritory, int num_unit, int player_id) {
    super("Move", fromTerritory, toTerritory, num_unit, player_id);
  }

}
