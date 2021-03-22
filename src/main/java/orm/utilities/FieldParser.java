package orm.utilities;

import orm.annotations.Entity;
import orm.annotations.Foreign;
import orm.annotations.Primary;

import java.lang.reflect.Field;
import java.util.Arrays;

public class FieldParser {

    /*
     *  Returns a statement to insert a row when given a field array
     */
    public <T> String returnSqlSave(T t, Field[] fields) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into " + t.getClass().getAnnotation(Entity.class).name() + " values (");

        for(int i = 0; i < fields.length; i++){
            // checking if last field to close parenthesis
            if(i == (fields.length - 1)){

                // if current field is a foreign key
                if(fields[i].isAnnotationPresent(Foreign.class)){

                    int finalI = i;
                    // check each field in the foreign key object
                    Arrays.stream(fields[i].get(t).getClass().getDeclaredFields()).forEach(x->{

                        // if you find the primary key of the foreign key object
                        if(x.isAnnotationPresent(Primary.class)){

                            // if the foreign key is a string
                            if(returnDataType(x) == "String"){
                                try {
                                    sb.append("\"" + x.get(fields[finalI].get(t)) + "\")");
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
                    if(returnDataType(fields[i]) == "String"){
                        try {
                            sb.append("\"" + fields[i].get(t) + "\")");
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

                    //TODO: Problem here
                    System.out.println("Hello");

                    System.out.println(fields[i].get(t));

                    int finalI = i;
                    Arrays.stream(fields[i].get(t).getClass().getDeclaredFields()).forEach(x->{

                        // if you find the primary key of the foreign key object
                        if(x.isAnnotationPresent(Primary.class)){

                            // if the foreign key is a string
                            if(returnDataType(x) == "String"){
                                try {
                                    sb.append("\"" + x.get(fields[finalI].get(t)) + "\", ");
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
                    if(returnDataType(fields[i]) == "String"){
                        try {
                            sb.append("\"" + fields[i].get(t) + "\", ");
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

    public String returnDataType(Field field) {

        switch (field.getType().toString()) {
            case "class java.lang.String":
                return "String";
            default:
                return field.getType().toString();
        }
    }
}

