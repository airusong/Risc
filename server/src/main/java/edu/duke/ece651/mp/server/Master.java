package edu.duke.ece651.mp.server;

import java.io.IOException;
import java.net.Socket;

import edu.duke.ece651.mp.common.Map;
import edu.duke.ece651.mp.common.V1Map;

public class Master {
  final MasterServer theMasterServer;
  Map<Character> theMap;

  /**
   * Constructor
   */
  public Master(int port, int num_players) {
    this.theMasterServer = new MasterServer(port, num_players);
    this.theMap = new V1Map<Character>();
  }

  /**
   *
   */
  public void playGame() {
    try {
      theMasterServer.acceptPlayers();
      sendMapToAll();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Method to send current version map to ALL the players
   */
  public void sendMapToAll() {
    theMasterServer.sendToAll((Object)theMap);
  }
  
}
