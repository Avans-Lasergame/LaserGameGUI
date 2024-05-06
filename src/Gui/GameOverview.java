package Gui;

import Objects.Game;
import Objects.Player;
import Objects.Team;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameOverview {
    private static final Game game = GUI.getGame();
    private static boolean freeForAllEnabled;
    public static VBox getComponent() {
        VBox gameOverviewBox = new VBox();

        // Check if there is a game
        if (game.getPlayers().isEmpty()){
            freeForAllEnabled = false;
        } else if (game.getTeams().isEmpty()){
            freeForAllEnabled = true;
        } else {
            // Show alert
            Alert errorGame = new Alert(Alert.AlertType.INFORMATION);
            errorGame.setHeaderText("Error!");
            errorGame.setContentText("There are no Teams and no Players!");
            errorGame.showAndWait();
            return null;
        }

        //gameOverviewBox.getChildren().addAll(team1Box, middleBox, team2Box);
        return gameOverviewBox;
    }
}
