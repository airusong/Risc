package edu.duke.ece651.mp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.*;
import java.io.ObjectInputStream;
import edu.duke.ece651.mp.common.TurnList;
import edu.duke.ece651.mp.server.MasterServer;

public class PlayerThread<T> implements Runnable {
  // public Socket player_socket = null;
  public Socket player_socket;
  // public TurnList<T> turn_list;
  public Object obj;

  public PlayerThread(Socket player_socket) {
    this.player_socket = player_socket;
    //this.turn_list = new TurnList<>("");
  }

  @Override
  //@SuppressWarnings("unchecked")
  public void run(){
    // handle TurnList receive from a player.
    try{
        InputStream o = player_socket.getInputStream();
        ObjectInputStream s = new ObjectInputStream(o);
        Object obj = s.readObject();
        this.obj = obj;

        //this.turn_list = (TurnList<T>)obj;
    }catch(Exception e){
        e.printStackTrace();
    }
  }

  // Send origin Map to all Players
  /*
  InputStream is = player_socket.getInputStream();
  BufferedReader br = new BufferedReader(new InputStreamReader(is));
  String msg = br.readLine();System.out.println("Received Message: "+msg);
  */

}
