package Objects;

import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.UUID;

public class Game {
    private HashMap<UUID, Player> players;
    private HashMap<UUID, Team> teams;
    private GameModes gameMode;
    private boolean gameRunning;
    private Thread gameThread;

    public Game() {
        this.players = new HashMap<>();
        this.teams = new HashMap<>();
        this.gameRunning = false;
    }

    public Game(GameModes gameMode, ObservableList<Player> players) {
        this.teams = new HashMap<>();
        this.players = new HashMap<>();
        for (Player player : players) {
            this.players.put(player.getId(), player);
        }
        this.gameMode = gameMode;
        this.gameRunning = false;
    }

    public Game(GameModes gameMode, Team team1, Team team2) {
        this.teams = new HashMap<>();
        this.players = new HashMap<>();
        this.players.putAll(team1.getPlayers());
        this.players.putAll(team2.getPlayers());
        this.teams.put(team1.getId(), team1);
        this.teams.put(team2.getId(), team2);
        this.gameMode = gameMode;
        this.gameRunning = false;
    }

    public HashMap<UUID, Player> getPlayers() {
        return players;
    }

    public HashMap<UUID, Team> getTeams() {
        return teams;
    }

    public void deletePlayer(UUID id) {
        this.players.remove(id);
    }

    public GameModes getGameMode() {
        return this.gameMode;

    }

    public void startGame() {
        this.gameRunning = true;
        reset();
        this.update();
        gameThread = new Thread(new GameRunnable());
        gameThread.start();
    }

    public void endGame() {
        this.gameRunning = false;
        reset();
        try {
            if (gameThread != null) {
                gameThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void reset() {
        if (gameMode == GameModes.FreeForAll) {
            for (Player player : this.players.values()) {
                player.reset();
            }
        } else if (gameMode == GameModes.TeamDeathmatch) {
            for (Team team : this.teams.values()) {
                for (Player player : team.getPlayers().values()) {
                    player.reset();
                }
            }
        }
    }

    public boolean isGameRunning() {
        return this.gameRunning;
    }

    public void update() {
        System.out.println("game update");
        calculateBlinkRates();
    }

    private void calculateBlinkRates() {
        for (UUID uuid : players.keySet()) {
            if (players.get(uuid).isDead()) {
                players.get(uuid).getGun().changeLED(255, 0, 0);
            } else if (players.get(uuid).getHealth() == players.get(uuid).getMaxHealth()) {
                players.get(uuid).getGun().changeLED(0, 255, 0);
            } else {
                if (players.get(uuid).getHealth() > 0) {
                    double i = (double) players.get(uuid).getHealth() / players.get(uuid).getMaxHealth();
                    players.get(uuid).getGun().blink(i, 0, 255, 0);
                }
            }
        }
    }

    private class GameRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("Game is running...");
            while (isGameRunning()) {
                update();
                try {
                    Thread.sleep(100); // Sleep for a while to simulate game processing
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Game thread interrupted");
                }
            }
            System.out.println("Game has ended");
        }
    }
}
