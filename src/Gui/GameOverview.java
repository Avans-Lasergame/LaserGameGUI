package Gui;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class GameOverview{
    public static VBox getComponent() {
        // General settings
        VBox gameOverviewBox = new VBox(20);
        gameOverviewBox.setPadding(new Insets(20));

//        gameOverviewBox.getChildren().addAll();
        return gameOverviewBox;
    }
}
