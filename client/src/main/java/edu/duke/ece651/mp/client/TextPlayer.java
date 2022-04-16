package edu.duke.ece651.mp.client;

import edu.duke.ece651.mp.common.FoodResource;
import edu.duke.ece651.mp.common.FoodResourceList;
import edu.duke.ece651.mp.common.MapTextView;
import edu.duke.ece651.mp.common.TechResource;
import edu.duke.ece651.mp.common.TechResourceList;
import edu.duke.ece651.mp.common.V2Map;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

public class TextPlayer {
  final PlayerServer connectionToMaster;
  V2Map<Character> theMap;
  MapTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  protected String identity; // color
  private FoodResource totalFood;
  private TechResource totalTech;

  /**
   * Constructor
   */
  public TextPlayer(String servername, int port, BufferedReader inputReader, PrintStream out)
      throws UnknownHostException, IOException {
    this.theMap = null; // will receive from server
    this.view = new MapTextView(theMap);
    if (servername == "null") {
      this.connectionToMaster = null;
    } else {
      this.connectionToMaster = new PlayerServer(servername, port);
    }
    this.inputReader = inputReader;
    this.out = out;
    this.identity = "";
    this.totalFood = new FoodResource(0);
    this.totalTech = new TechResource(0);
  }

  /**
   * method to update the copy of the map
   */
  public void updateMap(V2Map<Character> newMap) {
    theMap = newMap;
    view.updateTextView(newMap);
  }

  /**
   * method to print map
   */
  public void printMap() {
    out.println(view.displayMap());
  }

  /**
   * method to receive map from the server
   */
  @SuppressWarnings("unchecked")
  public void receiveMap() {
    V2Map<Character> receivedMap = (V2Map<Character>) connectionToMaster.receiveFromServer();
    updateMap(receivedMap);
  }

  /**
   * method to receive Player Color from the server
   */
  public void receiveIdentity() {
    System.out.println("Receive Identity from Server");
    String ident = (String) connectionToMaster.receiveFromServer();
    System.out.println("Set ident");
    setIdentity(ident);
    out.println("My player's color: " + ident);
  }

  public void setIdentity(String ident) {
    this.identity = ident;
  }

  /**
   * method to receive Player Resource List from the server
   */
  public void receiveResource() {
    System.out.println("Receive Resource from Server");
    FoodResourceList food_list = (FoodResourceList) connectionToMaster.receiveFromServer();
    TechResourceList tech_list = (TechResourceList) connectionToMaster.receiveFromServer();
    this.totalFood = food_list.resource_list.get(identity);
    this.totalTech = tech_list.resource_list.get(identity);
    System.out.println("Set Player resource List");
  }


  protected ArrayList<String> getMyOwnTerritories() {
    return theMap.getPlayerTerritories(identity);
  }

  protected ArrayList<String> getOthersTerritories() {
    for (int index = 0; index < 2; index++) {
      String color = theMap.players_colors.get(index);
      if (!color.equals(identity)) {
        return theMap.getPlayerTerritories(color);
      }
    }
    return null;

  }

  /**
   * Method to initiate game with the Server
   */
  public void initiateGame() {
    // Step-1:
    // Send "Ready "message to Server
    String msg = "Client is ready.";
    connectionToMaster.sendToServer(msg);

    // Step-2:
    receiveIdentity();
  }

  /**
   * Method to play the game
   * 
   * @throws IOException
   */
  public void playGame() throws IOException {
    while (true) { // main loop to play
      // Step-1: Receive map and resources from server
      receiveMap();
      System.out.print("Map received");
      printMap();
      // Receive resources info from server
      receiveResource();
      System.out.print("Resource received");

      // Step-2:
      // Receive game status from server
      String status = receiveAndPrintGameStatus();

      if (status.startsWith("Ready")) {
        // Step-3:
        // takeAndSendTurn(); - Done By GUI for Eval 2

        // Step-4:
        ArrayList<String> turnResult = receiveTurnStatus();
        printTurnStatus(turnResult);

      } else {
        // game ended
        break;
      }
    }
  }

  /**
   * method to receive game status from the server
   * 
   * @return game status in string format
   */
  public String receiveAndPrintGameStatus() {
    String status = (String) connectionToMaster.receiveFromServer();
    out.println(status);
    return status;
  }

  /**
   * method to receive turn status from the server
   * 
   */
  @SuppressWarnings("unchecked")
  public ArrayList<String> receiveTurnStatus() {
    ArrayList<String> turnStatus = (ArrayList<String>) connectionToMaster.receiveFromServer();
    return turnStatus;
  }

  /**
   * method to receive turn status from the server
   * 
   */
  private void printTurnStatus(ArrayList<String> status_list) {
    out.println("--------------\nTurn Status:\n--------------\n");
    for (String turn_status : status_list) {
      out.println(turn_status);
    }
    out.print("\n");
  }

  /**
   * method to return resource num
   *
   */
  private int getResourceNum(String resource_type) {
    if (resource_type.equals("food")) {
      return this.totalFood.getResourceAmount();
    } else if (resource_type.equals("tech")) {
      return this.totalTech.getResourceAmount();
    }
    return 0;
  }

  /**
   * Method to get total food resource
   **/
  private int getTotalFoodResourceAmount() {
    return this.totalFood.getResourceAmount();
  }

  /**
   * Method to get total tech resource
   **/
  private int getTotalTechResourceAmount() {
    return this.totalTech.getResourceAmount();
  }

  /**
   * Method to get the resource details of the player
   * 
   * @return string
   */
  public String getResourcesDtails() {
    StringBuilder resourceDetails = new StringBuilder("");
    resourceDetails.append("Player: " + this.identity + "\n");
    resourceDetails.append("***********RESOURCES**********\n");
    resourceDetails.append("Total Food: " + this.getTotalFoodResourceAmount() + "\n");
    resourceDetails.append("Total Tech: " + this.getTotalTechResourceAmount());

    return resourceDetails.toString();
  }

}
