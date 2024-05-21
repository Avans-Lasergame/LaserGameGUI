package Gui;

import Objects.Gun;
import Server.ServerThread;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ServerGUI {
    private static boolean isServerRunning = false;
    private static ServerThread serverThread;
    private static TextArea logArea, rawData;
    private static VBox gunsContainer;

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
            log("Deleted all guns");
            Platform.runLater(() -> {
                for (Gun gun : GUI.getGuns()) {
                    gun.stop();
                }
                GUI.getGuns().clear();
                PlayerCRUD.updateData();
                updateGunsContainer();
            });
        });

        Button stopClients = new Button("Stop client handlers");
        stopClients.setOnAction(e -> {
            Platform.runLater(() -> {
                for (Gun gun : GUI.getGuns()) {
                    gun.stop();
                }
            });
        });

        Button sendCommand = new Button("Send Command to All");
        sendCommand.setOnAction(e -> {
            String command = rawData.getText();
            Platform.runLater(() -> {
                for (Gun gun : GUI.getGuns()) {
                    gun.rawCommand(command);
                }
                rawData.clear();
            });
        });

        logArea = new TextArea();
        logArea.setMaxWidth(250);
        logArea.setEditable(false);

        rawData = new TextArea();
        rawData.setMaxWidth(250);

        gunsContainer = new VBox(10);
        gunsContainer.setPadding(new Insets(10));
        updateGunsContainer();

        ScrollPane scrollPane = new ScrollPane(gunsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        HBox topContainer = new HBox(10);
        topContainer.setPadding(new Insets(10));
        topContainer.getChildren().addAll(startServer, deleteGuns, stopClients, sendCommand);

        mainPane.setTop(topContainer);
        mainPane.setLeft(logArea);
        mainPane.setCenter(scrollPane);
        mainPane.setRight(rawData);

        return mainPane;
    }

    public static void updateGunsContainer() {
        Platform.runLater(() -> {
            gunsContainer.getChildren().clear();
            for (Gun gun : GUI.getGuns()) {
                HBox gunBox = new HBox(10);
                Label gunLabel = new Label("Gun"+gun.getID());
                TextField gunCommandField = new TextField();
                Button sendButton = new Button("Send");
                sendButton.setOnAction(e -> {
                    String command = gunCommandField.getText();
                    gun.rawCommand(command);
                    gunCommandField.clear();
                });
                gunBox.getChildren().addAll(gunLabel, gunCommandField, sendButton);
                gunsContainer.getChildren().add(gunBox);
            }
        });
    }

    public static void log(String message) {
        System.out.println(message);
        Platform.runLater(() -> logArea.appendText(message + "\n"));
    }
}
