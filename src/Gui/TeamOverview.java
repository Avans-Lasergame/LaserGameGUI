package Gui;

import Objects.Player;
import Objects.Team;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TeamOverview{
    private static ArrayList<Player> players = GUI.getPlayers(); //All Players
    private static ArrayList<Team> teams = GUI.getTeams(); //All Teams

    public static VBox getComponent() {
        // General settings
        VBox teamOverviewBox = new VBox(20);
        teamOverviewBox.setPadding(new Insets(20));


//        teamOverviewBox.getChildren().addAll();
        return teamOverviewBox;
    }

    public static ArrayList<Player> getPlayers(){
        return players;
    }

    public static ArrayList<Team> getTeams(){
        return teams;
    }
}
