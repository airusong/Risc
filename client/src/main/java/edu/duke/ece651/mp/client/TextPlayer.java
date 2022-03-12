package edu.duke.ece651.mp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;

import javax.sound.midi.Receiver;

import edu.duke.ece651.mp.common.Map;
import edu.duke.ece651.mp.common.V1Map;

public class TextPlayer {
  final PlayerServer connectionToMaster;
  V1Map<Character> theMap;
  MapTextView view;
  final BufferedReader inputReader;
  final PrintStream out;

  private String identity;

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
    V1Map<Character> receivedMap = new V1Map<Character>();
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
  }

}
