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

    public Hub getPlayerHub(){
        return this.hub;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public void setPlayerHealth(int playerHealth){
        this.health = playerHealth;
    }
    public int getPlayerHealth(){
        if (this.hub.getVest().isHit()){
            this.setPlayerHealth(this.health-10);
        }
        return this.health;
    }

}
