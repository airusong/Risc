package edu.duke.ece651.mp.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import edu.duke.ece651.mp.common.TurnList;
import edu.duke.ece651.mp.server.MasterServer;

public class HandleTurnThread<T> implements Runnable{
    public TurnList<T> turn_list;
    public Socket player_socket;
    
    public HandleTurnThread(Socket player_socket){
        this.player_socket = player_socket;
        this.turn_list = new TurnList<>("");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run(){
        // handle TurnList receive from a player.
        try{
            InputStream o = player_socket.getInputStream();
            ObjectInputStream s = new ObjectInputStream(o);
            Object obj = s.readObject();
            this.turn_list = (TurnList<T>)obj;
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    
}
