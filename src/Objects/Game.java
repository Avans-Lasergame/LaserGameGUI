package Objects;


import java.util.HashMap;
import java.util.UUID;

public class Game {
    private HashMap<UUID, Player> attractions;

    public Game() {
        this.attractions = new HashMap<>();
    }

    public HashMap<UUID, Player> getSpelers() {
        return attractions;
    }

    public Player getAttraction(UUID id) {
        return attractions.get(id);
    }

    public void addSpeler(Player player) {
        this.attractions.put(player.getId(), player);
    }

    public void deleteSpeler(UUID id) {
        this.attractions.remove(id);
    }

}
