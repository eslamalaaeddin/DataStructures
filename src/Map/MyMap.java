package Map;

import java.util.*;

//I have not implemented, I just used it
public class MyMap {
    public static void main(String[] args) {
        Map<Integer, String> contacts = new HashMap<>();

        contacts.put(1, "A");
        contacts.put(2, "B");
        contacts.put(3, "C");
        contacts.put(4, "D");
        contacts.put(5, "E");

        //To get all the key/value pairs in it
        Set<Map.Entry<Integer, String>> keyValues = contacts.entrySet();

        for (Map.Entry<Integer, String> keyValue : keyValues) {
            System.out.println(keyValue);
        }

        System.out.println("-----------------");

        //To get all the values
        Collection<String> values = contacts.values();

        for (String value : values) {
            System.out.println(value);
        }

        System.out.println("-----------------");


        //To get all the keys
        Set<Integer> keys = contacts.keySet();

        for (Integer key : keys) {
            System.out.println(key);
        }

        System.out.println("-----------------");


        contacts.entrySet().stream().forEach(entry -> {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        });



    }
}
