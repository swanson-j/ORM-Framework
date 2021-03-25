import orm.service.EntityManager;
import orm.testing.Alien;
import orm.testing.Mothership;
import orm.testing.Planet;
import orm.testing.RayGun;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class DeleteDriver {

    public static void main(String[] args) throws IOException, SQLException, ExecutionException, InterruptedException {

        EntityManager.setPath("src/main/resources/jdbc.config");

        EntityManager entityManager = EntityManager.getInstance();

        //Try deleting object that is referenced
//        entityManager.delete(Mothership.class, "000000000002");

        entityManager.delete(Alien.class, 4);
    }
}
