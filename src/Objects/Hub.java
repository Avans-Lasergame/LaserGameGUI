package Objects;

public class Hub{
    private Gun gun;
    private Vest vest;

    public Hub(Gun gun, Vest vest){
        this.gun = gun;
        this.vest = vest;
    }

    // Get items
    public Gun getGun(){
        return this.gun;
    }
    public Vest getVest(){
        return this.vest;
    }

}
