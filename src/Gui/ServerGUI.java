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
    private static ServerThread serverThread; // Server Thread
    private static TextArea logArea, rawData;
    private static VBox gunsContainer;

    public static BorderPane getComponent() {
        BorderPane mainPane = new BorderPane();

        // start stop server
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

        // delete all guns form the GUI
        Button deleteGuns = new Button("Delete all guns");
        deleteGuns.setOnAction(e -> {
            log("Deleted all guns");
            Platform.runLater(() -> {
                for (Gun gun : GUI.getGuns()) {
                    gun.stop(); // stop the client before deleting the gun
                }
                GUI.getGuns().clear();
                PlayerCRUD.updateData(); // update all gun related stuff
                updateGunsContainer();
            });
        });

        // stop all running clients
        Button stopClients = new Button("Stop client handlers");
        stopClients.setOnAction(e -> {
            Platform.runLater(() -> {
                for (Gun gun : GUI.getGuns()) {
                    gun.stop();
                }
            });
        });

        // send the raw command to all connected guns
        Button sendCommand = new Button("Send Command to All");
        sendCommand.setOnAction(e -> {
            String command = rawData.getText();
            Platform.runLater(() -> {
                for (Gun gun : GUI.getGuns()) {
                    gun.rawCommand(command); // send the command to the gun
                }
                rawData.clear();
            });
        });

        // the server log for all the events
        logArea = new TextArea();
        logArea.setMaxWidth(250);
        logArea.setEditable(false);

        // the raw data field
        rawData = new TextArea();
        rawData.setMaxWidth(250);

        // the area where all the guns are displayed
        gunsContainer = new VBox(10);
        gunsContainer.setPadding(new Insets(10));
        updateGunsContainer();

        ScrollPane scrollPane = new ScrollPane(gunsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // the container for all the buttons
        HBox topContainer = new HBox(10);
        topContainer.setPadding(new Insets(10));
        topContainer.getChildren().addAll(startServer, deleteGuns, stopClients);

        // Create labeled containers for the GUI sections
        VBox topSection = new VBox(10);
        topSection.setPadding(new Insets(10));
        topSection.getChildren().addAll(new Label("Server Controls"), topContainer);

        VBox logSection = new VBox(10);
        logSection.setPadding(new Insets(10));
        logSection.getChildren().addAll(new Label("Server Log"), logArea);

        VBox commandSection = new VBox(10);
        commandSection.setPadding(new Insets(10));
        commandSection.getChildren().addAll(new Label("Raw Command"), rawData, sendCommand);

        VBox gunsSection = new VBox(10);
        gunsSection.setPadding(new Insets(10));
        gunsSection.getChildren().addAll(new Label("Connected Guns"), scrollPane);

        mainPane.setTop(topSection);
        mainPane.setLeft(logSection);
        mainPane.setCenter(gunsSection);
        mainPane.setRight(commandSection);

        return mainPane;
    }

    public static void updateGunsContainer() { // update the gun container when a gun is added or deleted
        Platform.runLater(() -> {
            gunsContainer.getChildren().clear();
            for (Gun gun : GUI.getGuns()) {
                HBox gunBox = new HBox(10);
                Label gunLabel = new Label("Gun" + gun.getID());
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

    public static void log(String message) { // the log method for all other classes
        System.out.println(message);
        Platform.runLater(() -> logArea.appendText(message + "\n"));
    }
}
