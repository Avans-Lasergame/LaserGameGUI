package Gui;

import Objects.Game;
import Objects.Gun;
import Objects.Player;
import Objects.Vest;
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
    public static void main(String[] args) {
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Testdata
        players.add(new Player("Daan", 100, new Gun(), new Vest()));
        players.add(new Player("Storm", 90, new Gun(), new Vest()));
        players.add(new Player("Erik", 80, new Gun(), new Vest()));
        // Uncomment for real data
        //players = GUI.getPlayers();

        stage.setTitle("Laser_game GUI");
        TabPane tabpane = new TabPane();

        Tab playerCRUD = new Tab("Player");
        HBox playerBox = new HBox();
        playerBox.getChildren().addAll(SpelerCreate.getComponent(), SpelerUpdate.getComponent(), SpelerOverview.getComponent());
        playerCRUD.setContent(playerBox);

        Tab gameCRUD = new Tab("Game");
        HBox gameBox = new HBox();
        gameBox.getChildren().addAll(GameCreate.getComponent());
        gameCRUD.setContent(gameBox);


        tabpane.getTabs().addAll(playerCRUD, gameCRUD);
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
}
