package Objects;

public class Gun{
    private boolean isShooting;

    public Gun(){
        this.isShooting = false;
    }

    // Get items
    public boolean isShooting(){
        return this.isShooting;
    }

    // Set items
    public void shootGun(){
        this.isShooting = true;
        // Set false after with a timer?
    }

}
