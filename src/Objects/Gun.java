package Objects;

public class Gun {
    // TODO: 04/04/2024 maak deze klasse
    int ID;

    public Gun(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString(){
        return "Gun<" + ID + ">";
    }
}
