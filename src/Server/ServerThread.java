package Server;

import Gui.ServerGUI;
import javafx.application.Platform;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerThread extends Thread {
    private ServerSocket serverSocket;
    private int port = 8888; // the port for the server

    @Override
    public void run() {
        try {
            // create a server socket
            serverSocket = new ServerSocket(port);
            Platform.runLater(() -> {
                ServerGUI.log("Server running on port: "+port);
            });

            while (!serverSocket.isClosed()) {
                Thread clientThread = new Thread(new ClientHandler(serverSocket.accept()));
                clientThread.start(); // make a new client handler and start
            }
        } catch (IOException e) {
            Platform.runLater(() -> {
                ServerGUI.log("Server stopped"); // on exception log the server stop
            });
        }
    }

    public void stopServer() {
        try {
            serverSocket.close(); // stop the server
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
