package Objects;

import java.io.Serializable;
import java.util.UUID;

public class Player implements Serializable {


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
    }
    public void setAll(String name, int health,int maxHealth, Gun gun) {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.gun = gun;
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

    public void isHit(){
        // 0 wordt hierbij de damage die ontvangen wordt vanuit de gun
        int damage = 0;
        this.health -= damage;

        if (this.health <= 0){
            isDead = true;
        }
    }


}
