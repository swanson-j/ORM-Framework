package orm.testing;

import orm.annotations.Column;
import orm.annotations.Entity;


@Entity(name="Alien")
public class Alien {

    @Column(name = "SSN")
    public int SSN;

    @Column(name = "name")
    public String name;

    @Column(name = "mothership")
    public Mothership mothership;

    @Column(name = "rayGun")
    public RayGun rayGun;


    public Alien(int SSN, String name, Mothership mothership, RayGun rayGun) {
        this.SSN = SSN;
        this.name = name;
        this.mothership = mothership;
        this.rayGun = rayGun;
    }
}
