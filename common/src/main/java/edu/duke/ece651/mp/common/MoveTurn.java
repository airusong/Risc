package edu.duke.ece651.mp.common;

public class MoveTurn extends Turn {

  public String fromTerritory;
  public String toTerritory;
  public String unit_type;

  public MoveTurn(String fromTerritory, String toTerritory, String unit_type, int num_unit, String player_color) {
    super("Move", num_unit, player_color);
    this.fromTerritory = fromTerritory;
    this.toTerritory = toTerritory;
    this.unit_type = unit_type;
  }

  public String getSource() {
    return this.fromTerritory;
  }

  public String getDestination() {
    return this.toTerritory;
  }

  public String getUnitType(){
    return this.unit_type;
  }

  @Override
  public void printTurn(){
    System.out.println("Turn: ");
    System.out.println(this.type);
    System.out.println(this.fromTerritory);
    System.out.println(this.toTerritory);
    System.out.println(this.unit_type);
    System.out.println(this.num_unit);
    System.out.println(this.player_color);
  }
}
