package edu.duke.ece651.mp.client;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


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


