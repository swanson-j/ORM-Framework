package orm.testing;


import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Primary;

@Entity(name = "Planet")
public class Planet {

    @Primary
    @Column(name = "planetName")
    public String planetName;

    @Column(name = "color")
    public String color;

    public Planet(String planetName, String color) {
        this.planetName = planetName;
        this.color = color;
    }
}
