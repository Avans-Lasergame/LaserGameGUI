package Gui;

import Objects.Gun;
import Objects.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import java.util.ArrayList;

public class PlayerCRUD {
    private static ArrayList<Player> players = GUI.getPlayers(); // All Players
    private static final ObservableList<Player> selectablePlayers = FXCollections.observableArrayList(); // Selectable Players
    // Items:
    private static final ComboBox<Player> selectPlayer = new ComboBox<>(); // Selected Players
    private static final ComboBox<Gun> gunComboBox = new ComboBox<>(); // Connected LaserGuns
    private static final ArrayList<Integer> usedGuns = new ArrayList<Integer>(); // Already assigned LaserGuns

    public static VBox getComponent() {
        // General settings
        VBox playerCrudBox = new VBox(20);
        playerCrudBox.setPadding(new Insets(20));
        VBox firstRow = new VBox(20);
        VBox secondRow = new VBox(20);
        VBox thirdRow = new VBox(20);
        HBox columns = new HBox(100);
        // Fill starting list of Teams
        selectablePlayers.addAll(players);

        //#region Create Player (with LaserGun)
        Label labelCreatePlayer = new Label("Create new Player:");

        VBox item1 = new VBox(10);
        Label nameInputLabel = new Label("Name: ");
        TextField nameInput = new TextField();
        item1.getChildren().addAll(nameInputLabel, nameInput);

        VBox item2 = new VBox(10);
        Label healthInputLabel = new Label("Health: ");
        TextField healthInput = new TextField();
        item2.getChildren().addAll(healthInputLabel, healthInput);

        VBox item3 = new VBox(10);
        Label maxHealthInputLabel = new Label("MaxHealth: ");
        TextField maxHealthInput = new TextField();
        item3.getChildren().addAll(maxHealthInputLabel, maxHealthInput);

        VBox item4 = new VBox(10);
        selectPlayer.getItems().addAll(GUI.getPlayers());
        gunComboBox.setPrefWidth(100);

        item4.getChildren().addAll(gunComboBox);
        //#endregion


        //#region Select Player ComboBox
        Label labelSelectPlayer = new Label("Select Player:");
        selectPlayer.setPrefWidth(200);
        ObservableList<Player> playerList = FXCollections.observableArrayList(players);
        selectPlayer.setItems(playerList);
        selectPlayer.setConverter(new StringConverter<Player>() {
            @Override
            public String toString(Player player) {
                return player.getName();
            }

            @Override
            public Player fromString(String string) {
                return selectPlayer.getItems().stream().filter(player ->
                        player.equals(string)).findFirst().orElse(null);
            }
        });
        selectPlayer.getSelectionModel().selectFirst();
        //#endregion

        // Action items:
        //#region Create Player Button
        Button buttonCreatePlayer = new Button("Create Player");
        buttonCreatePlayer.setOnAction(e -> {
            if (!nameInput.getText().equalsIgnoreCase("") && !healthInput.getText().equalsIgnoreCase("") &&
                    !maxHealthInput.getText().equalsIgnoreCase("") && nameInput.getText().length() <= 20 &&
                    healthInput.getText().length() <= 3 && maxHealthInput.getText().length() <= 3) {
                if (Integer.parseInt(healthInput.getText()) <= Integer.parseInt(maxHealthInput.getText())) {
                    // TODO: Creating of Guns and Vests + selections for this!
                    Player newPlayer = new Player(nameInput.getText(), Integer.parseInt(healthInput.getText()), Integer.parseInt(maxHealthInput.getText()), gunComboBox.getValue());
                    usedGuns.add(gunComboBox.getValue().getID());
                    gunComboBox.getItems().remove(gunComboBox.getValue());
                    selectablePlayers.add(newPlayer);
                    selectPlayer.getItems().add(newPlayer);
                    players.add(newPlayer);
                    selectPlayer.getSelectionModel().selectFirst();

                    // Show alert
                    Alert addedPlayer = new Alert(Alert.AlertType.INFORMATION);
                    addedPlayer.setHeaderText("Success!");
                    addedPlayer.setContentText(newPlayer.getName() + " was created! ");
                    addedPlayer.showAndWait();

                    // Empty textfields
                    nameInput.setText("");
                    healthInput.setText("");
                    maxHealthInput.setText("");
                } else {
                    // Show alert
                    Alert errorPlayer = new Alert(Alert.AlertType.INFORMATION);
                    errorPlayer.setHeaderText("Error!");
                    errorPlayer.setContentText("Player cannot have more health than the maximum!");
                    errorPlayer.showAndWait();
                }
            } else {
                // Show alert
                Alert errorPlayer = new Alert(Alert.AlertType.INFORMATION);
                errorPlayer.setHeaderText("Error!");
                errorPlayer.setContentText("Player was not created!");
                errorPlayer.showAndWait();
            }
        });
        //#endregion

        //#region Remove Player Button
        Button buttonRemovePlayer = new Button("Delete selected Player");
        buttonRemovePlayer.setOnAction(e -> {
            Player selectedPlayer = selectPlayer.getSelectionModel().getSelectedItem();
            if (selectedPlayer == null) {
                // Show alert
                Alert errorPlayer = new Alert(Alert.AlertType.INFORMATION);
                errorPlayer.setHeaderText("Error!");
                errorPlayer.setContentText("There was no Player selected!");
                errorPlayer.showAndWait();
                return;
            }
            if (!selectedPlayer.getName().equalsIgnoreCase("")) {
                selectPlayer.getItems().remove(selectedPlayer);
                players.remove(selectedPlayer);
                selectPlayer.getSelectionModel().selectFirst();

                // Show alert
                Alert removedPlayer = new Alert(Alert.AlertType.INFORMATION);
                removedPlayer.setHeaderText("Success!");
                removedPlayer.setContentText("The selected Player was removed!");
                removedPlayer.showAndWait();
            } else {
                // Show alert
                Alert errorPlayer = new Alert(Alert.AlertType.INFORMATION);
                errorPlayer.setHeaderText("Error!");
                errorPlayer.setContentText("Something went wrong deleting this Player!");
                errorPlayer.showAndWait();
            }
        });
        //#endregion

        firstRow.getChildren().addAll(labelCreatePlayer, item1, item2, item3, item4, buttonCreatePlayer);
        secondRow.getChildren().addAll(labelSelectPlayer, selectPlayer, buttonRemovePlayer);
        columns.getChildren().addAll(firstRow, secondRow, thirdRow);
        playerCrudBox.getChildren().addAll(columns);
        return playerCrudBox;
    }

    public static void updateData() {
        players = GUI.getPlayers();
        // selectPlayer
        // Fill starting list of Players
        selectablePlayers.addAll(players);

        ObservableList<Player> playerList = FXCollections.observableArrayList(players);
        selectPlayer.setItems(playerList);

        System.out.println(usedGuns);
        if (!GUI.getGuns().isEmpty())
            for (Gun gun : GUI.getGuns()) {
                if (!usedGuns.contains(gun.getID()) && !gunComboBox.getItems().contains(gun)) {
                    gunComboBox.getItems().add(gun);
                }
            }
        else
            usedGuns.clear();
        System.out.println("set guns");
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }
}
