package com.multithreaded.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;



/*
    * ClientHandler is a class that implements Runnable, which allows it to define the logic inside the run() method.
    *  This logic is then executed when we pass an instance of ClientHandler to a Thread and call start().
    * That’s how we run that task concurrently in a separate thread
* */

class ClientHandler implements Runnable {
    private Socket clientSocket;
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String clientmsg;
            while((clientmsg = fromClient.readLine()) != null) {
                System.out.println("Client Message: " + clientmsg);

                if(clientmsg.toLowerCase().equalsIgnoreCase("ping")) {
                    toClient.println("pong");
                } else
                toClient.println("Client Message: " + clientmsg + " 100");
            }

        }catch(IOException ex) {
            System.out.println("Server Error In Client Handler: " + ex.getMessage());
        }
        finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}


public class Server {
    public static void main(String[] args) {
        int port = 8080;
        try {
            ServerSocket socket = new ServerSocket(port);
            socket.setSoTimeout(30000);
            System.out.println("Server listening on port:" + port);
            while (true) {
                Socket clientSocket = socket.accept();//on accepting create a new thread for the request
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start(); //.start() for new thread, .run() for the current thread
            }
        } catch(Exception ex) {
            System.out.println("Server error: " + ex.getMessage());
        }
    }
}
