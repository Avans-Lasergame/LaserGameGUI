package Objects;

import java.util.ArrayList;
import java.util.UUID;

public class Team{
    private UUID id;
    private String name;
    private ArrayList<Player> players = new ArrayList<>();

    // Constructors with and without TeamName and with multiple Players
    public Team(Player player){
        this.id = UUID.randomUUID();
        this.name = "";
        this.players.add(player);
    }
    public Team(String teamName, Player player){
        this.id = UUID.randomUUID();
        this.name = teamName;
        this.players.add(player);
    }
    public Team(String teamName, Player player1, Player player2){
        this.id = UUID.randomUUID();
        this.name = teamName;
        this.players.add(player1);
        this.players.add(player2);
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }
    public void removePlayer(Player player){
        if (this.players.contains(player)){
            this.players.remove(player);
        }
    }
    public ArrayList<Player> getPlayers(){
        return this.players;
    }

}
