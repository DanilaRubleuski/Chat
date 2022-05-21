package client;

import message.*;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        System.out.println("Enter IP address to connect to: ");
        Scanner scanner = new Scanner(System.in);
        String ip = scanner.nextLine();
        try (Socket socket = new Socket(ip, 1234)) {

            //Sending data to server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            //Reading data from server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //Sending objects
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            //Reading objects
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            //Input content
            Scanner sc = new Scanner(System.in);
            String str = "", line = "";
            //Getting "Ready text"
            str = in.readLine();
            System.out.println(str);
            while (!(line.equals("Quit"))) {
                //Sending Message blocks to Server
                line = sc.nextLine();
                Message message = new Message(line);
                objectOutputStream.writeObject(message);
                out.flush();
            }
            //Closing Scanner
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

