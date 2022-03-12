package edu.duke.ece651.mp.server;

import java.io.IOException;

public class Server {
  final Master theMaster;

  public Server(int port, int num_players) throws IOException {
    this.theMaster = new Master(port, num_players);
  }

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    //int port = Integer.parseInt(args[0]);
    //int num_players = Integer.parseInt(args[1]);
    int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
    int num_players = args.length > 1 ? Integer.parseInt(args[1]) : 1 ;
    Master theMaster = new Master(port, num_players);

    
    theMaster.acceptPlayers();
    theMaster.sendPlayerIdentityToAll();
    theMaster.sendMapToAll();
    theMaster.receiveTurnListFromAllPlayers();
    theMaster.handleMoveOrder(theMaster);
  }

}
