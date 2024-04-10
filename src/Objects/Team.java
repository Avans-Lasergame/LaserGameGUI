package Objects;

import java.util.ArrayList;
import java.util.UUID;

public class Team{
    private UUID id;
    private String name;
    private ArrayList<Player> players = new ArrayList<>();

    public Team(String teamName, Player player){
        this.id = UUID.randomUUID();
        this.name = teamName;
        this.players.add(player);
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void removePlayer(Player player){
        if (this.players.contains(player)){
            this.players.remove(player);
        }
    }

    public String getTeamName(){
        return this.name;
    }

    public ArrayList<Player> getPlayers(){
        return this.players;
    }

}
