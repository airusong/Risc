/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.mp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.UnknownHostException;

import edu.duke.ece651.mp.common.Map;
import edu.duke.ece651.mp.common.V1Map;

public class Client {
  final TextPlayer theTextPlayer;

  /**
   * constructor
   */
  public Client(String servername, int port, BufferedReader inputReader, PrintStream out)
      throws IOException, UnknownHostException {
    this.theTextPlayer = new TextPlayer(servername, port, inputReader, out);
  }

  /**
   * Main program for Client/Player
   */
  public static void main(String[] args) throws InterruptedException {
    try {
      int port = args.length > 0 ? Integer.parseInt(args[1]) : 8080;
      String servername = args.length > 1 ? args[0] : "127.0.0.1";

      BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
      Client theClient = new Client(servername, port, input, System.out);

      if (servername != "null") {
        // Send "Ready "message to Server
        OutputStream os = theClient.theTextPlayer.connectionToMaster.socket.getOutputStream();
        PrintWriter out = new PrintWriter(os, true);
        String msg = "Client is ready.";
        out.println(msg);
        out.flush();

        /*
         * // Received message from Server InputStream is =
         * theClient.theTextPlayer.connectionToMaster.socket.getInputStream();
         * BufferedReader br = new BufferedReader(new InputStreamReader(is)); String
         * receivedMsg = br.readLine();
         * System.out.println("Received message from Server: " + receivedMsg);
         * is.close(); br.close();
         * 
         */

        theClient.theTextPlayer.receiveInfo();
        theClient.theTextPlayer.receiveMap();

        os.close();

        // theClient.theTextPlayer.connectionToMaster.socket.shutdownOutput();
        theClient.theTextPlayer.connectionToMaster.socket.close();

      } else {

        // The map should be received from master
        // using minimal V1Map for now
        V1Map<Character> mapFromServer = new V1Map<Character>();
        theClient.theTextPlayer.updateMap(mapFromServer);
      }
      theClient.theTextPlayer.printMap();

    } catch (

    Exception e) {
      e.printStackTrace();
    }
  }
}
