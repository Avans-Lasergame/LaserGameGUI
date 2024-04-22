package Gui;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("Server running on port 8888");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostName());

            ClientHandler clientHandler = new ClientHandler(clientSocket);
            Thread thread = new Thread(clientHandler);
            thread.start();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from client " + clientSocket.getInetAddress().getHostName() + ": " + inputLine);
                out.println("Server received: " + inputLine);
            }

            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
