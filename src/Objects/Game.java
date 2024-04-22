package Objects;


import Objects.Observers.PlayerObserver;

import java.util.HashMap;
import java.util.UUID;

public class Game {
    private HashMap<UUID, Player> players;
    private PlayerObserver playerObserver;

    public Game() {
        this.players = new HashMap<>();
        this.playerObserver = new PlayerObserver(this);
    }

    public HashMap<UUID, Player> getSpelers() {
        return players;
    }

    public Player getSpeler(UUID id) {
        return players.get(id);
    }

    public void addSpeler(Player player) {
        this.players.put(player.getId(), player);
        this.playerObserver.update();
    }

    public void deleteSpeler(UUID id) {
        this.players.remove(id);
        this.playerObserver.update();
    }

    public void setPlayerObserver(PlayerObserver playerObserver) {
        this.playerObserver = playerObserver;
    }
}
