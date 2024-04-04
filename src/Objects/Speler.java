package Objects;

import java.io.Serializable;
import java.util.UUID;

public class Speler implements CRUD, Serializable {


    private Vest vest;
    private Gun gun;
    private UUID id;
    private String name;
    private int health;


    public Speler(String name, int health, Gun gun, Vest vest) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.gun = gun;
        this.health = health;
        this.vest = vest;

        this.update();
    }
    public void setAll(String name, int health, Gun gun, Vest vest) {
        this.name = name;
        this.health = health;
        this.gun = gun;
        this.vest = vest;
    }
    public Vest getVest() {
        return vest;
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

    @Override
    public void update() {

    }

    @Override
    public void delete(Game game) {
        game.deleteSpeler(this.getId());
    }



}
