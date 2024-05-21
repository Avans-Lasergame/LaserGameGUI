package Objects;

import java.util.HashMap;
import java.util.UUID;

public class Team{
    private final UUID id;
    private final String name;
    private final HashMap<UUID, Player> players = new HashMap<>();
    public Team(String teamName, Player player){
        this.id = UUID.randomUUID();
        this.name = teamName;
        this.players.put(player.getId(),player);
    }

    public void addPlayer(Player player){
        this.players.put(player.getId(),player);
    }

    public void removePlayer(Player player){
        this.players.remove(player.getId());
    }

    public UUID getId(){
        return this.id;
    }

    public String getTeamName(){
        return this.name;
    }

    public HashMap<UUID, Player> getPlayers(){
        return this.players;
    }

}
