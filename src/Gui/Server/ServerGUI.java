package Gui.Server;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.ServerSocket;


public class ServerGUI {
    private static boolean isServerRunning = false;
    private static ServerThread serverThread;

    public static StackPane getComponent() {
        StackPane mainPane = new StackPane();

        Button startServer = new Button("Start Server");
        startServer.setOnAction(e -> {
            if (!isServerRunning) {
                serverThread = new ServerThread();
                serverThread.start();
                isServerRunning = true;
                startServer.setText("Stop Server");
            } else {
                serverThread.stopServer();
                isServerRunning = false;
                startServer.setText("Start Server");
            }
        });

        mainPane.getChildren().add(startServer);
        return mainPane;
    }

    private static class ServerThread extends Thread {
        private ServerSocket serverSocket;

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(8888);
                Platform.runLater(() -> {
                    System.out.println("Server running on port 8888");
                });

                while (!serverSocket.isClosed()) {
                    Thread clientThread = new Thread(new ClientHandler(serverSocket.accept()));
                    clientThread.start();
                }
            } catch (IOException e) {
                Platform.runLater(() -> {
                    System.out.println("Server stopped");
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
}