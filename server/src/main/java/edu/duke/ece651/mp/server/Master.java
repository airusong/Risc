package edu.duke.ece651.mp.server;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.duke.ece651.mp.common.Map;
import edu.duke.ece651.mp.common.Territory;
import edu.duke.ece651.mp.common.Turn;
import edu.duke.ece651.mp.common.TurnList;
import edu.duke.ece651.mp.common.V1Map;

public class Master {
  final MasterServer theMasterServer;
  public V1Map<Character> theMap;
  public ArrayList<String> players_identity;
  public ArrayList<TurnList> all_order_list;
  HandleOrder theHandleOrder;

  /**
   * Constructor
   * 
   * @throws IOException
   */
  public Master(int port, int num_players) throws IOException {
    this.theMasterServer = new MasterServer(port, num_players);
    this.players_identity = new ArrayList<String>(Arrays.asList("Green", "Blue"));
    this.theMap = new V1Map<Character>(this.players_identity);
    this.all_order_list = new ArrayList<TurnList>();
    this.theHandleOrder = new HandleOrder(all_order_list, theMap);
  }

  /**
   * @throws IOException
   * @throws InterruptedException
   *
   */
  public void acceptPlayers() throws IOException, InterruptedException {
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
   * Method to receive a list of orders from a player
   * 
   * @throws IOException
   */
  /*
  public Object receiveObjectFromPlayer(Socket player_socket) throws ClassNotFoundException, IOException {
    return theMasterServer.receiveObjectFromPlayer(player_socket);
  }*/

  /**
   * Method to receive and update orders from ALL players
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   * @throws InterruptedException
   */
  public void receiveTurnListFromAllPlayers() throws IOException, ClassNotFoundException, InterruptedException {
    theMasterServer.receiveTurnListFromAllPlayers();
    this.all_order_list = theMasterServer.all_order_list;
  }

  /**
   * Method to handle orders
   * 
   */
  public void handleOrders() {
    this.theHandleOrder.handleOrders(all_order_list, theMap);
  }
}
