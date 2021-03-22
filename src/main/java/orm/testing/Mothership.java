package orm.testing;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Foreign;
import orm.annotations.Primary;

@Entity(name = "Mothership")
public class Mothership {

    @Primary
    @Column(name = "vinNumber")
    public String vinNumber;

    @Foreign(name = "Planet")
    @Column(name = "planet")
    public Planet planet;

    public Mothership(String vinNumber, Planet planetOriginated) {
        this.vinNumber = vinNumber;
        this.planet = planetOriginated;
    }
}
