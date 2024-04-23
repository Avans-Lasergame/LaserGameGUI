package Gui;

import Objects.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameCRUD{
    private static Game game; //Game to be made
    private static ArrayList<Player> players = GUI.getPlayers(); //All Players
    private static ArrayList<Team> teams = GUI.getTeams(); //All Teams
    private static ObservableList<Player> selectedPlayers = FXCollections.observableArrayList();  //Selected Players for a game
    private static int maximumPlayerCount = 4;
    // Items:
    private static ComboBox selectPlayer = new ComboBox();
    private static ComboBox selectTeam1 = new ComboBox();
    private static ComboBox selectTeam2 = new ComboBox();
    public static VBox getComponent() {
        // General settings
        VBox gameCreateBox = new VBox(20);
        gameCreateBox.setPadding(new Insets(20));
        VBox firstRow = new VBox(20);
        VBox secondRow = new VBox(20);
        HBox columns = new HBox(100);

        //#region List of players TableView
        TableView<Player> playerTable = new TableView<>();
        playerTable.setPrefWidth(200);
        TableColumn<Player, String> playerColumn = new TableColumn("Selected Players");
        playerColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        playerColumn.setPrefWidth(playerTable.getPrefWidth());
        playerTable.getColumns().add(playerColumn);
        playerTable.setItems(selectedPlayers);
        //#endregion

        //#region Select GameMode ComboBox
        Label labelSelectGameType = new Label("Select game type:");
        ComboBox selectGameType = new ComboBox();
        selectGameType.setPrefWidth(200);
        List<String> selectionGameModes = new LinkedList<>();
        for (GameModes value : GameModes.values()){
            selectionGameModes.add(String.valueOf(value));
        }
        ObservableList<String> selectionList = FXCollections.observableList(selectionGameModes);
        selectGameType.setItems(selectionList);
        selectGameType.getSelectionModel().selectFirst();
        //#endregion

        //#region Select Player ComboBox
        Label labelSelectPlayer = new Label("Select Players:");
        selectPlayer.setPrefWidth(200);
        ObservableList<Player> playerList = FXCollections.observableArrayList(players);
        selectPlayer.setItems(playerList);
        selectPlayer.setConverter(new StringConverter<Player>() {
            @Override
            public String toString(Player player){
                return player.getName();
            }
            @Override
            public Player fromString(String string) {
                return (Player) selectPlayer.getItems().stream().filter(player ->
                        player.equals(string)).findFirst().orElse(null);
            }
        });
        selectPlayer.getSelectionModel().selectFirst();
        //#endregion

        //#region Select Team 1 ComboBox
        Label labelSelectTeam1 = new Label("Select first Team:");
        selectTeam1.setPrefWidth(200);
        ObservableList<Team> team1List = FXCollections.observableArrayList(teams);
        selectTeam1.setItems(team1List);
        selectTeam1.setConverter(new StringConverter<Team>() {
            @Override
            public String toString(Team team){
                return team.getTeamName();
            }
            @Override
            public Team fromString(String string) {
                return (Team) selectTeam1.getItems().stream().filter(player ->
                        player.equals(string)).findFirst().orElse(null);
            }
        });
        selectTeam1.getSelectionModel().selectFirst();
        selectTeam1.setOnAction(e -> {
            Object selectedGameMode = selectGameType.getSelectionModel().getSelectedItem();
            if (selectedGameMode == String.valueOf(GameModes.TeamDeathmatch)){
                selectedPlayers.clear();
            } 
//            else {
//                // Show alert
//                Alert errorPlayer = new Alert(Alert.AlertType.INFORMATION);
//                errorPlayer.setHeaderText("Error!");
//                errorPlayer.setContentText("You must change the GameMode to select Teams!");
//                errorPlayer.showAndWait();
//            }
        });
        //#endregion

        //#region Select Team 2 ComboBox
        Label labelSelectTeam2 = new Label("Select second Team:");
        selectTeam2.setPrefWidth(200);
        ObservableList<Team> team2List = FXCollections.observableArrayList(teams);
        selectTeam2.setItems(team2List);
        selectTeam2.setConverter(new StringConverter<Team>() {
            @Override
            public String toString(Team team){
                return team.getTeamName();
            }
            @Override
            public Team fromString(String string) {
                return (Team) selectTeam2.getItems().stream().filter(player ->
                        player.equals(string)).findFirst().orElse(null);
            }
        });
        selectTeam2.getSelectionModel().selectFirst();
        selectTeam2.setOnAction(e -> {
            Object selectedGameMode = selectGameType.getSelectionModel().getSelectedItem();
            if (selectedGameMode == String.valueOf(GameModes.TeamDeathmatch)){
                selectedPlayers.clear();
            }
//            else {
//                // Show alert
//                Alert errorPlayer = new Alert(Alert.AlertType.INFORMATION);
//                errorPlayer.setHeaderText("Error!");
//                errorPlayer.setContentText("You must change the GameMode to select Teams!");
//                errorPlayer.showAndWait();
//            }
        });
        //#endregion

        // Action items:
        //#region Add Player Button
        Button buttonAddPlayer = new Button("Add Player to List");
        buttonAddPlayer.setOnAction(e -> {
            Object selectedGameMode = selectGameType.getSelectionModel().getSelectedItem();
            if (selectedGameMode == String.valueOf(GameModes.FreeForAll)){
                if (selectedPlayers.size() < maximumPlayerCount){
                    Player selectedPlayer = (Player) selectPlayer.getSelectionModel().getSelectedItem();
                    for (Player sPlayer : selectedPlayers){
                        if (sPlayer.getName().equalsIgnoreCase(selectedPlayer.getName())){
                            // Show alert
                            Alert errorPlayer = new Alert(Alert.AlertType.INFORMATION);
                            errorPlayer.setHeaderText("Error!");
                            errorPlayer.setContentText("Player is already in the list!");
                            errorPlayer.showAndWait();
                            return;
                        }
                    }
                    selectedPlayers.add(selectedPlayer);
                    // Show alert
                    Alert addedPlayer = new Alert(Alert.AlertType.INFORMATION);
                    addedPlayer.setHeaderText("Success!");
                    addedPlayer.setContentText(selectedPlayer.getName() + " was added to the list of selected players!");
                    addedPlayer.showAndWait();
                } else{
                    // Show alert
                    Alert errorPlayer = new Alert(Alert.AlertType.INFORMATION);
                    errorPlayer.setHeaderText("Error!");
                    errorPlayer.setContentText("Maximum amount of Players reached!");
                    errorPlayer.showAndWait();
                }
            } else {
                selectedPlayers.clear();
                // Show alert
                Alert errorPlayer = new Alert(Alert.AlertType.INFORMATION);
                errorPlayer.setHeaderText("Error!");
                errorPlayer.setContentText("You must change the GameMode to add individual Players!");
                errorPlayer.showAndWait();
            }
        });
        //#endregion

        //#region Remove Player Button
        Button buttonRemovePlayer = new Button("Remove last Player from List");
        buttonRemovePlayer.setOnAction(e -> {
            Object selectedGameMode = selectGameType.getSelectionModel().getSelectedItem();
            if (selectedGameMode == String.valueOf(GameModes.FreeForAll)){
                if (!selectedPlayers.isEmpty()){
                    selectedPlayers.remove(selectedPlayers.size() - 1);
                    // Show alert
                    Alert removedPlayer = new Alert(Alert.AlertType.INFORMATION);
                    removedPlayer.setHeaderText("Success!");
                    removedPlayer.setContentText("The last Player was removed from the list of selected players!");
                    removedPlayer.showAndWait();
                } else{
                    // Show alert
                    Alert errorPlayer = new Alert(Alert.AlertType.INFORMATION);
                    errorPlayer.setHeaderText("Error!");
                    errorPlayer.setContentText("There are no Players found in the list!");
                    errorPlayer.showAndWait();
                }
            } else {
                selectedPlayers.clear();
                // Show alert
                Alert errorPlayer = new Alert(Alert.AlertType.INFORMATION);
                errorPlayer.setHeaderText("Error!");
                errorPlayer.setContentText("You must change the GameMode to add individual Players!");
                errorPlayer.showAndWait();
            }
        });
        //#endregion

        //#region Create Game Button
        Button buttonCreateGame = new Button("Create Game");
        buttonCreateGame.setOnAction(e -> {
            Object selectedGameMode = selectGameType.getSelectionModel().getSelectedItem();
            if (selectedGameMode == String.valueOf(GameModes.FreeForAll)){
                // Free For All
                if (selectedPlayers.size() > 1){
                    game = new Game(GameModes.FreeForAll, selectedPlayers);
                    // Show alert
                    Alert createdGame = new Alert(Alert.AlertType.INFORMATION);
                    createdGame.setHeaderText("Success!");
                    createdGame.setContentText("Game created!");
                    createdGame.showAndWait();
                } else {
                    // Show alert
                    Alert errorGame = new Alert(Alert.AlertType.INFORMATION);
                    errorGame.setHeaderText("Error!");
                    errorGame.setContentText("There must be at least 2 Players for this game!");
                    errorGame.showAndWait();
                }
            } else if (selectedGameMode == String.valueOf(GameModes.TeamDeathmatch)){
                // Team Deathmatch
                Team team1 = (Team) selectTeam1.getSelectionModel().getSelectedItem();
                Team team2 = (Team) selectTeam2.getSelectionModel().getSelectedItem();
                if (!team1.equals(null) && !team2.equals(null) && team1 != team2){
                    game = new Game(GameModes.TeamDeathmatch, team1, team2);
                    // Show alert
                    Alert createdGame = new Alert(Alert.AlertType.INFORMATION);
                    createdGame.setHeaderText("Success!");
                    createdGame.setContentText("Game created!");
                    createdGame.showAndWait();
                } else {
                    // Show alert
                    Alert errorGame = new Alert(Alert.AlertType.INFORMATION);
                    errorGame.setHeaderText("Error!");
                    errorGame.setContentText("There is at least 1 Team missing for this game!");
                    errorGame.showAndWait();
                }
            }
        });
        //#endregion

        firstRow.getChildren().addAll(labelSelectGameType, selectGameType, labelSelectPlayer, selectPlayer,
                buttonAddPlayer, playerTable, buttonRemovePlayer);
        secondRow.getChildren().addAll(labelSelectTeam1, selectTeam1, labelSelectTeam2, selectTeam2, buttonCreateGame);
        columns.getChildren().addAll(firstRow, secondRow);
        gameCreateBox.getChildren().addAll(columns);
        return gameCreateBox;
    }

    public static void updateData(){
        players = GUI.getPlayers();
        teams = GUI.getTeams();

        // selectPlayer
        ObservableList<Player> playerList = FXCollections.observableArrayList(players);
        selectPlayer.setItems(playerList);

        // selectTeam1
        ObservableList<Team> team1List = FXCollections.observableArrayList(teams);
        selectTeam1.setItems(team1List);

        ObservableList<Team> team2List = FXCollections.observableArrayList(teams);
        selectTeam2.setItems(team2List);
    }

    public static Game getGame(){
        return game;
    }
}
