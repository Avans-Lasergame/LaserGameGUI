package Gui;

import Objects.Game;
import Objects.Player;
import Objects.Team;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameOverview {
    private static Game game = GUI.getGame();
    private static boolean freeForAllEnabled;
    private static Canvas canvas;
    private static FlowPane pane = new FlowPane();
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

        Image image = new Image(String.valueOf(GUI.class.getResource("/backgroundImage.png")));
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        pane.setBackground(background);

        canvas = new Canvas(gameOverviewBox.getWidth(), gameOverviewBox.getHeight());
        gameOverviewBox.getChildren().addAll(pane, canvas);
        return gameOverviewBox;
    }

    public static void updateOverview(){
        game = GameCRUD.getGame();

//        BackgroundFill outerBackgroundFill = new BackgroundFill(Color.rgb(103, 103, 103), new CornerRadii(0), new Insets(0));
//        BackgroundFill innerBackgroundFill = new BackgroundFill(Color.rgb(44, 245, 228), new CornerRadii(0), new Insets(15));
//        Background background = new Background(outerBackgroundFill, innerBackgroundFill);

        //#region Populate this tab with the Teams / Players of this Game
        pane.getChildren().clear();
        Font textSizeFont = new Font(30);

        HBox gameOverviewBox = new HBox(350);
        gameOverviewBox.setPadding(new Insets(40));
        VBox team1Box = new VBox(5);
        VBox middleBox = new VBox(5);
        VBox team2Box = new VBox(5);

        Label labelGameMode = new Label("Gamemode: " + game.getGameMode());
        labelGameMode.setFont(new Font(40));
        labelGameMode.setStyle("-fx-background-color: rgb(173, 173, 173)");
        HBox centerBox = new HBox(labelGameMode);
        centerBox.setPadding(new Insets(40));

        // If game has Players (FFA), put them in the middle
        if (game.getPlayers() != null) {
            Label labelPlayers = new Label("Players: ");
            labelPlayers.setFont(textSizeFont);
            labelPlayers.setStyle("-fx-background-color: rgb(185, 0, 230)");
            middleBox.getChildren().add(labelPlayers);
            for (Player player : game.getPlayers().values()) {
                Label labelPlayer = new Label(player.getName());
                labelPlayer.setFont(textSizeFont);
                labelPlayer.setStyle("-fx-background-color: rgb(185, 0, 230)");
                middleBox.getChildren().add(labelPlayer);
            }
        } else {
            // If game has no Players (TDM), get the Players form the 2 Teams
            VBox theBox = team1Box;
            for (Team team : game.getTeams().values()) {
                Label labelTeamName = new Label("Team: " + team.getTeamName());
                labelTeamName.setFont(textSizeFont);
                if (theBox == team1Box) {
                    labelTeamName.setTextFill(Color.valueOf("black"));
                    labelTeamName.setStyle("-fx-background-color: rgb(230, 0, 0)");
                } else {
                    labelTeamName.setTextFill(Color.valueOf("white"));
                    labelTeamName.setStyle("-fx-background-color: rgb(0, 0, 230)");
                }
                theBox.getChildren().add(labelTeamName);
                for (Player player : team.getPlayers()) {
                    Label labelPlayer = new Label(player.getName());
                    labelPlayer.setFont(textSizeFont);
                    if (theBox == team1Box) {
                        labelPlayer.setTextFill(Color.valueOf("black"));
                        labelPlayer.setStyle("-fx-background-color: rgb(230, 0, 0)");
                    } else {
                        labelPlayer.setTextFill(Color.valueOf("white"));
                        labelPlayer.setStyle("-fx-background-color: rgb(0, 0, 230)");
                    }
                    theBox.getChildren().add(labelPlayer);
                }
                theBox = team2Box;
            }
        }
        gameOverviewBox.getChildren().addAll(team1Box, middleBox, team2Box);

        Label labelCredit1 = new Label("Neon Laser Tag Battle Wallpaper HD by robokoboto:");
        Label labelCredit2 = new Label("https://alphacoders.com/users/profile/69089/robokoboto");
        labelCredit1.setFont(new Font(12));
        labelCredit1.setTextFill(Color.valueOf("white"));
        labelCredit1.setStyle("-fx-background-color: rgb(0, 0, 0)");
        labelCredit2.setFont(new Font(12));
        labelCredit2.setTextFill(Color.valueOf("white"));
        labelCredit2.setStyle("-fx-background-color: rgb(0, 0, 0)");

        VBox finalBox = new VBox(centerBox, gameOverviewBox, labelCredit1, labelCredit2);
        pane.getChildren().addAll(finalBox);
        //#endregion
    }
}
