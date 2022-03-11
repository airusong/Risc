package edu.duke.ece651.mp.server;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.net.Socket;
import java.util.ArrayList;
import edu.duke.ece651.mp.common.V1Map;

public class MasterServer {
  public int port;
  public ServerSocket server_socket;
  public int num_players;
  public ArrayList<Socket> player_socket_list;
  public Socket player_socket;

  public MasterServer(int port, int num_players) {
    this.port = port;
    try {
      // create socket and bind it to port
      this.server_socket = new ServerSocket(port);
    } catch (Exception e) {
      // print exception message about Throwable object
      e.printStackTrace();
    }
    this.num_players = num_players;
    this.player_socket_list = new ArrayList<Socket>();
  }

  public int getPort() {
    return this.port;
  }

  public void acceptPlayers() throws IOException {
    // ExecutorService threadPool = Executors.newFixedThreadPool(num_players);
    // Socket player_socket;
    int connectedPlayers = 0;
    
    while (connectedPlayers < num_players) {
      System.out.println("Server is waiting...");
      player_socket = server_socket.accept();
      System.out.println("Server accepted.");
      player_socket_list.add(player_socket);
      connectedPlayers++;
      PlayerThread pth = new PlayerThread(player_socket);
      //new Thread(new PlayerThread(player_socket)).start();
      pth.start();
      
      // Thread t = new Thread();
      // PlayerThread t = new PlayerThread(player_socket);
      // t.start();
      // player_socket.close();
    }
    System.out.println("Server is connected to ALL the players.");
    
  }
  
  /**
   * Method to send any object over the socket
   * @param Object to send
   */
  public void sendToAll(Object obj) {
    // iterate through the player socket list
    for (Socket soc : player_socket_list) {
      sendToPlayer(obj, soc);
    }
  }

  /**
   * Method to send object to a specific player
   * @param object to send and player's socket
   */ 
  public void sendToPlayer(Object obj, Socket soc) {
    try {
      OutputStream o = soc.getOutputStream();
      ObjectOutputStream s = new ObjectOutputStream(o);

      s.writeObject(obj);
      s.flush();
      //s.close();
    } catch (Exception e) {
          System.out.println(e.getMessage());
          System.out.println("Error during serialization");
          e.printStackTrace();
     }
  }

  /* Close Server Socket. */
  public void close() throws IOException{
    server_socket.close();
    this.close_clients();
  }

  /* Close All Client Sockets. */
  public void close_clients() throws IOException{
    for(int i=0; i<player_socket_list.size(); ++i){
      player_socket_list.get(i).close();
    }
  }
}

