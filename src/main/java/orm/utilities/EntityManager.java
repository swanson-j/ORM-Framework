package orm.utilities;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Foreign;
import orm.annotations.Primary;
import orm.config.JDBCConnection;
import orm.dao.EntityManagerDAO;
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

import static java.util.Arrays.stream;


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
    public EntityManagerDAO entityManagerDAO;

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


    /**
     *  Recursively calls save() on fields with @Foreign annotation. This
     *      ensure that relations with dependencies are saved first
     */
    public <T> void save(T t) throws IllegalAccessException {

        stream(t.getClass().getDeclaredFields()).forEach(x->{
            if(x.isAnnotationPresent(Foreign.class)){
                try {
                    save(x.get(t));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        //save fields in array and call returnSqlSave to get sql statement
        Field[] fields = t.getClass().getFields();


        String sql = FieldParser.returnSqlSave(t, fields);

        entityManagerDAO = new EntityManagerDAO();
        entityManagerDAO.save(sql);
    }

    /**
     *
     * @param clazz:    relation
     * @param f:        primary field to query against
     * @param <T>       Model class
     * @param <F>       dataType of primary field
     */
    public <T,F> void read(Class<T> clazz, F f) throws Exception {
        String fieldName = "";
        for(Field field : clazz.getFields()){
            if(field.isAnnotationPresent(Primary.class)){
                if(field.isAnnotationPresent(Column.class)){
                    Column column = field.getAnnotation(Column.class);
                    fieldName = column.name();
                    break;
                }
            }
        }

        if(fieldName.equals(""))
            throw new Exception("Primary and Column annotations must be present in each model");

        System.out.println(FieldParser.read(clazz, fieldName, f));

        //TODO: call DAO for select statement called read() from above method, within sysout^^
    }
}
