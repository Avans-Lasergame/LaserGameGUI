package Gui.SpelerCRUD;

import Gui.GUI;
import Objects.Game;
import Objects.Gun;
import Objects.Player;
import Objects.Vest;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SpelerCreate {

    public static VBox getComponent() {
        Game game = GUI.getGame();
        VBox mainCreateSpelerBox = new VBox(20);
        mainCreateSpelerBox.setPadding(new Insets(20));

        Label forumTypeLabel = new Label("Create new Speler:");
        mainCreateSpelerBox.getChildren().add(forumTypeLabel);

        HBox contentRowBox = new HBox(10);
        VBox labelColumnBox = new VBox(22);
        VBox inputsColumnBox = new VBox(10);

        contentRowBox.getChildren().addAll(labelColumnBox, inputsColumnBox);

        //#region Name TextField
        Label nameInputLabel = new Label("Name: ");
        labelColumnBox.getChildren().add(nameInputLabel);

        TextField nameInput = new TextField();
        inputsColumnBox.getChildren().add(nameInput);
        //#endregion

        //#region Health TextField
        Label healthInputLabel = new Label("Health: ");
        labelColumnBox.getChildren().add(healthInputLabel);

        TextField healthInput = new TextField();
        inputsColumnBox.getChildren().add(healthInput);
        //#endregion

        //#region MaxHealth TextField
        Label maxHealthInputLabel = new Label("MaxHealth: ");
        labelColumnBox.getChildren().add(maxHealthInputLabel);

        TextField maxHealthInput = new TextField();
        inputsColumnBox.getChildren().add(maxHealthInput);
        //#endregion


        //#region Submit Button
        Button createSpelerButton = new Button("Create Speler");
        createSpelerButton.setOnAction(e -> {

            String name = nameInput.getText();
            int health = Integer.parseInt(healthInput.getText());
            int maxHealth = Integer.parseInt(maxHealthInput.getText());

            Player newItem = new Player(name, health, maxHealth, new Gun(-1), new Vest()); // TODO: 04/04/2024 maak hier vest en gun goed aan
            game.addSpeler(newItem);

            System.out.println("created Speler: " + newItem);
        });

        inputsColumnBox.getChildren().add(createSpelerButton);
        //#endregion

        mainCreateSpelerBox.getChildren().add(contentRowBox);

        return mainCreateSpelerBox;
    }

}
