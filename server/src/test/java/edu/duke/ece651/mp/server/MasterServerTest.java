package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.mp.common.V1Map;

public class MasterServerTest {
  @Test
  public void test_getPort() throws IOException{
    MasterServer ms = new MasterServer(8002, 1);
    assertEquals(8002,ms.getPort());
    assertEquals(1, ms.num_players);
    assertEquals(new ArrayList<Socket>(), ms.player_socket_list);
    ms.close();
  }

  
  @Test
  public void test_acceptplayers() throws UnknownHostException, IOException{
    MasterServer ms = new MasterServer(8003, 1);
    Socket s = new Socket("127.0.0.1", 8003);
    ms.acceptPlayers();
    assertNotNull(ms.player_socket_list);
    ms.close();
    s.close();
  }

  
  @Test
  @SuppressWarnings("unchecked")
  public void test_sendToAll() throws UnknownHostException, IOException, ClassNotFoundException{
    MasterServer ms = new MasterServer(8004, 1);
    Socket soc = new Socket("127.0.0.1", 8004);
    ms.acceptPlayers();
    assertNotNull(ms.player_socket_list);

    V1Map<Character> Mymap = new V1Map<Character>();
    ms.sendToAll(Mymap);
    assertNotNull(ms.player_socket_list);
     
    InputStream o = soc.getInputStream();
    ObjectInputStream os = new ObjectInputStream(o);
    Object obj = os.readObject();
    assertNotNull(obj);
    String expected = "Green player:\n" + "-----------\n" + "\n" + "Blue player:\n" + "-----------\n" + "[Narnia]";
    
    //assertEquals(expected, obj);
    V1Map<Character> actual = (V1Map<Character>)obj;
    
    // To Do: transfer obj to string display.
    //assertEquals(expected, null);

    os.close();
    ms.close();
    soc.close();

  }
}
