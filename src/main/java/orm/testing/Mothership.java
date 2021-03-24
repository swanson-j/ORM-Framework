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
    @Column(name = "planetOriginated")
    public Planet planetOriginated;

    public Mothership(){}

    public Mothership(String vinNumber, Planet planetOriginated) {
        this.vinNumber = vinNumber;
        this.planetOriginated = planetOriginated;
    }
}
