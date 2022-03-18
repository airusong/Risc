package edu.duke.ece651.mp.server;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.duke.ece651.mp.common.Map;
import edu.duke.ece651.mp.common.OwnerChecking;
import edu.duke.ece651.mp.common.PathChecking;
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
     if (port != 0) {
      this.theMasterServer = new MasterServer(port, num_players);
    }
    else {
      this.theMasterServer = new MockMasterServer(port, num_players);
    }
    this.players_identity = new ArrayList<String>(Arrays.asList("Green", "Blue"));
    this.theMap = new V1Map<Character>(this.players_identity);
    this.all_order_list = new ArrayList<TurnList>();
    OwnerChecking<Character> ocheck=new OwnerChecking<>(null);
    PathChecking<Character> pcheck=new PathChecking<>(ocheck);
    this.theHandleOrder = new HandleOrder(all_order_list, theMap,pcheck);
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
    //System.out.println("For test: gonna handle orders.");
    PathChecking<Character> pcheck=new PathChecking<>(null);
    OwnerChecking<Character> ocheck=new OwnerChecking<>(pcheck);
    this.theHandleOrder = new HandleOrder(this.all_order_list, theMap,ocheck);
    this.theHandleOrder.handleOrders();
  }

}
