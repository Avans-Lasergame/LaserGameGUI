package Gui;

import Objects.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;

public class GUI extends Application {
    private static Game game = new Game();
    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<Gun> guns = new ArrayList<>();
    private static ArrayList<Team> teams = new ArrayList<>();
    private static final TabPane tabpane = new TabPane();
    private static Tab playerCRUD;
    private static Tab teamCRUD;
    private static Tab gameCRUD;
    private static Tab serverTab;

    public static void main(String[] args) {
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        generatePlayerTestData();
        generateTeamTestData();

        stage.setTitle("Laser_game GUI");
        tabpane.getSelectionModel().selectedItemProperty().addListener(
                (ov, t, t1) -> handleTab(t, t1)
        );

        //#region PlayerCRUD
        playerCRUD = new Tab("Player");
        HBox playerBox = new HBox();
        playerBox.getChildren().addAll(PlayerCRUD.getComponent());
        playerCRUD.setContent(playerBox);
        //#endregion

        //#region TeamCRUD
        teamCRUD = new Tab("Team");
        HBox teamBox = new HBox();
        teamBox.getChildren().addAll(TeamCRUD.getComponent());
        teamCRUD.setContent(teamBox);
        //#endregion

        //#region GameCRUD
        gameCRUD = new Tab("Game");
        HBox gameBox = new HBox();
        gameBox.getChildren().addAll(GameCRUD.getComponent());
        gameCRUD.setContent(gameBox);

        //#endregion

        //#region GameOverview
        Tab gameOverviewTab = new Tab("Game Overview");
        FlowPane pane = new FlowPane();

        Image image = new Image(String.valueOf(GUI.class.getResource("/backgroundImage.png")));
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

//        BackgroundFill outerBackgroundFill = new BackgroundFill(Color.rgb(103, 103, 103), new CornerRadii(0), new Insets(0));
//        BackgroundFill innerBackgroundFill = new BackgroundFill(Color.rgb(44, 245, 228), new CornerRadii(0), new Insets(15));
//        Background background = new Background(outerBackgroundFill, innerBackgroundFill);
        pane.setBackground(background);
        gameOverviewTab.setContent(pane);
        // Handle with its own event, because we don't have to update data
        gameOverviewTab.setOnSelectionChanged(event -> {
            game = GameCRUD.getGame();
            if (GUI.getGame() == null || !GUI.getGame().isGameRunning()) {
                tabpane.getSelectionModel().select(gameCRUD);
                // Show alert
                Alert errorGame = new Alert(Alert.AlertType.INFORMATION);
                errorGame.setHeaderText("Error!");
                errorGame.setContentText("You must create or start a Game before you can view it!");
                errorGame.showAndWait();
            } else {
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
        });
        //#endregion

        //#region Server Tab
        serverTab = new Tab("Server");
        serverTab.setContent(ServerGUI.getComponent());
        //#endregion

        tabpane.getTabs().addAll(playerCRUD, teamCRUD, gameCRUD, gameOverviewTab, serverTab);
        tabpane.setTabClosingPolicy(UNAVAILABLE);

        Scene scene = new Scene(tabpane, 1200, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static Game getGame() {
        return game;
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static ArrayList<Team> getTeams() {
        return teams;
    }

    public void handleTab(Tab previousTab, Tab current) {

        if (previousTab == playerCRUD) {
            // Update Players data
            players = PlayerCRUD.getPlayers();
            TeamCRUD.updateData();
            GameCRUD.updateData();

//            System.out.println("Data updated! from playerTab");
        } else if (previousTab == teamCRUD) {
            System.out.println("Players updated!");
        } else if (previousTab == teamCRUD) {
            // Update Players and Teams data
            players = TeamCRUD.getPlayers();
            teams = TeamCRUD.getTeams();
            PlayerCRUD.updateData();
            GameCRUD.updateData();
            System.out.println("Teams updated!");

//            System.out.println("Data updated! from teamTab");
        } else if (previousTab == gameCRUD) {
            // Update Game data
            game = GameCRUD.getGame();
            if (game != null) {
                System.out.println("Game set!");
            } else {
                System.out.println("No game found!");
            }
            PlayerCRUD.updateData();
            TeamCRUD.updateData();
        } else if (previousTab == serverTab) {
            // Update Game data
            GameCRUD.updateData();
            PlayerCRUD.updateData();
            TeamCRUD.updateData();
            System.out.println("Data updated! from serverTab");
        }
    }

    public static ArrayList<Gun> getGuns() {
        return guns;
    }

    private void generatePlayerTestData() {
        // Testdata
        Player erik = new Player("Erik", 100, 100, new Gun(-1));
        Player storm = new Player("Storm", 95, 100, new Gun(-1));
        Player daan = new Player("Daan", 90, 100, new Gun(-1));
        players.add(erik);
        players.add(storm);
        players.add(daan);
    }

    private void generateTeamTestData() {
        Player erik = new Player("Erik", 100, 100, new Gun(-1));
        Player storm = new Player("Storm", 95, 100, new Gun(-1));
        Player daan = new Player("Daan", 90, 100, new Gun(-1));

        // Testdata
        Team team1 = new Team("Team 1", erik);
        team1.addPlayer(storm);
        Team team2 = new Team("Team 2", daan);
        teams.add(team1);
        teams.add(team2);
    }

}
