package edu.duke.ece651.mp.common;

public class CloakingTurn extends Turn{
    String fromTerritory;
    public CloakingTurn(String fromTerritory, String player_color) {
        super("Cloaking", player_color);
        this.fromTerritory=fromTerritory;
    }

}
