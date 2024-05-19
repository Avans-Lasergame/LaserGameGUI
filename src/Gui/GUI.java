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
import java.util.concurrent.CopyOnWriteArrayList;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;

public class GUI extends Application {
    private static Game game = new Game();
    private static ArrayList<Player> players = new ArrayList<>();
    private static CopyOnWriteArrayList<Gun> guns = new CopyOnWriteArrayList<>();
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
//        generatePlayerTestData();
//        generateTeamTestData();

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
        gameOverviewTab.setContent(GameOverview.getComponent());
        // Handle with its own event, because we don't have to update data
        gameOverviewTab.setOnSelectionChanged(event -> {
            if (GUI.getGame() == null || !GUI.getGame().isGameRunning()) {
                tabpane.getSelectionModel().select(gameCRUD);
                // Show alert
                Alert errorGame = new Alert(Alert.AlertType.INFORMATION);
                errorGame.setHeaderText("Error!");
                errorGame.setContentText("You must create or start a Game before you can view it!");
                errorGame.showAndWait();
            } else{
                GameOverview.updateOverview();
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
//            System.out.println("Players updated!");
        } else if (previousTab == teamCRUD) {
            // Update Players and Teams data
            players = TeamCRUD.getPlayers();
            teams = TeamCRUD.getTeams();
            PlayerCRUD.updateData();
            GameCRUD.updateData();
//            System.out.println("Teams updated!");

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
//            System.out.println("Data updated! from serverTab");
        }
    }

    public static CopyOnWriteArrayList<Gun> getGuns() {
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
