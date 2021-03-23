package orm.utilities;

import orm.annotations.Entity;
import orm.annotations.Foreign;
import orm.annotations.Primary;

import java.lang.reflect.Field;
import java.util.Arrays;

public class FieldParser {

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
}

