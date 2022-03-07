package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.google.common.io.Resources;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;

import edu.duke.ece651.mp.common.V1Map;

public class ServerTest {
    @Disabled
    @Test
    //@ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
    void test_main() throws IOException{
        // Output
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        // Input
        InputStream input = getClass().getClassLoader().getResourceAsStream("input.txt");
        assertNotNull(input);
        // Ouput
        InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output.txt");
        assertNotNull(expectedStream);

        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;

        try {
            System.setIn(input);
            System.setOut(out);
            Server.main(new String[0]);
        }
        finally {
            System.setIn(oldIn);
            System.setOut(oldOut);

        }
        String expected = new String(expectedStream.readAllBytes());
        String actual = bytes.toString();

        assertEquals(expected, actual);
    }
}
