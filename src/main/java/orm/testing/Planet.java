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

    public Planet(){}

    public Planet(String planetName, String color) {
        this.planetName = planetName;
        this.color = color;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
