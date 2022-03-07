package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.mp.common.V1Map;

public class MasterTest {
  @Test
  public void test_master(){
    Master m = new Master(8000, 2);
    assertEquals(8000, m.theMasterServer.port);
    assertEquals(2, m.theMasterServer.num_players);
    //V1Map<Character> map = new V1Map<Character>();
    //assertEquals(map, m.theMap);
  }

  @Test
  public void test_acceptplayers() throws IOException{
    Master m = new Master(8001, 1);
    Socket s = new Socket("127.0.0.1", 8001);
    m.acceptPlayers();
    m.close();
    assertNotNull(m.theMasterServer.player_socket_list);
    //assertNull(m.theMasterServer.server_socket.accept());

  }

}
