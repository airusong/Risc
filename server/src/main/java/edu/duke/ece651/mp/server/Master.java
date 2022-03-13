package edu.duke.ece651.mp.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.duke.ece651.mp.common.Map;
import edu.duke.ece651.mp.common.Territory;
import edu.duke.ece651.mp.common.Turn;
import edu.duke.ece651.mp.common.TurnList;
import edu.duke.ece651.mp.common.V1Map;

public class Master {
  final MasterServer theMasterServer;
  public V1Map<Character> theMap;
  public ArrayList<String> players_identity;
  public ArrayList<TurnList<Character>> all_order_list;

  /**
   * Constructor
   * 
   * @throws IOException
   */
  public Master(int port, int num_players) throws IOException {
    this.theMasterServer = new MasterServer(port, num_players);
    this.players_identity = new ArrayList<String>(Arrays.asList("Green", "Blue"));
    this.theMap = new V1Map<Character>(this.players_identity);
    this.all_order_list = new ArrayList<TurnList<Character>>();
    
  }

  /**
   * @throws IOException
   *
   */
  public void acceptPlayers() throws IOException {
    this.theMasterServer.acceptPlayers();
  }

  /**
   * Method to send current version map to ALL the players
   * 
   * @throws IOException
   */
  public void sendMapToAll() throws IOException {
    theMasterServer.sendToAll((Object) theMap);
  }

  public void close() throws IOException {
    this.theMasterServer.close();
  }

  /**
   * Method to send Player Color to ALL the players
   * 
   * @throws IOException
   */
  public void sendPlayerIdentityToAll() throws IOException {
    theMasterServer.sendPlayerIdentityToAll(players_identity);
  }

  /**
   * Method to receive a list of orders from a player
   * 
   * @throws IOException
   */
  public Object receiveObjectFromPlayer(Socket player_socket) throws ClassNotFoundException, IOException {
    return theMasterServer.receiveObjectFromPlayer(player_socket);
  }

  /**
   * Method to receive and update orders from ALL players
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void receiveTurnListFromAllPlayers() throws IOException, ClassNotFoundException {
    theMasterServer.receiveTurnListFromAllPlayers();
    this.all_order_list = theMasterServer.all_order_list;
  }

  /**
   * Method to handle Orders
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void handleOrder() {
    for(int i=0; i<all_order_list.size(); i++){
      TurnList curr = all_order_list.get(i);
      for(int j=0; j<curr.getListLength();j++){
        Turn curr_turn = (Turn) curr.order_list.get(j);
        if(curr_turn.type == "move"){
          handleMoveOrder(curr_turn);
        }
        else if(curr_turn.type == "attack"){
          // handleAttackOrder(curr_turn);
        }
        else{
          // handle others Action
        }
      }
    }

  }

  /**
   * Method to handle Move Orders
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */

  public void handleMoveOrder(Turn moveOrder) {
    // how to handle move action??
    // TO DO: add RuleChecker
    int move_num = moveOrder.getNumber();
    Territory dep = moveOrder.getDep();
    Territory des = moveOrder.getDes();
    int player_id = moveOrder.getPlayerID();
    
    // update Territory & Map
    int unit_num_dep = theMap.myTerritories.get(dep.getName()).getUnit();
    int new_unit_num_dep = unit_num_dep - move_num;
    
    int unit_num_des = theMap.myTerritories.get(des.getName()).getUnit();
    int new_unit_num_des = unit_num_des + move_num;

    theMap.updateMap(dep.getName(), des.getName(),new_unit_num_dep, new_unit_num_des);

  }

}
