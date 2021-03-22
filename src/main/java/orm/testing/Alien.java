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


    public Alien(int SSN, String name, Mothership mothership, RayGun rayGun) {
        this.SSN = SSN;
        this.name = name;
        this.mothership = mothership;
        this.rayGun = rayGun;
    }
}
