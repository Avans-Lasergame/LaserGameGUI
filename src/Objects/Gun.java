package Objects;

import Objects.Callbacks.GunCallback;
import Objects.Callbacks.PlayerCallback;

public class Gun {
    // TODO: 04/04/2024 maak deze klasse
    int ID;
    private GunCallback gunCallback;
    private PlayerCallback playerCallback;

    public Gun(int ID) {
        this.ID = ID;
    }

    public void isHit() {
        System.out.println("hit");
        playerCallback.isHit();
    }


    public void setCallback(GunCallback gunCallback) {
        this.gunCallback = gunCallback;
    }

    public void setCallback(PlayerCallback playerCallback) {
        this.playerCallback = playerCallback;
    }

    public void blink(int i, int r, int g, int b) {
        this.gunCallback.blink(i, r, g, b);
    }

    public void changeLED(int r, int g, int b) {
        this.gunCallback.changeLED(r, g, b);
    }

    @Override
    public String toString() {
        return "Gun{" +
                "ID=" + ID +
                '}';
    }
}
