package edu.duke.ece651.mp.client;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.ObjectInputFilter.Status;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import edu.duke.ece651.mp.common.AttackTurn;
import edu.duke.ece651.mp.common.MapTextView;
import edu.duke.ece651.mp.common.MoveTurn;
import edu.duke.ece651.mp.common.Turn;
import edu.duke.ece651.mp.common.TurnList;
import edu.duke.ece651.mp.common.V1Map;

public class TextPlayer {
  final PlayerServer connectionToMaster;
  V1Map<Character> theMap;
  MapTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  protected String identity; // color

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
  }

  /**
   * method to update the copy of the map
   */
  public void updateMap(V1Map<Character> newMap) {
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
    V1Map<Character> receivedMap = (V1Map<Character>) connectionToMaster.receiveFromServer();
    updateMap(receivedMap);
  }

  /**
   * method to receive Player Color from the server
   */
  public void receiveIdentity() {
    String ident = (String) connectionToMaster.receiveFromServer();
    setIdentity(ident);
    out.println("My player's color: " + ident);
  }

  public void setIdentity(String ident) {
    this.identity = ident;

  }

  public void takeAndSendTurn() throws IOException {
    TurnList newTurn = takeTurn();
    connectionToMaster.sendToServer(newTurn);
  }

  /**
   * method to ask player for entering their turn
   */
  public TurnList takeTurn() throws IOException {
    TurnList myTurn = new TurnList(identity);

    out.println("You are the " + identity + " player and it's time to take your turn!.\n"
        + "There are two types of orders that you may issue: move and attack."
        + "You may issue any number of each type of these orders in a turn."
        + "Once you're done enetering your orders, hit D and your turn will be sent to the server.\n");

    char enteredOrder = 'D'; // by default
    do {
      try {
        out.println("\nEnter new order (M or A or D)\n" + "(M)ove\n" + "(A)ttack\n" + "(D)one");
        enteredOrder = readOrder();
        switch (enteredOrder) {
        case 'M':
          Turn newMoveOrder = readOrderDetails(enteredOrder);
          myTurn.addTurn(newMoveOrder);
          break;
        case 'A':
          Turn newOrder = readOrderDetails(enteredOrder);
          myTurn.addTurn(newOrder);
          break;

        case 'D':
          break;
        }

      } catch (IllegalArgumentException ex) {
        out.println(ex.getMessage());
        out.println("Please re-enter correctly!");
        continue;
      }
    } while (enteredOrder != 'D');
    return myTurn;
  }

  /**
   * method to read only the order type from the player
   */
  private char readOrder() throws IOException, EOFException {
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException("Error in input readline.");
    }

    String invalidFormatException = "That order is invalid: it does not have the correct format.";
    if (s.length() != 1) {
      throw new IllegalArgumentException(invalidFormatException);
    }

    char enteredLetter = s.toUpperCase().charAt(0);
    if (enteredLetter != 'M' && enteredLetter != 'A' && enteredLetter != 'D') {
      throw new IllegalArgumentException(invalidFormatException);
    } else if (enteredLetter == 'M') {
      out.println("Requested order: Move");
    }

    else if (enteredLetter == 'A') {
      out.println("Requested order: Attack");
    } else { // 'D'
      out.println("Done with the turn!");
    }

    return enteredLetter;
  }

  /**
   * method to read details about move and attack orders and add the order to the
   * TurnList
   * 
   * @return the Turn object (Move or Attack)
   */
  private Turn readOrderDetails(char orderType) throws IOException, EOFException {
    Turn newOrder;
    ArrayList<String> terrOptions = new ArrayList<String>();
    int enteredOption;

    // from which territory (only player's own territories)
    out.println("- From which territory? (Enter the option# from following list)");
    terrOptions = getMyOwnTerritories();
    out.print(view.displayTerritoriesAsList(terrOptions));
    enteredOption = readOption(terrOptions.size());
    String fromTerritory = terrOptions.get(enteredOption - 1);
    out.println("You selected " + fromTerritory + " as the source.");

    // to which territory
    out.println("- To which territory? (Enter the option# from following list)");
    if (orderType == 'A') { // For attack: only other players' territories
      terrOptions = getOthersTerritories();
    } else { // For move: only my own territories
      terrOptions = getMyOwnTerritories();
      ;
    }

    out.print(view.displayTerritoriesAsList(terrOptions));
    enteredOption = readOption(terrOptions.size());
    String toTerritory = terrOptions.get(enteredOption - 1);
    out.println("You selected " + toTerritory + " as the destination.");

    // how many units?
    out.println("- How many units?");
    int units = readOption(0);
    out.println("Requested " + units + " units.");

    if (orderType == 'M') {
      newOrder = new MoveTurn(fromTerritory, toTerritory, units, identity);
    } else { // 'A'
      newOrder = new AttackTurn(fromTerritory, toTerritory, units, identity);
    }

    return newOrder;
  }

  /**
   * this method takes an input number from the user
   * 
   * @param totalOptions (if 0, this is ignored)
   */
  private int readOption(int totalOptions) throws IOException, EOFException {
    int enteredOption;
    while (true) {
      String s = inputReader.readLine();
      if (s == null) {
        throw new EOFException("Error in input readline.");
      }

      enteredOption = Integer.valueOf(s);
      if (enteredOption < 1 || (totalOptions != 0 && enteredOption > totalOptions)) {
        out.println("Invalid option: please enter again!");
        continue;
      }
      break;
    }

    return enteredOption;
  }

  private ArrayList<String> getMyOwnTerritories() {
    return theMap.getPlayerTerritories(identity);
  }

  private ArrayList<String> getOthersTerritories() {
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
      // Step-1:
      receiveMap();
      printMap();

      // Step-2:
      // Receive game status from server
      String status = receiveAndPrintGameStatus();
      
      if (status.startsWith("Ready")) {
        // Step-3:
        takeAndSendTurn();

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

}
