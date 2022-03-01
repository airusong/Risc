package edu.duke.ece651.mp.server;
//import edu.duke.ece651.mp.common.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;



public class MasterServer{
    public int port; 
    public ServerSocket server_socket;
    public int num_players;
    public int[] player_socket_list;

    public MasterServer(int port, int num_players){
        this.port = port;
        try{
            // create socket and bind it to port
            this.server_socket = new ServerSocket(port); 
        }
        catch(Exception e){
            // print exception message about Throwable object
            e.printStackTrace(); 
        }
        this.num_players = num_players;
    }

    public int getPort(){
        return this.port;
    }
    
    public void getPlayerSocket(int port) throws IOException{
        while(true){
            for(int i=0; i<num_players; ++i){
                System.out.println("Server is waiting...");
                Socket player_socket = server_socket.accept();
                Thread t = new Thread();
                //PlayerThread t = new PlayerThread(player_socket);
                t.start();
                System.out.println("Server is connected.");

                // Send message to Server
                /*
                OutputStream os = player_socket.getOutputStream();
                String msg = "Server is ready.";
                byte[] sendBytes = msg.getBytes("UTF-8");
                os.write(sendBytes);
                os.flush();

                os.close();
                server_socket.close();
                player_socket.close();
                */
                
            }
        }
    }

    public static void main(String[] args){
        int port = Integer.parseInt(args[0]);
        int num_players = Integer.parseInt(args[1]);
        MasterServer test_MasterServer = new MasterServer(port, num_players);
        
        try {
            test_MasterServer.getPlayerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





