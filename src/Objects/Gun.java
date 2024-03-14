package Objects;

public class Gun{
    private boolean isShooting;

    public Gun(){
        this.isShooting = false;
    }

    public boolean isShooting(){
        return this.isShooting;
    }

    public void shootGun(){
        this.isShooting = true;
        // Set false after with a timer?
    }

}
