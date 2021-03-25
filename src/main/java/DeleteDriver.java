import orm.service.EntityManager;
import orm.testing.Alien;
import orm.testing.Mothership;
import orm.testing.Planet;
import orm.testing.RayGun;

import java.io.IOException;

public class DeleteDriver {

    public static void main(String[] args) throws IOException {

        EntityManager.setPath("src/main/resources/jdbc.config");

        EntityManager entityManager = EntityManager.getInstance();


        entityManager.delete(Mothership.class, "000000000002");
    }
}
