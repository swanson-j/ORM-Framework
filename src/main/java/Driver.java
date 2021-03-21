import orm.annotations.Column;
import orm.annotations.Entity;
import orm.config.JDBCConnection;
import orm.config.JDBCConnectionPool;
import orm.testing.Alien;
import orm.utilities.EntityManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class Driver {

    public static void main(String[] args) throws IOException, SQLException {
        EntityManager.setPath("src/main/resources/jdbc.config");
        JDBCConnection.printProperties();
//        JDBCConnectionPool.createPool();

        System.out.println(Alien.class.getAnnotation(Entity.class).name());
        Arrays.stream(Alien.class.getDeclaredFields()).forEach(x->{
            System.out.println(x.getName());
        });
    }
}
