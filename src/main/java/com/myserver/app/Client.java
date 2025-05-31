package com.myserver.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public void run() throws UnknownHostException, IOException {
        try {
            int port = 8080;
            InetAddress address = InetAddress.getLoopbackAddress(); //localhost
            Socket socket = new Socket(address, port);
                PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                toServer.println("Hello from Client");
                String line = fromServer.readLine();
                System.out.println("Response from Server: " + line);
                Scanner sc = new Scanner(System.in);
                toServer.println(sc.next());
                toServer.close();
                fromServer.close();
                socket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
             Client client = new Client();
             client.run();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
