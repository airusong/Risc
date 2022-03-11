package edu.duke.ece651.mp.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class PlayerServer {
  public Socket socket;
  public String servername;
  public int port;

  PlayerServer(String servername, int port) throws UnknownHostException, IOException {
    this.servername = servername;
    this.port = port;
    this.socket = new Socket(servername, port);
  }

  public Object receiveFromServer() {
    try {
      InputStream o = socket.getInputStream();
      ObjectInputStream s = new ObjectInputStream(o);
      Object obj = s.readObject();
      //s.close();
      return obj;

    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.out.println("Error during serialization");
      e.printStackTrace();
      return null;
    }
  }

}
