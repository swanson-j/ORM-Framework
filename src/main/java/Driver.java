import orm.annotations.Column;
import orm.annotations.Entity;
import orm.config.JDBCConnection;
import orm.config.JDBCConnectionPool;
import orm.testing.Alien;
import orm.testing.Mothership;
import orm.testing.Planet;
import orm.testing.RayGun;
import orm.utilities.EntityManager;
import orm.utilities.FieldParser;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;

public class Driver {

    public static void main(String[] args) throws Exception {
        EntityManager.setPath("src/main/resources/jdbc.config");

        Planet planet = new Planet("Omicron", "Green");
        Mothership mothership = new Mothership("000000000001",  planet);
        RayGun rayGun = new RayGun("123456", "Yellow");
        Alien alien = new Alien(1, "Josh", mothership, rayGun);

        EntityManager entityManager = EntityManager.getInstance();

        // save alien to db
//        entityManager.save(alien);
        entityManager.read(Mothership.class, "555555555");
    }
}
