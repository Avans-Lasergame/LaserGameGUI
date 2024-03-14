package Objects;

public class Vest{
    private boolean isHit;

    public Vest(){
        this.isHit = false;
    }

    public boolean isHit(){
        return this.isHit;
    }

    public void getsHit(){
        this.isHit = true;
    }
}
