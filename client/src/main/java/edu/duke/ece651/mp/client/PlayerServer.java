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

    public static void main(String[] args) throws InterruptedException {
        try {
            int port = Integer.parseInt(args[1]);
            String servername = args[0];
            PlayerServer player_sever = new PlayerServer(servername, port);
            
            // Send message to Server
            OutputStream os = player_sever.socket.getOutputStream();
            String msg = "Client is ready.";
            byte[] sendBytes = msg.getBytes("UTF-8");
            os.write(sendBytes);
            os.flush();
            
            //os.close();
            //player_sever.socket.shutdownOutput();
            //player_sever.socket.close();
            
            // Received message from Server
            /*
            InputStream is = player_sever.socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while((info = br.readLine())!=null){
                System.out.println("Received message from Server: "+info);
            }
            
            os.close();
            is.close();
            br.close();
            player_sever.socket.close();
            */
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
}


