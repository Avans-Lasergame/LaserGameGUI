package Application;

import Objects.Gun;
import Objects.Hub;
import Objects.Player;
import Objects.Vest;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GUI extends Application{

    private ArrayList<Player> players;
    public static void main(String[] args) {
        launch(GUI.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        generatePlayers();

        BorderPane mainPane = new BorderPane();

        Scene mainScene = new Scene(mainPane);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Lasergame GUI");
        primaryStage.show();
    }

    public void generatePlayers(){
        players = new ArrayList<>();

        // Create guns & Vests
        Player daan = new Player(new Hub(new Gun(), new Vest()), "Daan");
        players.add(daan);
        Player erik = new Player(new Hub(new Gun(), new Vest()), "Daan");
        players.add(erik);
        Player storm = new Player(new Hub(new Gun(), new Vest()), "Daan");
        players.add(storm);

        daan.getPlayerHub().getGun().shootGun();
        System.out.println(erik.getPlayerHealth());
        erik.getPlayerHub().getVest().getsHit();
        System.out.println(erik.getPlayerHealth());

    }
}
