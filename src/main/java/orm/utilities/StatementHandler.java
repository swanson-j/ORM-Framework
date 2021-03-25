package orm.utilities;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Foreign;
import orm.annotations.Primary;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class StatementHandler {

    /**
     *  Builds a insert statement given some class and its fields.
     *  Super nested for the purpose of checking for @Foreign annotation
     *      to save the primary key of that object as the column value of
     *      the current object
     */
    public static <T> String returnSqlSave(T t, Field[] fields) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into " + t.getClass().getAnnotation(Entity.class).name() + " values (");

        for(int i = 0; i < fields.length; i++){
            // checking if last field to close parenthesis
            if(i == (fields.length - 1)){

                // if current field is a foreign key
                if(fields[i].isAnnotationPresent(Foreign.class)){

                    int finalI = i;

                    // check each field in the foreign key object
                    Arrays.stream(fields[i].get(t).getClass().getFields()).forEach(x->{

                        // if you find the primary key of the foreign key object
                        if(x.isAnnotationPresent(Primary.class)){

                            // if the foreign key's primary key is a string
                            if(returnDataType(x).equals("String")){
                                try {
                                    sb.append("\'" + x.get(fields[finalI].get(t)) + "\')");
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    sb.append(x.get(fields[finalI].get(t)) + ")");
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } else {
                    if(returnDataType(fields[i]).equals("String")){
                        try {
                            sb.append("\'" + fields[i].get(t) + "\')");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            sb.append(fields[i].get(t) + ")");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                // if current field is a foreign key
                if(fields[i].isAnnotationPresent(Foreign.class)){
                    // check each field in the foreign key object

                    int finalI = i;
                    Arrays.stream(fields[i].get(t).getClass().getFields()).forEach(x->{

                        // if you find the primary key of the foreign key object
                        if(x.isAnnotationPresent(Primary.class)){

                            // if the foreign key is a string
                            if(returnDataType(x).equals("String")){
                                try {
                                    sb.append("\'" + x.get(fields[finalI].get(t)) + "\', ");
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    sb.append(x.get(fields[finalI].get(t)) + ", ");
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } else {
                    if(returnDataType(fields[i]).equals("String")){
                        try {
                            sb.append("\'" + fields[i].get(t) + "\', ");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            sb.append(fields[i].get(t) + ", ");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return sb.toString();
    }


    /**
     *  Given a field, return the data type of that field
     * @param field:    field with data type to return
     * @return          Data type of field as a String
     */
    public static String returnDataType(Field field) {

        switch (field.getType().toString()) {
            case "class java.lang.String":
                return "String";
            default:
                return field.getType().toString();
        }
    }


    /**
     * Returns a select statement for querying
     * @param clazz
     * @param <T>
     */
    public static <T, D> String read(Class<T> clazz, String fieldName, D d){
        StringBuilder sb = new StringBuilder();
        sb.append("select * from " + clazz.getAnnotation(Entity.class).name() + " where " + fieldName + " = ");

        for(Field field : clazz.getFields()){
            if(field.isAnnotationPresent(Primary.class)){
                if(returnDataType(field).equals("String")){
                    sb.append("\'" + d + "\'");
                }else{
                    sb.append(d);
                }
            }
        }

        return sb.toString();
    }

    /**
     *
     * @param t
     * @param fields
     * @param <T>
     * @return
     */
    public static <T, D> String update(T t, D d, Field[] fields) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String primaryField = null;
        StringBuilder sb = new StringBuilder();
        sb.append("update " + t.getClass().getAnnotation(Entity.class).name()
                + " set ");

        // looping through fields to create the set parameters
        for(int i = 0; i < fields.length; i++){
            if(fields[i].isAnnotationPresent(Primary.class)){
                primaryField = fields[i].getDeclaredAnnotation(Column.class).name();
            }

            // if the field is a foreign key, get the primary key associated with that foreign key
            if(fields[i].isAnnotationPresent(Foreign.class)){
                Field[] foreignFields = fields[i].getType().getFields();
                Field foreignPrimaryKey = null;
                for(int j = 0; j < foreignFields.length; j++){
                    if(foreignFields[j].isAnnotationPresent(Primary.class)){
                        foreignPrimaryKey = foreignFields[j];
                    }
                }

                // set foreign key's primary key field name
                sb.append(foreignPrimaryKey.getName());

                // get an object instance to send to parseUpdateFieldDataType()
                String column = fields[i].getDeclaredAnnotation(Foreign.class).name();
                Method method = t.getClass().getMethod("get" + column);
                sb.append(parseUpdateFieldsDataType(method.invoke(t), foreignPrimaryKey));

                if(i == fields.length - 1){
                } else {
                    sb.append(", ");
                }
            } else {
                sb.append(fields[i].getAnnotation(Column.class).name() + parseUpdateFieldsDataType(t, fields[i]));
                if(i == fields.length - 1){
                } else {
                    sb.append(", ");
                }
            }
        }
        sb.append(" where " + primaryField + " = \'" + d + "\';" );
        return sb.toString();
    }

    /**
     * Switch staements used to create each member with the set clause: ex: set ssn = 3, name = 'foo'
     * @param t
     * @param field
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T> String parseUpdateFieldsDataType(T t, Field field) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        Method method;
        String column;
        switch(field.getType().toString()){
            case "int":
            case "double":
                column = field.getDeclaredAnnotation(Column.class).name();
                column = column.substring(0,1).toUpperCase() + column.substring(1);
                method = t.getClass().getMethod("get" + column);
                sb.append(" = " + method.invoke(t));
                break;
            case "boolean":
            case "class java.lang.String":
                column = field.getDeclaredAnnotation(Column.class).name();
                column = column.substring(0,1).toUpperCase() + column.substring(1);
                method = t.getClass().getMethod("get" + column);
                sb.append(" = \'" + method.invoke(t) + "\'");
                break;
        }

        return sb.toString();
    }

    public static <T,D> String delete(Class<T> clazz, D d){
        StringBuilder sb = new StringBuilder();
        sb.append("delete from " + clazz.getDeclaredAnnotation(Entity.class).name() + " where ");
        Field[] fields = clazz.getFields();
        for(Field field : fields){
            if(field.isAnnotationPresent(Primary.class)){
                sb.append(field.getDeclaredAnnotation(Column.class).name());
            }
        }

        sb.append(" = \'" + d + "\';");
        return sb.toString();
    }
}

