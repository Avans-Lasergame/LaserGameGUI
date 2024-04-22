package Gui;

import Gui.GUI;
import Objects.GameModes;
import Objects.Player;
import Objects.Team;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.ArrayList;

public class TeamCRUD{
    private static ArrayList<Player> players = GUI.getPlayers(); //All Players
    private static ArrayList<Team> teams = GUI.getTeams(); //All Teams
    private static ObservableList<Team> selectableTeams = FXCollections.observableArrayList(); //Selectable Teams
    private static ObservableList<Player> selectedPlayers = FXCollections.observableArrayList();  //Selected Players for a Team
    private static int maximumPlayersInTeam = 5;
    public static VBox getComponent() {
        // General settings
        VBox teamCrudBox = new VBox(20);
        teamCrudBox.setPadding(new Insets(20));
        VBox firstRow = new VBox(20);
        VBox secondRow = new VBox(20);
        VBox thirdRow = new VBox(20);
        HBox columns = new HBox(100);
        // Fill starting list of Teams
        for (Team team : teams){
           selectableTeams.add(team);
        }

        //#region Create new Team
        Label labelCreateTeam = new Label("Create new Team:");
        TextField createTeam = new TextField();
        ComboBox selectTeamCaptain = new ComboBox();
        ObservableList<Player> allPlayers = FXCollections.observableArrayList(players);
        selectTeamCaptain.setItems(allPlayers);
        selectTeamCaptain.setConverter(new StringConverter<Player>() {
            @Override
            public String toString(Player player){
                return player.getName();
            }
            @Override
            public Player fromString(String string) {
                return (Player) selectTeamCaptain.getItems().stream().filter(player ->
                        player.equals(string)).findFirst().orElse(null);
            }
        });
        selectTeamCaptain.getSelectionModel().selectFirst();
        //#endregion

        //#region List of players TableView
        TableView<Player> playerTable = new TableView<>();
        playerTable.setPrefWidth(200);
        TableColumn<Player, String> playerColumn = new TableColumn("Selected Players");
        playerColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        playerColumn.setPrefWidth(playerTable.getPrefWidth());
        playerTable.getColumns().add(playerColumn);
        playerTable.setItems(selectedPlayers);
        //#endregion

        //#region Select Team ComboBox
        Label labelSelectTeam = new Label("Select a Team:");
        ComboBox selectTeam = new ComboBox();
        selectTeam.setPrefWidth(200);
        ObservableList<Team> teamsList = FXCollections.observableList(selectableTeams);
        selectTeam.setItems(teamsList);
        selectTeam.setConverter(new StringConverter<Team>() {
            @Override
            public String toString(Team team){
                return team.getTeamName();
            }
            @Override
            public Team fromString(String string) {
                return (Team) selectTeam.getItems().stream().filter(team ->
                        team.equals(string)).findFirst().orElse(null);
            }
        });
        selectTeam.setOnAction(event -> {
            selectedPlayers.clear();
            try {
                Team theTeam = (Team) selectTeam.getSelectionModel().getSelectedItem();
                if (selectTeam.getSelectionModel().getSelectedItem().equals(theTeam)){
                    for (Player player : theTeam.getPlayers()){
                        selectedPlayers.add(player);
                    }
                }
            } catch (NullPointerException npe){} //Catch is alleen voor als het eerste team wordt verwijderd
        });
        //#endregion

        //#region Select Player ComboBox
        Label labelSelectPlayer = new Label("Select Players:");
        ComboBox selectPlayer = new ComboBox();
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

        // Action items:
        //#region Create Team Button
        Button buttonCreateTeam = new Button("Create Team");
        buttonCreateTeam.setOnAction(e -> {
            if (!createTeam.getText().equalsIgnoreCase("")){
                Player selectedPlayer = (Player) selectTeamCaptain.getSelectionModel().getSelectedItem();
                Team newTeam = new Team(createTeam.getText(), selectedPlayer);
                selectTeam.getItems().add(newTeam);
                teams.add(newTeam);

                // Show alert
                Alert addedTeam = new Alert(Alert.AlertType.INFORMATION);
                addedTeam.setHeaderText("Success!");
                addedTeam.setContentText(newTeam.getTeamName() + " was created! ");
                addedTeam.showAndWait();

                // Empty textfield
                createTeam.setText("");
            } else {
                // Show alert
                Alert errorTeam = new Alert(Alert.AlertType.INFORMATION);
                errorTeam.setHeaderText("Error!");
                errorTeam.setContentText("Team name cannot be empty!");
                errorTeam.showAndWait();
            }
        });
        //#endregion

        //#region Add Player Button
        Button buttonAddPlayer = new Button("Add Player to Team");
        buttonAddPlayer.setOnAction(e -> {
            Team selectedTeam = (Team) selectTeam.getSelectionModel().getSelectedItem();
            if (selectedTeam == null){
                // Show alert
                Alert errorPlayer = new Alert(Alert.AlertType.INFORMATION);
                errorPlayer.setHeaderText("Error!");
                errorPlayer.setContentText("There was no Team selected!");
                errorPlayer.showAndWait();
                return;
            }
            if (!selectedTeam.getTeamName().equalsIgnoreCase("")){
                if (selectedPlayers.size() < maximumPlayersInTeam){
                    Player selectedPlayer = (Player) selectPlayer.getSelectionModel().getSelectedItem();
                    boolean playerAlreadyAdded = false;
                    for (Player player : selectedTeam.getPlayers()){
                        if (player.getName().equalsIgnoreCase(selectedPlayer.getName())){
                            playerAlreadyAdded = true;
                        }
                    }
                    if (!playerAlreadyAdded){
                        selectedPlayers.add(selectedPlayer);
                        selectedTeam.addPlayer(selectedPlayer);

                        // Show alert
                        Alert addedPlayer = new Alert(Alert.AlertType.INFORMATION);
                        addedPlayer.setHeaderText("Success!");
                        addedPlayer.setContentText(selectedPlayer.getName() + " was added to: " + selectedTeam.getTeamName());
                        addedPlayer.showAndWait();
                    } else {
                        // Show alert
                        Alert errorPlayer = new Alert(Alert.AlertType.INFORMATION);
                        errorPlayer.setHeaderText("Error!");
                        errorPlayer.setContentText(selectedPlayer.getName() + " was already added to: " + selectedTeam.getTeamName());
                        errorPlayer.showAndWait();
                    }
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
                errorPlayer.setContentText("You must have a team name to add Players to it!");
                errorPlayer.showAndWait();
            }
        });
        //#endregion

        //#region Remove Player Button
        Button buttonRemovePlayer = new Button("Remove last Player from List");
        buttonRemovePlayer.setOnAction(e -> {
            Team selectedTeam = (Team) selectTeam.getSelectionModel().getSelectedItem();
            if (selectedTeam == null){
                // Show alert
                Alert errorPlayer = new Alert(Alert.AlertType.INFORMATION);
                errorPlayer.setHeaderText("Error!");
                errorPlayer.setContentText("There was no Team selected!");
                errorPlayer.showAndWait();
                return;
            }
            if (!selectedTeam.getTeamName().equalsIgnoreCase("")){
                if (!selectedPlayers.isEmpty()){
                    selectedTeam.removePlayer(playerTable.getItems().get(selectedPlayers.size()-1));
                    selectedPlayers.remove(selectedPlayers.size()-1);

                    // Show alert
                    Alert removedPlayer = new Alert(Alert.AlertType.INFORMATION);
                    removedPlayer.setHeaderText("Success!");
                    removedPlayer.setContentText("The last Player was removed from: " + selectedTeam.getTeamName());
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
                errorPlayer.setContentText("You must have a team name to add Players to it!");
                errorPlayer.showAndWait();
            }
        });
        //#endregion

        //#region Remove Team Button
        Button buttonRemoveTeam = new Button("Delete selected Team");
        buttonRemoveTeam.setOnAction(e -> {
            Team selectedTeam = (Team) selectTeam.getSelectionModel().getSelectedItem();
            if (selectedTeam == null){
                // Show alert
                Alert errorTeam = new Alert(Alert.AlertType.INFORMATION);
                errorTeam.setHeaderText("Error!");
                errorTeam.setContentText("There was no Team selected!");
                errorTeam.showAndWait();
                return;
            }
            if (!selectedTeam.getTeamName().equalsIgnoreCase("")){
                selectTeam.getItems().remove(selectedTeam);
                teams.remove(selectedTeam);
                selectTeam.getSelectionModel().selectFirst();

                // Show alert
                Alert removedTeam = new Alert(Alert.AlertType.INFORMATION);
                removedTeam.setHeaderText("Success!");
                removedTeam.setContentText("The selected Team was removed!");
                removedTeam.showAndWait();
            } else {
                // Show alert
                Alert errorTeam = new Alert(Alert.AlertType.INFORMATION);
                errorTeam.setHeaderText("Error!");
                errorTeam.setContentText("Something went wrong deleting this Team!");
                errorTeam.showAndWait();
            }
        });
        //#endregion

        firstRow.getChildren().addAll(labelCreateTeam, createTeam, selectTeamCaptain, buttonCreateTeam);
        secondRow.getChildren().addAll(labelSelectTeam, selectTeam);
        thirdRow.getChildren().addAll(labelSelectPlayer, selectPlayer, buttonAddPlayer, playerTable,
                buttonRemovePlayer, buttonRemoveTeam);
        columns.getChildren().addAll(firstRow, secondRow, thirdRow);
        teamCrudBox.getChildren().addAll(columns);
        return teamCrudBox;
    }

    public static ArrayList<Player> getPlayers(){
        return players;
    }

    public static ArrayList<Team> getTeams(){
        return teams;
    }
}
