package Gui;

import Objects.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameCreate{
    private static ArrayList<Player> players = GUI.getPlayers(); //All Players
    private static ObservableList<Player> selectedPlayers = FXCollections.observableArrayList();  //Selected Players for a game
    private static int maximunPlayerCount = 4;

    public static VBox getComponent() {
        // General settings
        VBox gameCreateBox = new VBox(20);
        gameCreateBox.setPadding(new Insets(20));

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

        //#region List of players TableView
        TableView<Player> playerTable = new TableView<>();
        playerTable.setPrefWidth(200);
        TableColumn<Player, String> playerColumn = new TableColumn("Selected Players");
        playerColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        playerColumn.setPrefWidth(playerTable.getPrefWidth());
        playerTable.getColumns().add(playerColumn);
        playerTable.setItems(selectedPlayers);
        //#endregion


        // Action items:
        //#region Add Player Button
        Button buttonAddPlayer = new Button("Add Player to List");
        buttonAddPlayer.setOnAction(e -> {
            Object selectedGameMode = selectGameType.getSelectionModel().getSelectedItem();
            if (selectedGameMode == String.valueOf(GameModes.FreeForAll)){
                if (selectedPlayers.size() < maximunPlayerCount){
                    Player selectedPlayer = (Player) selectPlayer.getSelectionModel().getSelectedItem();
                    selectedPlayers.add(selectedPlayer);
                    // Show alert
                    Alert addedPlayer = new Alert(Alert.AlertType.INFORMATION);
                    addedPlayer.setHeaderText("Success!");
                    addedPlayer.setContentText(selectedPlayer.getName() + " was added to the list of selected players!");
                    addedPlayer.showAndWait();
                } else{
                    // Show alert
                    Alert addedPlayer = new Alert(Alert.AlertType.INFORMATION);
                    addedPlayer.setHeaderText("Error!");
                    addedPlayer.setContentText("Maximum amount of Players reached!");
                    addedPlayer.showAndWait();
                }
            } else {
                selectedPlayers.clear();
                // Show alert
                Alert addedPlayer = new Alert(Alert.AlertType.INFORMATION);
                addedPlayer.setHeaderText("Error!");
                addedPlayer.setContentText("You must change the GameMode to add individual Players!");
                addedPlayer.showAndWait();
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
                    Alert addedPlayer = new Alert(Alert.AlertType.INFORMATION);
                    addedPlayer.setHeaderText("Success!");
                    addedPlayer.setContentText("The last Player was removed from the list of selected players!");
                    addedPlayer.showAndWait();
                } else{
                    // Show alert
                    Alert addedPlayer = new Alert(Alert.AlertType.INFORMATION);
                    addedPlayer.setHeaderText("Error!");
                    addedPlayer.setContentText("There are no Players found in the list!");
                    addedPlayer.showAndWait();
                }
            } else {
                selectedPlayers.clear();
                // Show alert
                Alert addedPlayer = new Alert(Alert.AlertType.INFORMATION);
                addedPlayer.setHeaderText("Error!");
                addedPlayer.setContentText("You must change the GameMode to add individual Players!");
                addedPlayer.showAndWait();
            }
        });
        //#endregion

        gameCreateBox.getChildren().addAll(labelSelectGameType, selectGameType, labelSelectPlayer, selectPlayer,
                buttonAddPlayer, playerTable, buttonRemovePlayer);
        return gameCreateBox;
    }
}
