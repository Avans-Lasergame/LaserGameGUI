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
        playerCallback.isHit();
    }

    public void changeLED(int r, int g, int b) {
        this.gunCallback.changeLED(r, g, b);
    }

    public void setCallback(GunCallback gunCallback) {
        this.gunCallback = gunCallback;
    }

    public void setCallback(PlayerCallback playerCallback) {
        this.playerCallback = playerCallback;
    }
}
