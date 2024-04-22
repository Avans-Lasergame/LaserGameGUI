package Gui.SpelerCRUD;

import Gui.GUI;
import Objects.Player;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.UUID;

public class SpelerOverview {
    public static VBox getComponent() {

        VBox mainBox = new VBox(20);
        mainBox.setPadding(new Insets(20));

        Label label = new Label("Overview of Spelers:");
        mainBox.getChildren().add(label);


        if (GUI.getGame().getPlayers().isEmpty()) {
            Label labelEmpty = new Label("No Spelers yet");
            mainBox.getChildren().add(labelEmpty);
        } else {
            HBox listsContainerBox = new HBox();

            ListView<String> collumnNames = new ListView<>();
            collumnNames.getItems().add("Name");

            ListView<String> collumnHealth = new ListView<>();
            collumnHealth.getItems().add("Health");

            ListView<Button> collumnDelete = new ListView<>();
            Button deleteButtonLabel = new Button("Delete");
            deleteButtonLabel.setPadding(new Insets(0));
            deleteButtonLabel.setStyle("-fx-border: none; -fx-background-color: none;");
            collumnDelete.getItems().add(deleteButtonLabel);

            listsContainerBox.getChildren().addAll(collumnNames, collumnHealth, collumnDelete);

            HashMap<UUID, Player> Spelers = GUI.getGame().getPlayers();
            for (UUID key : Spelers.keySet()) {
                collumnNames.getItems().add(Spelers.get(key).getName());
                collumnHealth.getItems().add(Integer.toString(Spelers.get(key).getHealth()));
                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction(e -> {
                    Spelers.get(key).delete(GUI.getGame());
                });
                deleteButton.setPadding(new Insets(0));
                collumnDelete.getItems().add(deleteButton);
            }


            collumnNames.setPrefWidth(100);
            collumnHealth.setPrefWidth(100);
            collumnDelete.setPrefWidth(100);

            mainBox.getChildren().add(listsContainerBox);
        }

        return mainBox;
    }
}

