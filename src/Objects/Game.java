package Objects;


import java.util.HashMap;
import java.util.UUID;

public class Game {
    private HashMap<UUID, Speler> spelers;

    public Game() {
        this.spelers = new HashMap<>();
    }

    public HashMap<UUID, Speler> getSpelers() {
        return spelers;
    }

    public Speler getAttraction(UUID id) {
        return spelers.get(id);
    }

    public void addSpeler(Speler speler) {
        this.spelers.put(speler.getId(), speler);
    }

    public void deleteSpeler(UUID id) {
        this.spelers.remove(id);
    }

}
