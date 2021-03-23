package orm.testing;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Primary;

@Entity(name = "RayGun")
public class RayGun {

    @Primary
    @Column(name = "serialNumber")
    public String serialNumber;

    @Column(name = "plasmaColor")
    public String plasmaColor;

    public RayGun(String serialNumber, String plasmaColor) {
        this.serialNumber = serialNumber;
        this.plasmaColor = plasmaColor;
    }
}
