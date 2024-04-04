package Gui;

import Objects.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;

public class GUI extends Application {
    private static Game game = new Game();
    public static void main(String[] args) {
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Laser_game GUI");

        TabPane tabpane = new TabPane();
        Tab attractionRead = new Tab("Spelers");
        HBox AttractionBox = new HBox();
        AttractionBox.getChildren().addAll(SpelerCreate.getComponent(), SpelerUpdate.getComponent(), SpelerOverview.getComponent());
        attractionRead.setContent(AttractionBox);


        tabpane.getTabs().addAll(attractionRead);
        tabpane.setTabClosingPolicy(UNAVAILABLE);

        Scene scene = new Scene(tabpane, 1200, 600);
        stage.setScene(scene);
        stage.show();
    }
    public static Game getGame(){
        return game;
    }
}
