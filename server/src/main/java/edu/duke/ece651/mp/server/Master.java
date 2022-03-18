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
  public Map<Character> theMap;
  public ArrayList<String> players_identity;
  public ArrayList<TurnList> all_order_list;
  HandleOrder<Character> theHandleOrder;

  /**
   * Constructor
   * 
   * @throws IOException
   */
  public Master(int port, int num_players) throws IOException {
    if (port != 0) {
      this.theMasterServer = new MasterServer(port, num_players);
    } else {
      this.theMasterServer = new MockMasterServer(port, num_players);
    }
    this.players_identity = new ArrayList<String>(Arrays.asList("Green", "Blue"));
    this.theMap = new V1Map<Character>(this.players_identity);
    this.all_order_list = new ArrayList<TurnList>();
    this.theHandleOrder = new HandleOrder<Character>(all_order_list, theMap);
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
   * Method to receive and update orders from ALL players
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   * @throws InterruptedException
   */
  private void receiveTurnListFromAllPlayers() throws IOException, ClassNotFoundException, InterruptedException {
    theMasterServer.receiveTurnListFromAllPlayers();
    this.all_order_list = theMasterServer.all_order_list;
  }

  /**
   * Method to handle orders
   * 
   */
  private void handleOrders() {
    Map<Character> updatedMap = this.theHandleOrder.handleOrders(all_order_list, theMap);
    displayTurnStatus();
    theMap = updatedMap;
  }

  /**
   * Display in the server console the status of each order in the turn
   */
  private void displayTurnStatus() {
    ArrayList<String> status_list = theHandleOrder.turnStatus;
    for (String turn_status : status_list) {
      System.out.println(turn_status);
    }
  }

  /**
   * Method to start a game by accepting players sending the players their colors
   * 
   * @throws IOException, InterruptedException
   */
  public void initiateGame() throws IOException, InterruptedException {
    // Step-1:
    acceptPlayers();

    // Step-2:
    sendPlayerIdentityToAll();
  }

  /**
   * Method to play the game
   * 
   * @throws IOException, ClassNotFoundException, InterruptedException
   */
  public void playGame() throws IOException, ClassNotFoundException, InterruptedException {
    String gameStatus = "Ready for accepting turn!";
    while (true) { // main playing loop
      // Step-1:
      sendMapToAll();

      // Step-2:
      // Send game status to all players
      // Options: "Ready for accepting turn"
      // OR a player lost
      theMasterServer.sendToAll(gameStatus);

      if (gameStatus == "Ready for accepting turn!") {
        // Step-3:
        receiveTurnListFromAllPlayers();

        // Step-4:
        handleOrders();

        // Step-5:
        // check victory and defeat
        // update gameStatus if needed
      }
      else  {
        break;
      }
    }
  }
}
