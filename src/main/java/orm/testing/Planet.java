package orm.testing;


import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Primary;

@Entity(name = "Planet")
public class Planet {

    @Primary
    @Column(name = "planetName")
    String planetName;

    public Planet(String planetName) {
        this.planetName = planetName;
    }
}
