package edu.duke.ece651.mp.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.net.Socket;
import java.util.ArrayList;
import edu.duke.ece651.mp.common.V1Map;

public class MasterServer {
  public int port;
  public ServerSocket server_socket;
  public int num_players;
  public ArrayList<Socket> player_socket_list;
  public Socket player_socket;

  public MasterServer(int port, int num_players) throws IOException {
    this.port = port;
    // create socket and bind it to port
    this.server_socket = new ServerSocket(port);
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
      Thread t = new Thread(pth);
      // new Thread(new PlayerThread(player_socket)).start();
      t.start();
    }
    System.out.println("Server is connected to ALL the players.");
  }

  /**
   * Method to send any object over the socket
   * 
   * @param Object to send
   * @throws IOException
   */
  public void sendToAll(Object obj) throws IOException {
    // iterate through the player socket list
    for (Socket soc : player_socket_list) {
      sendToPlayer(obj, soc);
    }
  }

  /**
   * Method to send object to a specific player
   * 
   * @param object to send and player's socket
   * @throws IOException
   */
  public void sendToPlayer(Object obj, Socket soc) throws IOException {
    OutputStream o = soc.getOutputStream();
    ObjectOutputStream s = new ObjectOutputStream(o);

    s.writeObject(obj);
    s.flush();
    // s.close();

  }

  /* Close Server Socket. */
  public void close() throws IOException {
    server_socket.close();
    this.close_clients();
  }

  /* Close All Client Sockets. */
  public void close_clients() throws IOException {
    for (int i = 0; i < player_socket_list.size(); ++i) {
      player_socket_list.get(i).close();
    }
  }

  /**
   * Method to send Player Identity(Green/Yellow) over the socket
   * 
   * @param Object to send
   * @throws IOException
   */
  public void sendPlayerIdentityToAll(List<String> players_identity) throws IOException {
    for (int i = 0; i < player_socket_list.size(); ++i) {
      sendToPlayer(players_identity.get(i), player_socket_list.get(i));
    }
  }

  /**
   * Method to receive Object over the socket
   * 
   * @return Object received from player
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public Object receiveOrderFromPlayer() throws IOException, ClassNotFoundException {
    InputStream o = player_socket.getInputStream();
    ObjectInputStream s = new ObjectInputStream(o);
    Object obj = s.readObject();
    return obj;
  }

  /**
   * Method to receive Object over the socket from all players
   * 
   * @throws IOException
   */
  public void receiveOrderFromAllPlayers() throws IOException {
    int connectedPlayers = 0;

    while (connectedPlayers < num_players) {
      System.out.println("Server is waiting...");
      player_socket = server_socket.accept();
      System.out.println("Server accepted.");
      player_socket_list.add(player_socket);
      connectedPlayers++;
      PlayerThread pth = new PlayerThread(player_socket);
      Thread t = new Thread(pth);
      // new Thread(new PlayerThread(player_socket)).start();
      t.start();
    }
    System.out.println("Server is connected to ALL the players.");
  }

}
