import orm.annotations.Foreign;
import orm.testing.Alien;
import orm.testing.Mothership;
import orm.testing.Planet;
import orm.testing.RayGun;
import orm.service.EntityManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Driver {

    public static void main(String[] args) throws Exception {
        EntityManager.setPath("src/main/resources/jdbc.config");

        Planet planet = new Planet("Omicron", "Green");
        Mothership mothership = new Mothership("000000000002",  planet);
        RayGun rayGun = new RayGun("123456", "Yellow");
        Alien alien = new Alien(2, "Josh", mothership, rayGun);

        EntityManager entityManager = EntityManager.getInstance();

        // save alien to db
        entityManager.save(alien);

        //TODO: finish read within EntitiyManager and dao
        RayGun newRaygun = entityManager.read(RayGun.class, "123456");
        System.out.println("Serial Number: " + newRaygun.serialNumber);
        System.out.println("Plasma Color: " + newRaygun.plasmaColor);



//        Alien a = new Alien();
//        Method method = alien.getClass().getMethod("setSSN", int.class);
//        method.invoke(a, 4);
//
//        System.out.println(a.SSN);
    }
}
