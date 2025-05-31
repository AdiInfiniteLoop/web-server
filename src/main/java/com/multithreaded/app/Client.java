package com.multithreaded.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    //To test the multithreaded server we have to use thread pool of say 100 threads
    public Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                int port  = 8080;
                try {
                    InetAddress address = InetAddress.getLoopbackAddress();
                    Socket socket = null;
                    try {
                        socket = new Socket(address, port);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    try{
                        try (
                            PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
                            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            ) {
                            toServer.println("Hello from Client");
                            System.out.println("Response from Server: " + fromServer.readLine());
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }
        };
    }
    public static void main(String[] args) {
        Client client = new Client();
        for (int i = 0; i < 100; i++) {
            try {
                Thread thread = new Thread(client.getRunnable());
                thread.start();
            } catch(Exception ex) {
                System.out.println("Something went wrong!!!");
                return;
            }
        }
    }
}
