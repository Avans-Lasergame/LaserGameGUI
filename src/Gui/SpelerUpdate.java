package Gui;

import Objects.Gun;
import Objects.Player;
import Objects.Game;
import Objects.Vest;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.HashMap;
import java.util.UUID;

public class SpelerUpdate {

    public static VBox getComponent() {
        Game game = GUI.getGame();;
        VBox mainCreateSpelerBox = new VBox(20);
        mainCreateSpelerBox.setPadding(new Insets(20));

        Label forumTypeLabel = new Label("Update Speler data:");
        mainCreateSpelerBox.getChildren().add(forumTypeLabel);

        HBox contentRowBox = new HBox(10);
        VBox labelColumnBox = new VBox(22);
        VBox inputsColumnBox = new VBox(10);

        contentRowBox.getChildren().addAll(labelColumnBox, inputsColumnBox);

        //#region Speler
        Label spelerInputLabel = new Label("Speler: ");
        labelColumnBox.getChildren().add(spelerInputLabel);

        ComboBox<Player> SpelerOptionsComboBox = new ComboBox<>();
        HashMap<UUID, Player> spelers = game.getSpelers();
        for (UUID key : spelers.keySet()) {
            SpelerOptionsComboBox.getItems().add(spelers.get(key));
        }
        SpelerOptionsComboBox.setConverter(new StringConverter<Player>() {
            @Override
            public String toString(Player player) {
                StringBuilder gameData = new StringBuilder();
                gameData.append(player.getName());
                gameData.append(", ");
                gameData.append(player.getHealth() + " health");
                gameData.append(", ");
                return gameData.toString();
            }

            @Override
            public Player fromString(String string) {
                return null;
            }
        });

        inputsColumnBox.getChildren().add(SpelerOptionsComboBox);
        //#endregion

        //#region Name TextField
        Label nameInputLabel = new Label("Name: ");
        labelColumnBox.getChildren().add(nameInputLabel);

        javafx.scene.control.TextField nameInput = new javafx.scene.control.TextField();
        inputsColumnBox.getChildren().add(nameInput);
        //#endregion

        //#region Health TextField
        Label healthInputLabel = new Label("Health: ");
        labelColumnBox.getChildren().add(healthInputLabel);

        javafx.scene.control.TextField healthInput = new javafx.scene.control.TextField();
        inputsColumnBox.getChildren().add(healthInput);
        //#endregion

        SpelerOptionsComboBox.setOnAction(e -> {
            //get the scheduleItem that was selected
            Player selectedPlayer = SpelerOptionsComboBox.getValue();
            //fill name
            nameInput.setText(selectedPlayer.getName());
            //fill health
            healthInput.setText(Integer.toString(selectedPlayer.getHealth()));
        });


        //#region update button
        Button updateScheduleItemButton = new Button("Update Speler");
        updateScheduleItemButton.setOnAction(event -> {
            Player player = SpelerOptionsComboBox.getValue();

            System.out.println("updating scheduleItem " + player.getId() + ":");
            System.out.println("from:");
            System.out.println(player);

            String name = nameInput.getText();
            int health = Integer.parseInt(healthInput.getText());


            player.setAll(name, health, new Gun(),new Vest());

            System.out.println("To:");
            System.out.println(player);

            //updates the value in the Speler options Combobox
            SpelerOptionsComboBox.getItems().set(SpelerOptionsComboBox.getSelectionModel().getSelectedIndex(), player);
        });
        inputsColumnBox.getChildren().add(updateScheduleItemButton);
        //#endregion


        mainCreateSpelerBox.getChildren().add(contentRowBox);

        return mainCreateSpelerBox;
    }


}
