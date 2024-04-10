package Gui;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class GameUpdate{
    public static VBox getComponent() {
        // General settings
        VBox gameUpdateBox = new VBox(20);
        gameUpdateBox.setPadding(new Insets(20));


//        gameUpdateBox.getChildren().addAll();
        return gameUpdateBox;
    }
}
