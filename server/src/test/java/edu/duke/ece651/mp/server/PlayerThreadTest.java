package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class PlayerThreadTest {
  @Disabled
  @Test
  public void test_run() throws UnknownHostException, IOException {
    Socket s = new Socket("127.0.0.1",8006);
    PlayerThread pth = new PlayerThread(s);
    //pth.run();
  }

}
