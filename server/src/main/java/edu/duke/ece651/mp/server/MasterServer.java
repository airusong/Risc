package edu.duke.ece651.mp.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MasterServer {
  public int port;
  public ServerSocket server_socket;
  public int num_players;
  public int[] player_socket_list;
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
  }

  public int getPort() {
    return this.port;
  }

  public void runServer(int port) throws IOException {
    // ExecutorService threadPool = Executors.newFixedThreadPool(num_players);
    // Socket player_socket;
    while (true) {
      System.out.println("Server is waiting...");
      player_socket = server_socket.accept();
      System.out.println("Server is connected.");

      new Thread(new PlayerThread(player_socket)).start();

      // Thread t = new Thread();
      // PlayerThread t = new PlayerThread(player_socket);
      // t.start();
      // player_socket.close();
    }
  }
}
