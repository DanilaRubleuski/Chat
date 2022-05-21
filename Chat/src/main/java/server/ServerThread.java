package server;

import java.io.*;
import java.net.Socket;

import message.*;

public class ServerThread implements Runnable {
    private final Socket clientSocket;

    // Constructor
    public ServerThread(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;

        OutputStream outputStream;
        ObjectOutputStream objectOutputStream;

        InputStream inputStream;
        ObjectInputStream objectInputStream;
        try {
            // get the output stream of client
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            // get the input stream of client
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //Sending data to Client
            outputStream = clientSocket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            //Receiving data from Client
            inputStream = clientSocket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            //Sending confirmation to Client after the connection
            out.println("Server: Ready!");
            //Getting Message blocks from Client
            Message message;
            while (true) {
                message = (Message) objectInputStream.readObject();
                if (!(message.getContent().equals("Quit"))) {
                    System.out.printf("Client " + clientSocket.getInetAddress().getHostAddress() +
                            " send: " + message.getContent() + "\n");
                } else {
                    System.out.printf("Client " + clientSocket.getInetAddress().getHostAddress() +
                            " just disconnected!\n");
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}