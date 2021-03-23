package orm.testing;

import orm.annotations.Entity;

import java.util.Arrays;

public class RandomClass {

    public static void main(String[] args) {


    }

    public <T> void doSomething(T t){
        System.out.println("----------------------------------------------------");

        // Get class name
        System.out.println(t.getClass().getAnnotation(Entity.class).name());

        // Gets field name
        Arrays.stream(t.getClass().getDeclaredFields()).forEach(x->{
            System.out.println(x.getName());
        });
        System.out.println("----------------------------------------------------");
    }
}
