package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.mp.common.V1Map;

public class MasterTest {
  @Test
  public void test_master() throws IOException{
    Master m = new Master(8000, 2);
    assertEquals(8000, m.theMasterServer.port);
    assertEquals(2, m.theMasterServer.num_players);
    //V1Map<Character> map = new V1Map<Character>();
    //assertEquals(map, m.theMap);
    m.close();
  }

  @Test
  public void test_acceptplayers() throws IOException{
    Master m = new Master(8001, 1);
    Socket s = new Socket("127.0.0.1", 8001);
    //m.acceptPlayers();
    //m.close();
    assertNotNull(m.theMasterServer.player_socket_list);
    //assertNull(m.theMasterServer.server_socket.accept());
    m.close();
    s.close();
  }

  //@Disabled
  @Test
  public void test_sendMapToAll() throws UnknownHostException, IOException, ClassNotFoundException{
    Master m = new Master(8005, 1);
    Socket soc = new Socket("127.0.0.1", 8005);
    m.acceptPlayers();
    assertNotNull(m.theMap);
    m.sendMapToAll();
    
    /*
    InputStream o = soc.getInputStream();
    ObjectInputStream os = new ObjectInputStream(o);
    Object obj = os.readObject();
    assertNotNull(obj);
    assertEquals(Mymap, obj);
    os.close();
    */

    m.close();
    soc.close();

  }

}
