package edu.duke.ece651.mp.server;

import edu.duke.ece651.mp.common.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Master {
  final MasterServer theMasterServer;
  public V2Map<Character> theMap;
  public ArrayList<String> players_identity;
  public ArrayList<TurnList> all_order_list;
  HandleOrder<Character> theHandleOrder;

  private FoodResourceList food_list;
  private TechResourceList tech_list;

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
    this.theMap = new V2Map<Character>(this.players_identity);
    this.all_order_list = new ArrayList<TurnList>();
    PathChecking<Character> pcheck = new PathChecking<>(null);
    OwnerChecking<Character> ocheck = new OwnerChecking<>(pcheck);

    // initialize the Resources at the begining of the game
    this.food_list = new FoodResourceList(players_identity);
    this.tech_list = new TechResourceList(players_identity);
    for (String playerColor : players_identity) {
      food_list.addResource(playerColor, new FoodResource(50));
      tech_list.addResource(playerColor, new TechResource(50));

    }
    this.theHandleOrder = new HandleOrder<Character>(this.all_order_list, theMap, ocheck, players_identity, food_list,
        tech_list);
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

  /**
   * Method to send current resources list to ALL the players
   *
   * @throws IOException
   */
  public void sendResourceToAll() throws IOException {
    theMasterServer.sendToAll(food_list);
    theMasterServer.sendToAll(tech_list);
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
   * Method to send Player Color to ALL the players
   * 
   * @throws IOException
   */
  public void sendTurnStatusToAll(ArrayList<String> turnStatus) throws IOException {
    theMasterServer.sendToAll(turnStatus);
  }

  /**
   * int
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
   * Display in the server console the status of each order in the turn
   */
  private void displayTurnStatus(ArrayList<String> status_list) {
    System.out.println("--------------\nTurn Status:\n--------------\n");
    for (String turn_status : status_list) {
      System.out.println(turn_status);
    }
    System.out.println("\n");
  }

  /**
   * Method to handle orders
   * 
   * @return list of turn result
   */
  private ArrayList<String> handleOrders() {
    V2Map<Character> updatedMap = (V2Map<Character>) theHandleOrder.handleOrders(all_order_list, theMap);
    theMap = updatedMap;
    // update the resources
    food_list = theHandleOrder.getFoodList();
    tech_list = theHandleOrder.getTechList();

    theMasterServer.all_order_list.clear(); // reset the turn list

    ArrayList<String> status_list = new ArrayList<String>(theHandleOrder.turnStatus);
    theHandleOrder.turnStatus.clear(); // reset the list
    return status_list;
  }

  /**
   * Method to update players' resources at the end of each turn
   *
   * @return list of turn result
   */
  private void updatePlayerResource() {
    HashMap<String, Integer> food_l = theMap.getOwnersTerritoryFoodGroups();
    HashMap<String, Integer> tech_l = theMap.getOwnersTerritoryTechGroups();

    for (Map.Entry<String, Integer> s : food_l.entrySet()) {
      this.food_list.addResource(s.getKey(), s.getValue());
    }

    for (Map.Entry<String, Integer> s : tech_l.entrySet()) {
      this.tech_list.addResource(s.getKey(), s.getValue());
    }
  }

  /**
   * cl
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
      // Step-1: send map and resources to player
      sendMapToAll();
      sendResourceToAll();

      // Step-2:
      // Send game status to all players
      // Options: "Ready for accepting turn"
      // OR a player lost
      theMasterServer.sendToAll(gameStatus);
      System.out.println(gameStatus);

      if (gameStatus == "Ready for accepting turn!") {
        // Step-3:
        receiveTurnListFromAllPlayers();

        // Step-4:
        ArrayList<String> turnResult = handleOrders();
        sendTurnStatusToAll(turnResult);
        displayTurnStatus(turnResult);

        // Step-5:
        // check victory and defeat
        // update gameStatus if needed

        String winning_color = this.theMasterServer.detectresult(theMap);
        if (winning_color != null) {
          gameStatus = winning_color + " player has won!";
        } else {
          // add one unit to each territory
          theMap.updateMapbyOneUnit();
          // updated the territories' produced resources to theTextPlayr
          updatePlayerResource();
        }

      } else {
        break;
      }
    }
  }

}
