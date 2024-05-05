package Objects.Observers;


import Objects.Game;

public class PlayerObserver extends GameObserver {
    public PlayerObserver(Game game) {
        this.game = game;
        this.game.setPlayerObserver(this);
    }

    @Override
    public void update() {
    }
}
