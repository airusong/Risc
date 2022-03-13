package edu.duke.ece651.mp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.midi.Receiver;

import edu.duke.ece651.mp.common.Map;
import edu.duke.ece651.mp.common.MoveTurn;
import edu.duke.ece651.mp.common.Territory;
import edu.duke.ece651.mp.common.Turn;
import edu.duke.ece651.mp.common.TurnList;
import edu.duke.ece651.mp.common.V1Map;

public class TextPlayer {
  final PlayerServer connectionToMaster;
  V1Map<Character> theMap;
  MapTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  private String identity; // color

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
    // V1Map<Character> receivedMap =
    // (V1Map<Character>)connectionToMaster.receiveFromServer();
    Object rec_obj = connectionToMaster.receiveFromServer();
    // 
    ArrayList<String> players_identity = new ArrayList<String>(Arrays.asList("Green", "Blue"));
    V1Map<Character> receivedMap = new V1Map<Character>(players_identity);
    if (rec_obj instanceof V1Map) {
      receivedMap = (V1Map<Character>) rec_obj;
    }
    updateMap(receivedMap);
  }

  /**
   * method to receive Player Color from the server
   */
  public void receiveIdentity() {
    String ident = (String) connectionToMaster.receiveFromServer();
    this.identity = ident;
    out.println("My player's color: " + ident);
  }


  // yy - Testing
  
  public Turn createTurn() {
    Territory<Character> t1 = new Territory<Character>("Narnia","Green",new ArrayList<String>(),8);
    Territory<Character> t2 = new Territory<Character>("Midemio","Green",new ArrayList<String>(),3);
    MoveTurn<Character> mt = new MoveTurn<Character>("move", t1, t2, 2, 1);
    return (Turn) mt;
  }
  

  // yy - Testing
  
  public void sendTurn(){
    Turn t  = createTurn();
    TurnList t_list = new TurnList<>("player_info");
    t_list.order_list.add(t);
    connectionToMaster.sendToServer(t_list);
  }
  
}
