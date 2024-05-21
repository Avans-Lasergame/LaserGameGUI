package Objects;

import Objects.Interfaces.GunCallback;
import Objects.Interfaces.PlayerCallback;

public class Gun implements GunCallback{
    // TODO: 04/04/2024 maak deze klasse
    int ID;
    private GunCallback gunCallback;
    private PlayerCallback playerCallback;

    public Gun(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Gun<" + ID + ">";
    }

    public void isHit() {
        System.out.println("hit");
        if (playerCallback != null)
            playerCallback.isHit();
    }


    public void setCallback(GunCallback gunCallback) {
        this.gunCallback = gunCallback;
    }

    public void setCallback(PlayerCallback playerCallback) {
        this.playerCallback = playerCallback;
    }
    @Override
    public void blink(double i, int r, int g, int b) {
        this.gunCallback.blink(i, r, g, b);
    }

    @Override
    public void rawCommand(String msg) {
        this.gunCallback.rawCommand(msg);
    }

    @Override
    public void changeLED(int r, int g, int b) {
        this.gunCallback.changeLED(r, g, b);
    }

    public int getID() {
        return ID;
    }
    @Override
    public void stop() {
        gunCallback.stop();
    }
}
