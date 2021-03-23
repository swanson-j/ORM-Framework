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

    public static void main(String[] args) throws IOException, SQLException, IllegalAccessException {
        EntityManager.setPath("src/main/resources/jdbc.config");
//        JDBCConnectionPool.createPool();

        Planet planet = new Planet("Omicron", "Green");
        Mothership mothership = new Mothership("000000000001",  planet);
        RayGun rayGun = new RayGun(123456, "Yellow");
        Alien alien = new Alien(1, "Josh", mothership, rayGun);

        EntityManager entityManager = EntityManager.getInstance();


        Field[] fields = alien.getClass().getFields();
        FieldParser fieldParser = new FieldParser();

//        for(int i = 0; i < fields.length; i++){
//            System.out.println(fieldParser.returnDataType(fields[i]));
//        }

//        System.out.println(fieldParser.returnSqlSave(alien, fields));

//        entityManager.save(alien);

//        System.out.println(Alien.class.getAnnotation(Entity.class).name());
//        Arrays.stream(Alien.class.getDeclaredFields()).forEach(x->{
//            System.out.println(x.getName());
//        });

        entityManager.save(alien);
    }
}
