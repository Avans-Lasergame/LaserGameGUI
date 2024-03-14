package Objects;

public class Player{
    private Hub hub;
    private String playerName;
    private int health;

    public Player(Hub hub, String playerName){
        this.hub = hub;
        this.playerName = playerName;
        this.health = 100;
    }

    // Get items
    public Hub getPlayerHub(){
        return this.hub;
    }
    public String getPlayerName(){
        return this.playerName;
    }
    public int getPlayerHealth(){
        return this.health;
    }

    // Set items
    public int reduceHealth(){
        if (this.hub.getVest().isHit()){
            this.health = this.health-10;
        }
        return this.health;
    }

}
