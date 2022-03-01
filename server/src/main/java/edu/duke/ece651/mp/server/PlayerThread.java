package edu.duke.ece651.mp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.*;


public class PlayerThread extends Thread{
    public Socket player_socket;

    public PlayerThread(Socket player_socket){
        this.player_socket = player_socket;
    }

    @Override
    public void run(){
        System.out.println("start a new thread.");
        
        try {
            InputStream is = player_socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String msg = br.readLine();
            System.out.println("Received Message: " + msg);
            player_socket.shutdownInput();
            is.close();
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }     
        
    }

    
}
