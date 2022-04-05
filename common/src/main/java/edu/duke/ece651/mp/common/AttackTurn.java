package edu.duke.ece651.mp.common;

public class AttackTurn extends Turn {
  public AttackTurn(String fromTerritory, String toTerritory, String unit_type, int num_unit, String player_color) {
    super("Attack", fromTerritory, toTerritory, unit_type, num_unit, player_color);
  }

}
