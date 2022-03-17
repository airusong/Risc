package edu.duke.ece651.mp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MockMasterServer extends MasterServer{
  public MockMasterServer(int port, int num_players) throws IOException {
    super(port, num_players);
  }

  @Override
  public int getPort() {
    return this.port;
  }

  @Override
  public void acceptPlayers() throws IOException {
    // ExecutorService threadPool = Executors.newFixedThreadPool(num_players);
    // Socket player_socket;
    int connectedPlayers = 0;

    while (connectedPlayers < num_players) {
      System.out.println("Server is waiting...");
      player_socket = null;
      System.out.println("Server accepted.");
      player_socket_list.add(player_socket);
      connectedPlayers++;
    }
    System.out.println("Server is connected to ALL the players.");

  }

  /**
   * Method to send any object over the socket
   * 
   * @param Object to send
   */
  @Override
  public void sendToAll(Object obj) {
    // empty as we don't wanna send anything while mocking
  }

  /* Close Server Socket. */
  @Override
  public void close() throws IOException {

  }

  /**
   * Method to send Player Identity(Green/Yellow) over the socket
   * 
   * @param Object to send
   */
  @Override
  public void sendPlayerIdentityToAll(List<String> players_identity) {
    System.out.println("Sending color to player: Green");
  }
}
