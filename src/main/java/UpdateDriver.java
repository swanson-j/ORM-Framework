import orm.service.EntityManager;
import orm.testing.Alien;
import orm.testing.Mothership;
import orm.testing.Planet;
import orm.testing.RayGun;

import java.io.IOException;
import java.sql.SQLException;

public class UpdateDriver {

    public static void main(String[] args) throws IOException, SQLException {

        EntityManager.setPath("src/main/resources/jdbc.config");

        EntityManager entityManager = EntityManager.getInstance();

        RayGun rayGun = new RayGun("123456", "Green");
        Planet planet = new Planet("Omicron","Green");
        Mothership mothership = new Mothership("000000000002", planet);
        Alien alien = new Alien(6, "Josh", mothership, rayGun);

        entityManager.update(alien, 5);

        // Update a row that doesn't exist
//        entityManager.update(alien, 5);

    }
}
