package Objects.Observers;


import Objects.Game;

public abstract class GameObserver {
    protected Game game;

    public abstract void update();
}
