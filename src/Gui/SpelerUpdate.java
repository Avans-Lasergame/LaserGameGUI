package Gui;

import Objects.Gun;
import Objects.Speler;
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

        ComboBox<Speler> SpelerOptionsComboBox = new ComboBox<>();
        HashMap<UUID, Speler> spelers = game.getSpelers();
        for (UUID key : spelers.keySet()) {
            SpelerOptionsComboBox.getItems().add(spelers.get(key));
        }
        SpelerOptionsComboBox.setConverter(new StringConverter<Speler>() {
            @Override
            public String toString(Speler speler) {
                StringBuilder gameData = new StringBuilder();
                gameData.append(speler.getName());
                gameData.append(", ");
                gameData.append(speler.getHealth() + " health");
                gameData.append(", ");
                return gameData.toString();
            }

            @Override
            public Speler fromString(String string) {
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
            Speler selectedSpeler = SpelerOptionsComboBox.getValue();
            //fill name
            nameInput.setText(selectedSpeler.getName());
            //fill health
            healthInput.setText(Integer.toString(selectedSpeler.getHealth()));
        });


        //#region update button
        Button updateScheduleItemButton = new Button("Update Speler");
        updateScheduleItemButton.setOnAction(event -> {
            Speler speler = SpelerOptionsComboBox.getValue();

            System.out.println("updating scheduleItem " + speler.getId() + ":");
            System.out.println("from:");
            System.out.println(speler);

            String name = nameInput.getText();
            int health = Integer.parseInt(healthInput.getText());


            speler.setAll(name, health, new Gun(),new Vest());

            System.out.println("To:");
            System.out.println(speler);

            //updates the value in the Speler options Combobox
            SpelerOptionsComboBox.getItems().set(SpelerOptionsComboBox.getSelectionModel().getSelectedIndex(), speler);
        });
        inputsColumnBox.getChildren().add(updateScheduleItemButton);
        //#endregion


        mainCreateSpelerBox.getChildren().add(contentRowBox);

        return mainCreateSpelerBox;
    }


}
