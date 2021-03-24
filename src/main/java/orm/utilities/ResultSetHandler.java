package orm.utilities;

import orm.annotations.Column;
import orm.annotations.Foreign;
import orm.dao.EntityManagerDAO;
import orm.service.EntityManager;
import orm.testing.RayGun;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


/**
 *  Handles result sets that are passed from the DAO
 */

public class ResultSetHandler {

    private EntityManagerDAO entityManagerDAO = new EntityManagerDAO();

    public <T> T handleRead(Class<T> clazz, String sql) throws Exception {
        ResultSet resultSet = entityManagerDAO.read(sql);

        // if result set is empty
        if(!resultSet.next()) {
            System.out.println("No result from given primary key\n\n");
            return null;
        }
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        T clazzInstance = clazz.getConstructor().newInstance();

        Field[] fields = clazz.getFields();

        // TODO: loop through result set columns and try to store values into constructor

        for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
            String columnName;
            Method method;

            if (fields[i].isAnnotationPresent(Foreign.class)) {
                switch (fields[i].getType().toString()) {
                    case "int":
                        EntityManager.getInstance().read(fields[i].getClass(), resultSet.getInt(i + 1));
                        break;
                    case "double":
                        EntityManager.getInstance().read(fields[i].getClass(), resultSet.getDouble(i + 1));
                        break;
                    case "class java.lang.String":
                        EntityManager.getInstance().read(fields[i].getClass(), resultSet.getString(i + 1));
                        break;
                    case "boolean":
                        EntityManager.getInstance().read(fields[i].getClass(), resultSet.getBoolean(i + 1));
                        break;
                }
            } else {
                // Check what the datatype value is and store it
                switch (fields[i].getType().toString()) {
                    case "int":
                        columnName = fields[i].getDeclaredAnnotation(Column.class).name();
                        columnName = columnName.substring(0,1).toUpperCase() + columnName.substring(1);
                        method = clazz.getMethod("set" + columnName, int.class);
                        method.invoke(clazzInstance, resultSet.getInt(i+1));
                        break;
                    case "double":
                        columnName = fields[i].getDeclaredAnnotation(Column.class).name();
                        columnName = columnName.substring(0,1).toUpperCase() + columnName.substring(1);
                        method = clazz.getMethod("set" + columnName, double.class);
                        method.invoke(clazzInstance, resultSet.getDouble(i + 1));
                        break;
                    case "class java.lang.String":
                        columnName = fields[i].getDeclaredAnnotation(Column.class).name();
                        columnName = columnName.substring(0,1).toUpperCase() + columnName.substring(1);
                        method = clazz.getMethod("set" + columnName, String.class);
                        method.invoke(clazzInstance, resultSet.getString(i + 1));
                        break;
                    case "boolean":
                        columnName = fields[i].getDeclaredAnnotation(Column.class).name();
                        columnName = columnName.substring(0,1).toUpperCase() + columnName.substring(1);
                        method = clazz.getMethod("set" + columnName, boolean.class);
                        method.invoke(clazzInstance, resultSet.getBoolean(i + 1));
                        break;
                }
            }
        }

        return clazzInstance;
    }
}
