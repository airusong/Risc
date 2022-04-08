package edu.duke.ece651.mp.common;

import java.util.HashMap;

public class AttackTurn extends Turn {

  private String fromTerritory;
  private String toTerritory;

  public AttackTurn(String fromTerritory, String toTerritory, int num_unit, String player_color) {
    super("Attack", num_unit, player_color);
    this.fromTerritory = fromTerritory;
    this.toTerritory = toTerritory;
  }

  public AttackTurn(String fromTerritory, String toTerritory, HashMap<String, Integer> units, String player_color) {
    super("Attack", units, player_color);
    this.fromTerritory = fromTerritory;
    this.toTerritory = toTerritory;
  }

  // used by grouping attackTurn to the same Territory
  public AttackTurn(String toTerritory, String player_color) {
    super("Attack", player_color);
    this.fromTerritory = null;
    this.toTerritory = toTerritory;

  }

  public String getSource() {
    return this.fromTerritory;
  }

  public String getDestination() {
    return this.toTerritory;
  }

  @Override
  public void printTurn() {
    System.out.println("Turn: ");
    System.out.println(this.type);
    System.out.println(this.fromTerritory);
    System.out.println(this.toTerritory);
    System.out.println(this.player_color);
    printUnits();
  }

  public AttackTurn addTurn(AttackTurn attackTurn) {
    AttackTurn newAttackTurn = new AttackTurn(attackTurn.getSource(), attackTurn.getDestination(), null,
        attackTurn.getPlayerColor());
    for (HashMap.Entry<String, Integer> set : num_units.entrySet()) {
      for (HashMap.Entry<String, Integer> set2 : attackTurn.getUnitList().entrySet()) {
        if (set.getKey().equals(set2.getKey())) {
          int unit_num = set.getValue() + set2.getValue();
          num_units.put(set.getKey(), unit_num);
          break;
        }
      }
    }
    return newAttackTurn;
  }

}
