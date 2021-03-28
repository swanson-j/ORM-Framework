package orm.service;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Foreign;
import orm.annotations.Primary;
import orm.config.JDBCConnection;
import orm.config.JDBCConnectionPool;
import orm.dao.EntityManagerDAO;
import orm.exceptions.AnnotationException;
import orm.utilities.StatementHandler;
import orm.utilities.ResultSetHandler;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.concurrent.*;

import static java.util.Arrays.stream;


/**
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

    private static EntityManager instance;
    private static ExecutorService executorService;
    public EntityManagerDAO entityManagerDAO;
    public static int transactionSave = 0;
    public static StringBuilder transactionStringBuilder = new StringBuilder("begin;\n");

    private EntityManager(){}

    public static void setPath(String path) throws IOException {
        JDBCConnection.getInstance(path);
    }

    public static EntityManager getInstance() throws IOException, SQLException {
        if(instance == null){
            instance = new EntityManager();
            executorService = Executors.newFixedThreadPool(4);
            JDBCConnectionPool.createPool();
        }
        return instance;
    }

    //TODO: crud operations


    /**
     *  Recursively calls save() on fields with @Foreign annotation. This
     *      ensure that relations with dependencies are saved first
     */
    public <T> void save(T t) throws IllegalAccessException {

        // call save() on foreign keys
        stream(t.getClass().getDeclaredFields()).forEach(x->{
            if(x.isAnnotationPresent(Foreign.class)){
                try {
//                    transactionSave++;
                    save(x.get(t));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        //save fields in array and call returnSqlSave to get sql statement
        Field[] fields = t.getClass().getFields();

        String sql = StatementHandler.returnSqlSave(t, fields);

        entityManagerDAO = new EntityManagerDAO();
        entityManagerDAO.save(sql);

        /**
         * Failed transaction
         */
//        transactionStringBuilder.append(sql);
//        if(transactionSave == 0){
//            transactionStringBuilder.append("commit;");
//            entityManagerDAO = new EntityManagerDAO();
//            entityManagerDAO.save(transactionStringBuilder.toString());
//            System.out.println(transactionStringBuilder.toString());
//        }else{
//            transactionSave--;
//        }
    }

    /**
     *  read
     * @param clazz:    relation
     * @param f:        primary field to query against
     * @param <T>       Model class
     * @param <F>       dataType of primary field
     */
    public <T,F> T read(Class<T> clazz, F f) throws Exception {

        // get the column name of the primary key
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

        //Throw an exception if the user did not provide annotations
        if(fieldName.equals("")) {
            throw new AnnotationException("Primary and Column annotations must be present in each model." +
                    " Check: " + clazz.getName());
        }

        // Query select statement and send the result set to be handled
        String sql = StatementHandler.read(clazz, fieldName, f);
        ResultSetHandler resultSetHandler = new ResultSetHandler();
        return resultSetHandler.handleRead(clazz, sql);

    }


    /**
     *  update
     * @param t     instance
     * @param d     primary key
     * @param <T>   Class
     * @param <D>   Generic Datatype
     */
    public <T, D> void update(T t, D d){
        entityManagerDAO = new EntityManagerDAO();
        Field[] fields = t.getClass().getFields();
        try {
            System.out.println("Number of updated rows = " + entityManagerDAO.update(StatementHandler.update(t, d, fields)));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            System.out.println("Number of updated rows = 0");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("Number of updated rows = 0");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("Number of updated rows = 0");
        }
    }

    /**
     *  delete: Uses multithreading and connection pool for MVP
     * @param clazz relation
     * @param d     primary key
     * @param <T>   Model class
     * @param <D>   Generic datatype
     */
    public <T,D> void delete(Class<T> clazz, D d) throws ExecutionException, InterruptedException {
        entityManagerDAO = new EntityManagerDAO();

        Future future = executorService.submit(() -> {
            if(entityManagerDAO.destroy(StatementHandler.delete(clazz, d)))
                return clazz.getDeclaredAnnotation(Entity.class).name() + " destroyed";
            else
                return "Target missed";
        });

        System.out.println(future.get());
    }
}
