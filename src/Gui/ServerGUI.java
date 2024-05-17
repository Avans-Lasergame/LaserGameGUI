package Gui;

import Server.ServerThread;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ServerGUI {
    private static boolean isServerRunning = false;
    private static ServerThread serverThread;
    private static TextArea logArea;

    public static BorderPane getComponent() {
        BorderPane mainPane = new BorderPane();

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
        Button deleteGuns = new Button("Delete all guns");
        deleteGuns.setOnAction(e -> {
            GUI.getGuns().clear();
        });
        logArea = new TextArea();
        logArea.setMaxWidth(200);
        logArea.setEditable(false);


        HBox topContainer = new HBox(10); // Spacing between children
        topContainer.setPadding(new Insets(10));

        topContainer.getChildren().addAll(startServer,deleteGuns);
        mainPane.setTop(topContainer);
        mainPane.setLeft(logArea);

        return mainPane;
    }

    public static void log(String message) {
        logArea.appendText(message + "\n");
    }
}
