package Gui;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class TeamCreate{
    public static VBox getComponent() {
        // General settings
        VBox teamCreateBox = new VBox(20);
        teamCreateBox.setPadding(new Insets(20));


//        teamCreateBox.getChildren().addAll();
        return teamCreateBox;
    }
}
