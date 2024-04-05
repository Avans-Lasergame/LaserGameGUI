package Gui;

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
    private static ArrayList<Team> teams = new ArrayList<>();
    public static void main(String[] args) {
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        generatePlayerTestData();
        generateTeamTestData();

        stage.setTitle("Laser_game GUI");
        TabPane tabpane = new TabPane();

        Tab playerCRUD = new Tab("Player");
        HBox playerBox = new HBox();
        playerBox.getChildren().addAll(SpelerCreate.getComponent(), SpelerUpdate.getComponent(), SpelerOverview.getComponent());
        playerCRUD.setContent(playerBox);

        Tab teamCRUD = new Tab("Team");
        HBox teamBox = new HBox();
        teamBox.getChildren().addAll(TeamCreate.getComponent(), TeamUpdate.getComponent(), TeamOverview.getComponent());
        teamCRUD.setContent(teamBox);

        Tab gameCRUD = new Tab("Game");
        HBox gameBox = new HBox();
        gameBox.getChildren().addAll(GameCreate.getComponent(), GameUpdate.getComponent(), GameOverview.getComponent());
        gameCRUD.setContent(gameBox);


        tabpane.getTabs().addAll(playerCRUD, teamCRUD, gameCRUD);
        tabpane.setTabClosingPolicy(UNAVAILABLE);

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

    private void generatePlayerTestData(){
        // Testdata
        Player erik = new Player("Erik", 100, new Gun(), new Vest());
        Player storm = new Player("Storm", 95, new Gun(), new Vest());
        Player daan = new Player("Daan", 90, new Gun(), new Vest());
        players.add(erik);
        players.add(storm);
        players.add(daan);
    }

    private void generateTeamTestData(){
        Player erik = new Player("Erik", 100, new Gun(), new Vest());
        Player storm = new Player("Storm", 95, new Gun(), new Vest());
        Player daan = new Player("Daan", 90, new Gun(), new Vest());

        // Testdata
        Team team1 = new Team("Team 1", erik);
        team1.addPlayer(storm);
        Team team2 = new Team("Team 2", daan);
        teams.add(team1);
        teams.add(team2);
    }

}
