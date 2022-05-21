package server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import message.*;

public class Server {

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            // server is listening on port 1234
            server = new ServerSocket(1234);
            server.setReuseAddress(true);
            // running infinite loop for getting client request
            while (true) {
                Socket client = server.accept();
                if (!client.isConnected()) {
                    System.out.println("Waiting for new clients...");
                }
                // Displaying that new client is connected to server
                System.out.println("New client with IP: " + client.getInetAddress().getHostAddress() + " connected!");
                // create a new thread object
                ServerThread clientSock = new ServerThread(client);
                // This thread will handle the clients separately
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

