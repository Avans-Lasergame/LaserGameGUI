package Objects.Server;

import Gui.ServerGUI;
import javafx.application.Platform;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerThread extends Thread {
    private ServerSocket serverSocket;
    private int port = 8888;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            Platform.runLater(() -> {
                ServerGUI.log("Server running on port: "+port);
            });

            while (!serverSocket.isClosed()) {
                Thread clientThread = new Thread(new ClientHandler(serverSocket.accept()));
                clientThread.start();
            }
        } catch (IOException e) {
            Platform.runLater(() -> {
                ServerGUI.log("Server stopped");
            });
        }
    }

    public void stopServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
