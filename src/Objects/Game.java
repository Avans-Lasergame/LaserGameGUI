package Objects;


import java.util.HashMap;
import java.util.UUID;

public class Game {
    private HashMap<UUID, Speler> attractions;

    public Game() {
        this.attractions = new HashMap<>();
    }

    public HashMap<UUID, Speler> getSpelers() {
        return attractions;
    }

    public Speler getAttraction(UUID id) {
        return attractions.get(id);
    }

    public void addSpeler(Speler speler) {
        this.attractions.put(speler.getId(), speler);
    }

    public void deleteSpeler(UUID id) {
        this.attractions.remove(id);
    }

}
