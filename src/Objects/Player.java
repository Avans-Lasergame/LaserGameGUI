package Objects;

import Gui.GameCRUD;
import Objects.Interfaces.PlayerCallback;
import java.io.Serializable;
import java.util.UUID;

public class Player implements Serializable, PlayerCallback {
    private Gun gun;
    private UUID id;
    private String name;
    private int health;
    private int maxHealth;
    private boolean isDead = false;
    public Player(String name, int health,int maxHealth, Gun gun) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.gun = gun;
        this.health = health;
        this.maxHealth = maxHealth;
        gun.setCallback(this);
    }

    public void setAll(String name, int health,int maxHealth, Gun gun) {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.gun = gun;
        gun.setCallback(this);
    }

    public Gun getGun() {
        return gun;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public UUID getId() {
        return id;
    }

    public boolean isDead() {
        return isDead;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void delete(Game game) {
        game.deletePlayer(this.getId());
    }

    public void reset(){
        this.health = maxHealth;
        this.isDead = false;
        this.gun.changeLED(0,0,0);
    }

    @Override
    public void isHit() {
        if (health > 0)
            health --;
        else
            isDead = true;
        System.out.println("update");
        GameCRUD.getGame().update();
    }

}
