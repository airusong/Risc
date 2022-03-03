package edu.duke.ece651.mp.client;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class PlayerServer{
    public Socket socket;
    public String servername;
    public int port;

    PlayerServer(String servername, int port) throws UnknownHostException, IOException{
        this.servername = servername;
        this.port = port;
        this.socket = new Socket(servername,port);
    } 
}


