package Objects.Interfaces;

public interface GunCallback {
    void changeLED(int r, int g, int b);

    void blink(double i, int r, int g, int b);

    void rawCommand(String msg);
    void stop();

}
