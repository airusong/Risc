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
  Map<Character> theMap;
  ArrayList<String> players_identity;

  /**
   * Constructor
   * 
   * @throws IOException
   */
  public Master(int port, int num_players) throws IOException {
    this.theMasterServer = new MasterServer(port, num_players);
    this.theMap = new V1Map<Character>();
    this.players_identity = new ArrayList<String>(Arrays.asList("Yellow", "Green"));
  }

  /**
   * @throws IOException
   *
   */
  public void acceptPlayers() throws IOException {
    this.theMasterServer.acceptPlayers();
  }

  /**
   * Method to send current version map to ALL the players
   * 
   * @throws IOException
   */
  public void sendMapToAll() throws IOException {
    theMasterServer.sendToAll((Object) theMap);
  }

  public void close() throws IOException {
    this.theMasterServer.close();
  }

  /**
   * Method to send Player Color to ALL the players
   * 
   * @throws IOException
   */
  public void sendPlayerIdentityToAll() throws IOException {
    theMasterServer.sendPlayerIdentityToAll(players_identity);
  }

  /**
   * Method to receive orders from ALL players
   * 
   * @throws IOException
   */
  public void receiveOrderFromAllPlayers() throws IOException {
    theMasterServer.receiveOrderFromAllPlayers();
  }

  /**
   * Method to receive a list of orders from a player
   * 
   * @throws IOException
   */
  public void receiveOrderFromPlayer() throws ClassNotFoundException, IOException {
    theMasterServer.receiveOrderFromPlayer();
  }

}
