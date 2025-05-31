package com.myserver.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;

public class Server {
    public void run() throws IOException {
        int port = 8080;
        try {
            int queueLimitBacklog = 20;
            ServerSocket serverSocket = new ServerSocket(port, queueLimitBacklog);
            serverSocket.setSoTimeout(5000);
            while (true) {
                System.out.println("Server Listening on port " + port);
                Socket acceptedSocket = serverSocket.accept();
                System.out.println("Connection accepted....");
                System.out.println(acceptedSocket.getRemoteSocketAddress());
                PrintWriter toClient = new PrintWriter(acceptedSocket.getOutputStream(), true);
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedSocket.getInputStream())); //because I cannot read a stream directly in the buffered reader, buffered reader expects Reader
                toClient.println("Hello from Server");
                fromClient.readLine();
                System.out.println("Response from Client: " + fromClient.readLine());
                toClient.close();
                fromClient.close();
                acceptedSocket.close();
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void main(String[] args) {
        System.out.println("Single Threaded Server Started..");
        Server server = new Server();
        try {
            server.run();

        }catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
