package Gui;

import Objects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameCreate{
    private static ArrayList<Player> players = GUI.getPlayers();
    public static VBox getComponent() {
        VBox gameCreateBox = new VBox(20);
        gameCreateBox.setPadding(new Insets(20));

        //#region Select gamemode
        ComboBox selectGameType = new ComboBox();
        List<String> selectionGameModes = new LinkedList<>();
        for (GameModes value : GameModes.values()){
            selectionGameModes.add(String.valueOf(value));
        }
        ObservableList<String> selectionList = FXCollections.observableList(selectionGameModes);
        selectGameType.setItems(selectionList);
        selectGameType.getSelectionModel().selectFirst();
        //#endregion

        //#region Select player
        ComboBox selectPlayer = new ComboBox();
        List<String> playerNames = new LinkedList<>();
        for (Player player : players){
            playerNames.add(player.getName());
        }
        ObservableList<String> playerList = FXCollections.observableList(playerNames);
        selectPlayer.setItems(playerList);
        selectPlayer.getSelectionModel().selectFirst();
        //#endregion

        //#region List of players
        TableView<Player> playerTable = new TableView<>();
        if (!players.isEmpty()){
            //ObservableList<Player> playerList = FXCollections.observableList(players);
            //playerTable.setItems(playerList);

            TableColumn playerColumn = new TableColumn("Players");
            playerColumn.setCellValueFactory(new PropertyValueFactory<>("player"));
        }
//        TableView playerTable = new TableView();
//        TableColumn<Player, Object> allPlayerColumn = new TableColumn<>("All Players");
//        //TableColumn<Player, Object> selectedPlayerColumn = new TableColumn<>("Selected Players");
//        playerTable.getColumns().addAll(allPlayerColumn);
//
//        if (!players.isEmpty()){
//            ObservableList<Player> playerList = FXCollections.observableList(players);
//            playerTable.setItems(playerList);
//        }
        //#endregion


        gameCreateBox.getChildren().addAll(selectGameType, selectPlayer, playerTable);
        return gameCreateBox;
    }
}
