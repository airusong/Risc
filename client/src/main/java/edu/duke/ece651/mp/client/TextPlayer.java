package edu.duke.ece651.mp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;

import edu.duke.ece651.mp.common.Map;

public class TextPlayer {
  final PlayerServer connectionToMaster;
  Map<Character> theMap;
  MapTextView view;
  final BufferedReader inputReader;
  final PrintStream out;

  /**
   * Constructor
   */
  public TextPlayer(String servername, int port, BufferedReader inputReader, PrintStream out) throws UnknownHostException, IOException {
    this.theMap = null; // will receive from server
    this.view = new MapTextView(theMap);
    //this.connectionToMaster = new PlayerServer(servername, port);
    this.connectionToMaster = null; // TEMPORARY.. will create a socket actually
    this.inputReader = inputReader;
    this.out = out;
  }

  /** 
   * method to update the copy of the map
   */
  public void updateMap(Map<Character> newMap) {
    theMap = newMap;
  }

  /**
   * method to print map
   */
  public void printMap() {
    out.println(view.displayMap());
  }
  
}
