package com.multithreaded.app;

import org.junit.jupiter.api.Test;
import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {

    @Test
    void testPingResponse() {
        try (Socket socket = new Socket("localhost", 8080);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("ping");
            String response = in.readLine();
            assertEquals("pong", response);

        } catch (IOException e) {
            fail("Client connection failed: " + e.getMessage());
        }
    }
}
