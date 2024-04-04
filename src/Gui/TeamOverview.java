package Gui;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class TeamOverview{
    public static VBox getComponent() {
        // General settings
        VBox teamOverviewBox = new VBox(20);
        teamOverviewBox.setPadding(new Insets(20));


//        teamOverviewBox.getChildren().addAll();
        return teamOverviewBox;
    }
}
