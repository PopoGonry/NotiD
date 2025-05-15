package notid;

import java.util.HashMap;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        HashMap<String, HashSet<String>> map = new HashMap<>();
        map.put("test", new HashSet<>());
        map.get("test").add("test2");

        System.out.println(map.get("test"));
    }
}