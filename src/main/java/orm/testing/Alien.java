package orm.testing;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Foreign;
import orm.annotations.Primary;


@Entity(name="Alien")
public class Alien {

    @Primary
    @Column(name = "SSN")
    public int SSN;

    @Column(name = "name")
    public String name;

    @Foreign(name = "Mothership")
    @Column(name = "mothership")
    public Mothership mothership;

    @Foreign(name = "RayGun")
    @Column(name = "rayGun")
    public RayGun rayGun;

    public Alien(){}

    public Alien(int SSN, String name, Mothership mothership, RayGun rayGun) {
        this.SSN = SSN;
        this.name = name;
        this.mothership = mothership;
        this.rayGun = rayGun;
    }

    public void setSSN(int SSN) {
        this.SSN = SSN;
    }

    public int getSSN() {
        return SSN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mothership getMothership() {
        return mothership;
    }

    public void setMothership(Mothership mothership) {
        this.mothership = mothership;
    }

    public RayGun getRayGun() {
        return rayGun;
    }

    public void setRayGun(RayGun rayGun) {
        this.rayGun = rayGun;
    }

    public void printAlien(){
        System.out.println("SSN: " + this.getSSN());
        System.out.println("Name: " + this.getName());
        System.out.println("Mothership: " + this.getMothership().vinNumber);
        System.out.println("RayGun: " + this.getRayGun().serialNumber);
    }
}
