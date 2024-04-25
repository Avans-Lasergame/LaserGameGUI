package Gui;

import Gui.Server.ServerGUI;
import Objects.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;

public class GUI extends Application {
    private static Game game = new Game();
    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<Gun> guns = new ArrayList<>();
    private static ArrayList<Team> teams = new ArrayList<>();
    private static Tab previousTab;
    // Tabs:
    private static Tab playerCRUD;
    private static Tab teamCRUD;
    private static Tab gameCRUD;
    public static void main(String[] args) {
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        generatePlayerTestData();
        generateTeamTestData();

        stage.setTitle("Laser_game GUI");
        TabPane tabpane = new TabPane();
        tabpane.getSelectionModel().selectedItemProperty().addListener(
                (ov, t, t1) -> handleTab()
        );

        playerCRUD = new Tab("Player");
        HBox playerBox = new HBox();
        playerBox.getChildren().addAll(PlayerCRUD.getComponent());
        playerCRUD.setContent(playerBox);

        teamCRUD = new Tab("Team");
        HBox teamBox = new HBox();
        teamBox.getChildren().addAll(TeamCRUD.getComponent());
        teamCRUD.setContent(teamBox);

        gameCRUD = new Tab("Game");
        HBox gameBox = new HBox();
        gameBox.getChildren().addAll(GameCRUD.getComponent());
        gameCRUD.setContent(gameBox);
        Tab serverTab = new Tab("Server");
        serverTab.setContent(ServerGUI.getComponent());
        tabpane.getTabs().addAll(playerCRUD, teamCRUD, gameCRUD,serverTab);
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

            System.out.println("Data updated!");
        } else if (previousTab == teamCRUD){
            // Update Players and Teams data
            players = TeamCRUD.getPlayers();
            teams = TeamCRUD.getTeams();
            PlayerCRUD.updateData();
            GameCRUD.updateData();

            System.out.println("Data updated!");
        } else if (previousTab == gameCRUD){
            // Update Game data
            game = GameCRUD.getGame();
            if (game != null){
                System.out.println("Game set!");
            } else {
                System.out.println("No game found!");
            }
            PlayerCRUD.updateData();
            TeamCRUD.updateData();
        }
    }

    public static ArrayList<Gun> getGuns() {
        return guns;
    }

    private void generatePlayerTestData(){
        // Testdata
        Player erik = new Player("Erik", 100,100, new Gun(-1), new Vest());
        Player storm = new Player("Storm", 95,100, new Gun(-1), new Vest());
        Player daan = new Player("Daan", 90,100, new Gun(-1), new Vest());
        players.add(erik);
        players.add(storm);
        players.add(daan);
    }

    private void generateTeamTestData(){
        Player erik = new Player("Erik", 100,100, new Gun(-1), new Vest());
        Player storm = new Player("Storm", 95,100, new Gun(-1), new Vest());
        Player daan = new Player("Daan", 90,100, new Gun(-1), new Vest());

        // Testdata
        Team team1 = new Team("Team 1", erik);
        team1.addPlayer(storm);
        Team team2 = new Team("Team 2", daan);
        teams.add(team1);
        teams.add(team2);
    }

}
