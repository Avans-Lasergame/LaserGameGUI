package Objects;

import Objects.Callbacks.PlayerCallback;

import java.io.Serializable;
import java.util.UUID;

public class Player implements Serializable, PlayerCallback {


    private Gun gun;
    private UUID id;
    private String name;
    private int health;
    private int maxHealth;
    private boolean isDead;

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


    public void delete(Game game) {
        game.deletePlayer(this.getId());
    }


    @Override
    public void isHit() {
        // TODO: 13/05/2024 get hit here
    }
}
