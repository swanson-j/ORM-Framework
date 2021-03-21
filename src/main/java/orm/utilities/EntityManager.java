package orm.utilities;

import orm.config.JDBCConnection;
import orm.testing.Alien;
import orm.testing.Mothership;
import orm.testing.RayGun;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.ArrayList;
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

    public EntityManager getInstance(){
        if(instance == null){
            instance = new EntityManager();
        }
        return instance;
    }

    //TODO: crud operations

    public boolean save(Object object){
        return false;
    }

    public <T> List<T> read(Class<T> clazz, List<String> where){
        List<T> tList = new ArrayList<>();
        return tList;
    }

}
