package Objects;

public class Vest{
    private boolean isHit;

    public Vest(){
        this.isHit = false;
    }

    // Get items
    public void getsHit(){
        this.isHit = true;
    }


    // Set items
    public boolean isHit(){
        return this.isHit;
    }
    public void isNotHit(){
        this.isHit = false;
    }


}
