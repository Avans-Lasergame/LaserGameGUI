package Gui;

import Objects.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;

public class GUI extends Application {
    private static Game game = new Game();
    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<Team> teams = new ArrayList<>();
    private static TabPane tabpane = new TabPane();
    // Tabs:
    private static Tab previousTab;
    private static Tab playerCRUD;
    private static Tab teamCRUD;
    private static Tab gameCRUD;
    private static Tab gameOverviewTab;
    public static void main(String[] args) {
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        generatePlayerTestData();
        generateTeamTestData();

        stage.setTitle("Laser_game GUI");
        tabpane.getSelectionModel().selectedItemProperty().addListener(
                (ov, t, t1) -> handleTab()
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
        gameOverviewTab = new Tab("Game Overview");
        HBox gameOverviewBox = new HBox(500);
        //gameOverviewBox.getChildren().addAll(GameOverview.getComponent());
        gameOverviewTab.setContent(gameOverviewBox);
        // Handle with its own event, because we don't have to update data
        gameOverviewTab.setOnSelectionChanged(event -> {
            game = GameCRUD.getGame();
            if (GUI.getGame() == null || !GUI.getGame().isGameRunning()){
                tabpane.getSelectionModel().select(previousTab);
                // Show alert
                Alert errorGame = new Alert(Alert.AlertType.INFORMATION);
                errorGame.setHeaderText("Error!");
                errorGame.setContentText("You must create or start a Game before you can view it!");
                errorGame.showAndWait();
            } else {
                gameOverviewBox.getChildren().clear();
                VBox team1Box = new VBox(30);
                VBox middleBox = new VBox(30);
                VBox team2Box = new VBox(30);

                // If game has Players (FFA), put them in the middle
                if (game.getPlayers() != null){
                    for (Player player : game.getPlayers().values()){
                        Label labelPlayer = new Label(player.getName());
                        middleBox.getChildren().add(labelPlayer);
                    }
                } else {
                    // If game has no Players (TDM), get the Players form the 2 Teams
                    VBox theBox = team1Box;
                    for (Team team : game.getTeams().values()){
                        for (Player player : team.getPlayers()){
                            Label labelPlayer = new Label(player.getName());
                            theBox.getChildren().add(labelPlayer);
                        }
                        theBox = team2Box;
                    }
                }
                gameOverviewBox.getChildren().addAll(team1Box, middleBox, team2Box);
            }
        });
        //#endregion

        tabpane.getTabs().addAll(playerCRUD, teamCRUD, gameCRUD, gameOverviewTab);
        tabpane.setTabClosingPolicy(UNAVAILABLE);
        previousTab = tabpane.getTabs().get(0);

        Scene scene = new Scene(tabpane, 1200, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static Game getGame(){
        return game;
    }

    public static ArrayList<Player> getPlayers(){
        return players;
    }

    public static ArrayList<Team> getTeams(){
        return teams;
    }

    public void handleTab() {
        if (playerCRUD.isSelected()){
            previousTab = playerCRUD;
        } else if (teamCRUD.isSelected()){
            previousTab = teamCRUD;
        } else if (gameCRUD.isSelected()){
            previousTab = gameCRUD;
        }

        if (previousTab == playerCRUD){
            // Update Players data
            players = PlayerCRUD.getPlayers();
            TeamCRUD.updateData();
            GameCRUD.updateData();
            System.out.println("players updated!");
        } else if (previousTab == teamCRUD){
            // Update Players and Teams data
            players = TeamCRUD.getPlayers();
            teams = TeamCRUD.getTeams();
            PlayerCRUD.updateData();
            GameCRUD.updateData();
            System.out.println("Teams updated!");
        }
    }

    private void generatePlayerTestData(){
        // Testdata
        Player erik = new Player("Erik", 100,100, new Gun(), new Vest());
        Player storm = new Player("Storm", 95,100, new Gun(), new Vest());
        Player daan = new Player("Daan", 90,100, new Gun(), new Vest());
        players.add(erik);
        players.add(storm);
        players.add(daan);
    }

    private void generateTeamTestData(){
        Player erik = new Player("Erik", 100,100, new Gun(), new Vest());
        Player storm = new Player("Storm", 95,100, new Gun(), new Vest());
        Player daan = new Player("Daan", 90,100, new Gun(), new Vest());

        // Testdata
        Team team1 = new Team("Team 1", erik);
        team1.addPlayer(storm);
        Team team2 = new Team("Team 2", daan);
        teams.add(team1);
        teams.add(team2);
    }

}
