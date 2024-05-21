package Gui;

import Objects.Game;
import Objects.GameModes;
import Objects.Player;
import Objects.Team;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameOverview {
    private static Game game = GUI.getGame();
    private static StackPane holder = new StackPane();
    private static FXGraphics2D g;
    private static Image original = new Image(GameOverview.class.getResourceAsStream("/backgroundImage.png"));
    private static BufferedImage newImage = SwingFXUtils.fromFXImage(original, null);

    // 1920x1080 offset values:
    private static int imgX = -360; // Offset value
    private static int imgY = -300; // Offset value
    private static boolean startingUp = true;
    public static StackPane getComponent() {
        ResizableCanvas canvas = new ResizableCanvas(g -> draw(g), holder);
        g = new FXGraphics2D(canvas.getGraphicsContext2D());
        canvas.setHeight(1920);
        canvas.setWidth(1080);

        // StackPane events:
        holder.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (startingUp)
                return;
            // Change width of Canvas
            double prevValue = (double) oldVal;
            double newValue = (double) newVal;
            if (prevValue < newValue){
                imgX-= (int) ((prevValue-newValue)/2);
            } else if (prevValue > newValue){
                imgX-= (int) ((prevValue-newValue)/2);
            }
            canvas.setWidth(imgX);
        });
        holder.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (startingUp)
                return;
            // Change height of Canvas
            double prevValue = (double) oldVal;
            double newValue = (double) newVal;
            if (prevValue < newValue){
                imgY-= (int) ((prevValue-newValue)/2);
            } else if (prevValue > newValue){
                imgY-= (int) ((prevValue-newValue)/2);
            }
            canvas.setHeight(imgY);
        });
        holder.getChildren().addAll(canvas);

        // Timer
        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
                if (last == -1) {
                    last = now;
                }
                update((now - last) / 1000000000.0);
                last = now;
                draw(g);
            }
        }.start();
        return holder;
    }

    private static void draw(FXGraphics2D g) {
        // Colors:
        Color white = new Color(255, 255, 255);
        Color black = new Color(0, 0, 0);
        Color gray = new Color(173, 173, 173);
        Color purple = new Color(185, 0, 230);
        Color red = new Color(230, 0, 0);
        Color blue = new Color(0, 0, 230);
        Color green = new Color(0, 230, 0);

        // Fonts:
        Font plain = new Font("", Font.PLAIN, 30);

        // Make image transparent
        AlphaComposite ac1 = java.awt.AlphaComposite.getInstance(AlphaComposite.CLEAR,1F);
        g.setComposite(ac1);
        g.drawImage(newImage, imgX, imgY, null);
        // Back to front
        AlphaComposite ac2 = java.awt.AlphaComposite.getInstance(AlphaComposite.DST_OVER,1F);
        g.setComposite(ac2);

        // Draw individual items
        g.setFont(plain);
        g.setColor(white);
        if (game.getGameMode() == GameModes.FreeForAll){
            // GameMode
            g.fillRect((imgX+755), (imgY+300), 320, 35);
            g.setColor(black);
            g.drawString("Gamemode: " + game.getGameMode(), (imgX+760), (imgY+330));

            // Players
            g.setColor(gray);
            g.fillRect((imgX+365), (imgY+400), 310, 35);
            g.setColor(black);
            g.drawString("Players: ", (imgX+370), (imgY+425));
            int plusY = 0;
            for (Player player : game.getPlayers().values()){
                g.setColor(purple);
                g.fillRect((imgX+365), (imgY+465)+plusY, 310, 35);
                g.setColor(black);
                g.drawString(player.getName(), (imgX+370), (imgY+490)+plusY);
                // Display health
                int health = player.getHealth();
                int maxHealth = player.getMaxHealth();
                double percentage = (double) 100 /maxHealth*health;
                g.fillRect((imgX+365), (imgY+500)+plusY, 310, 35);
                g.setColor(green);
                g.fillRect((imgX+370), (imgY+505)+plusY, (int) (3*percentage), 25);

                plusY+=75;
            }
        } else if (game.getGameMode() == GameModes.TeamDeathmatch){
            // GameMode
            g.fillRect((imgX+705), (imgY+295), 420, 35);
            g.setColor(black);
            g.drawString("Gamemode: " + game.getGameMode(), (imgX+710), (imgY+325));

            // Teams
            int plusX = 0;
            int plusY = 0;
            Color theColor = red;
            Color theTextColor = black;
            for (Team team : game.getTeams().values()){
                g.setColor(gray);
                g.fillRect((imgX+350)+plusX, (imgY+395)+plusY, 420, 35);
                g.setColor(black);
                g.drawString("Team: " + team.getTeamName(), (imgX+355)+plusX, (imgY+425)+plusY);
                for (Player player : team.getPlayers()){
                    g.setColor(theColor);
                    g.fillRect((imgX+400)+plusX, (imgY+475)+plusY, 310, 35);
                    g.setColor(theTextColor);
                    g.drawString(player.getName(), (imgX+405)+plusX, (imgY+500)+plusY);
                    // Display health
                    int health = player.getHealth();
                    int maxHealth = player.getMaxHealth();
                    double percentage = (double) 100 /maxHealth*health;
                    g.setColor(black);
                    g.fillRect((imgX+400)+plusX, (imgY+510)+plusY, 310, 35);
                    g.setColor(green);
                    g.fillRect((imgX+405)+plusX, (imgY+515)+plusY, (int) (3*percentage), 25);
                    plusY+=75;
                }
                theColor = blue;
                theTextColor = white;
                plusX+=800;
                plusY = 0;
            }
        }
    }

    private static void update(double time){
        // When in this tab (not sure if needed)

    }

    public static void updateOverview(){
        // When switching tabs
        startingUp = false;
        game = GameCRUD.getGame();

        // Check if there is a game
        boolean freeForAllEnabled;
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
        }

        // USEFULL STUFF (DELETE LATER)
//        //#region Populate this tab with the Teams / Players of this Game
//        holder.getChildren().clear();
//        Font textSizeFont = new Font(30);
//        HBox gameOverviewBox = new HBox(350);
//        gameOverviewBox.setPadding(new Insets(40));
//        VBox team1Box = new VBox(5);
//        VBox middleBox = new VBox(5);
//        VBox team2Box = new VBox(5);
//        Label labelGameMode = new Label("Gamemode: " + game.getGameMode());
//        labelGameMode.setFont(new Font(40));
//        labelGameMode.setStyle("-fx-background-color: rgb(173, 173, 173)");
//        HBox centerBox = new HBox(labelGameMode);
//        centerBox.setPadding(new Insets(40));
//        // If game has Players (FFA), put them in the middle
//        if (!game.getPlayers().isEmpty()) {
//            Label labelPlayers = new Label("Players: ");
//            labelPlayers.setFont(textSizeFont);
//            labelPlayers.setStyle("-fx-background-color: rgb(185, 0, 230)");
//            middleBox.getChildren().add(labelPlayers);
//            for (Player player : game.getPlayers().values()) {
//                Label labelPlayer = new Label(player.getName());
//                labelPlayer.setFont(textSizeFont);
//                labelPlayer.setStyle("-fx-background-color: rgb(185, 0, 230)");
//                middleBox.getChildren().add(labelPlayer);
//            }
//        } else {
//            // If game has no Players (TDM), get the Players form the 2 Teams
//            VBox theBox = team1Box;
//            for (Team team : game.getTeams().values()) {
//                Label labelTeamName = new Label("Team: " + team.getTeamName());
//                labelTeamName.setFont(textSizeFont);
//                if (theBox == team1Box) {
//                    labelTeamName.setTextFill(Color.valueOf("black"));
//                    labelTeamName.setStyle("-fx-background-color: rgb(230, 0, 0)");
//                } else {
//                    labelTeamName.setTextFill(Color.valueOf("white"));
//                    labelTeamName.setStyle("-fx-background-color: rgb(0, 0, 230)");
//                }
//                theBox.getChildren().add(labelTeamName);
//                for (Player player : team.getPlayers()) {
//                    Label labelPlayer = new Label(player.getName());
//                    labelPlayer.setFont(textSizeFont);
//                    if (theBox == team1Box) {
//                        labelPlayer.setTextFill(Color.valueOf("black"));
//                        labelPlayer.setStyle("-fx-background-color: rgb(230, 0, 0)");
//                    } else {
//                        labelPlayer.setTextFill(Color.valueOf("white"));
//                        labelPlayer.setStyle("-fx-background-color: rgb(0, 0, 230)");
//                    }
//                    theBox.getChildren().add(labelPlayer);
//                }
//                theBox = team2Box;
//            }
//        }
//        gameOverviewBox.getChildren().addAll(team1Box, middleBox, team2Box);
//        Label labelCredit1 = new Label("Neon Laser Tag Battle Wallpaper HD by robokoboto:");
//        Label labelCredit2 = new Label("https://alphacoders.com/users/profile/69089/robokoboto");
//        labelCredit1.setFont(new Font(12));
//        labelCredit1.setTextFill(Color.valueOf("white"));
//        labelCredit1.setStyle("-fx-background-color: rgb(0, 0, 0)");
//        labelCredit2.setFont(new Font(12));
//        labelCredit2.setTextFill(Color.valueOf("white"));
//        labelCredit2.setStyle("-fx-background-color: rgb(0, 0, 0)");
//        holder.getChildren().addAll(canvas, img);
//        //#endregion
    }
}
