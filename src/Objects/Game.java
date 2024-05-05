package Objects;


import Objects.Observers.PlayerObserver;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Game {
    private HashMap<UUID, Player> players;
    private HashMap<UUID, Team> teams;
    private GameModes gameMode;
    private PlayerObserver playerObserver;
    private boolean gameRunning;

    public Game(){
        this.players = new HashMap<>();
        this.teams = new HashMap<>();
        this.playerObserver = new PlayerObserver(this);
        this.gameRunning = false;
    }

    public Game(GameModes gameMode, ObservableList<Player> players){
        this.players = new HashMap<>();
        for (Player player : players){
            this.players.put(player.getId(), player);
        }
        this.gameMode = gameMode;
        this.gameRunning = false;
    }

    public Game(GameModes gameMode, Team team1, Team team2){
        this.teams = new HashMap<>();
        this.teams.put(team1.getId(), team1);
        this.teams.put(team2.getId(), team2);
        this.gameMode = gameMode;
        this.gameRunning = false;
    }

    public HashMap<UUID, Player> getPlayers() {
        return players;
    }

    public HashMap<UUID, Team> getTeams(){
        return teams;
    }

    public void addPlayer(Player player) {
        this.players.put(player.getId(), player);
    }

    public void deletePlayer(UUID id) {
        this.players.remove(id);
    }

    public void setGameMode(GameModes gameMode){
        this.gameMode = gameMode;
    }

    public GameModes getGameMode(){
        return this.gameMode;
    }

    public void setPlayerObserver(PlayerObserver playerObserver) {
        this.playerObserver = playerObserver;
    }

    public void addSpeler(Player player) {
        this.players.put(player.getId(),player);
    }

    public void startGame() {
        this.gameRunning = true;
    }

    public void endGame(){
        this.gameRunning = false;
    }

    public boolean isGameRunning(){
        return this.gameRunning;
    }
}
