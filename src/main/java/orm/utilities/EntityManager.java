package orm.utilities;

import orm.annotations.Entity;
import orm.annotations.Foreign;
import orm.config.JDBCConnection;
import orm.testing.Alien;
import orm.testing.Mothership;
import orm.testing.RayGun;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*
 *  EntityManager is a singleton that is used as an intermediate data layer to wrap
 *      objects and perform operations to send to the EntityManagerDAO
 *
 *  Operations:
 *      save:
 *      read:
 *      update:
 *      destroy:
 */
public class EntityManager {

    public List<Connection> availableConnections;
    public List<Connection> usedConnections;
    public Connection connection;
    private static EntityManager instance;

    private EntityManager(){}

    public static void setPath(String path) throws IOException {
        JDBCConnection.getInstance(path);
    }

    public static EntityManager getInstance(){
        if(instance == null){
            instance = new EntityManager();
        }
        return instance;
    }

    //TODO: crud operations

    public <T> boolean save(T t) throws IllegalAccessException {


        System.out.println("----------------------------------------------------");

        // Get class name
        System.out.println(t.getClass().getAnnotation(Entity.class).name());

        // Gets field name
        Arrays.stream(t.getClass().getDeclaredFields()).forEach(x->{
            System.out.println(x.getName());
        });
        System.out.println("----------------------------------------------------");


        /*
         *     gets only foreign key fields
         * TODO: use this to recursively call foreign keys. When foreign keys
         *          are all in DB, then I can add all fields
         */
        Arrays.stream(t.getClass().getDeclaredFields()).forEach(x->{
            if(x.isAnnotationPresent(Foreign.class)){
                System.out.println("Foreign keys: " + x.getName());
            }
        });


        Field[] fields = t.getClass().getFields();

        for(int i = 0; i < fields.length; i++){
            System.out.println(fields[i].get(t));
        }


        return false;
    }

    public <T> List<T> read(Class<T> clazz, List<String> where){
        List<T> tList = new ArrayList<>();
        return tList;
    }

}
