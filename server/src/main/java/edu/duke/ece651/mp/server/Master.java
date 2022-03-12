package edu.duke.ece651.mp.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.duke.ece651.mp.common.Map;
import edu.duke.ece651.mp.common.V1Map;

public class Master {
  final MasterServer theMasterServer;
  ArrayList<String> players_identity;
  Map<Character> theMap;

  /**
   * Constructor
   */
  public Master(int port, int num_players) {
    this.theMasterServer = new MasterServer(port, num_players);
    this.players_identity = new ArrayList<String>(Arrays.asList("Green", "Blue"));
    this.theMap = new V1Map<Character>(this.players_identity);
  }

  /**
   * @throws IOException
   *
   */
  public void acceptPlayers() throws IOException{
    this.theMasterServer.acceptPlayers();
  }

  /*
  public void playGame() {
    try {
      theMasterServer.acceptPlayers();
      sendMapToAll();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  */
  
  /**
   * Method to send current version map to ALL the players
   */
  public void sendMapToAll() {
    theMasterServer.sendToAll((Object)theMap);
  }
  
  public void close() throws IOException{
    this.theMasterServer.close();
  }

  public void sendPlayerIdentityToAll(){
    theMasterServer.sendPlayerIdentityToAll(players_identity);
  }
}
