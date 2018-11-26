package com.haole.core.reflect.demo;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by shengjunzhao on 2018/11/24.
 */
public class ReflectClassDemo {

    public static void reflectClass() {
        HashMap<String, Integer> map = new HashMap<>();
        Class<? extends HashMap> mapClass = map.getClass();
        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
        Class<? extends LinkedHashMap> linkedMapClass = linkedHashMap.getClass();
        System.out.println(mapClass.isAssignableFrom(linkedMapClass));
        System.out.println(linkedMapClass.asSubclass(mapClass)); //class java.util.LinkedHashMap
        System.out.println("toString=" + mapClass.toString()); //toString=class java.util.HashMap
        System.out.println(
                "toGenericString=" + mapClass.toGenericString()); //toGenericString=public class java.util.HashMap<K,V>
        System.out.println(String[].class.toString());// class [Ljava.lang.String;
        System.out.println(mapClass.getSigners()); // null
        System.out.println("getSuperclass=" + mapClass.getSuperclass());// getSuperclass=class java.util.AbstractMap
        System.out.println("getGenericSuperclass=" + mapClass.getGenericSuperclass()); // java.util.AbstractMap<K, V>
        System.out.println(mapClass.getSigners()); // null
        System.out.println("getSimpleName=" + mapClass.getSimpleName()); // HashMap
        System.out.println("String[].class getSimpleName=" + String[].class.getSimpleName()); // String[]
        System.out.println("getTypeName=" + mapClass.getTypeName()); // java.util.HashMap
        System.out.println("String[].class getTypeName=" + String[].class.getTypeName()); //java.lang.String[]
        System.out.println("getName=" + mapClass.getName()); // java.util.HashMap
        System.out.println("String[].class getName=" + String[].class.getName());// class [Ljava.lang.String;
        System.out.println(
                "String[].class getCanonicalName=" + String[].class.getCanonicalName());// class [Ljava.lang.String;
        Class<?>[] mapClasses = mapClass.getClasses();
        for (Class<?> c : mapClasses)
            System.out.println("getClasses=" + c);
        Constructor<?>[] cons = mapClass.getConstructors();
        for (Constructor<?> c : cons)
            System.out.println("getConstructors=" + c.toGenericString());
        Color[] colors = Color.class.getEnumConstants();
        for (Color c : colors)
            System.out.println("getEnumConstants=" + c.name());
        Shape[] shapes = Shape.class.getEnumConstants();
        for (Shape s : shapes) {
            System.out.println("getEnumConstants=" + s.getCode());
            System.out.println("getEnumConstants=" + s.name());
            System.out.println("getEnumConstants=" + s.getName());
        }
        System.out.println("getAnnotatedSuperclass=" + mapClass.getAnnotatedSuperclass());

    }

    public static void main(String[] args) {
        reflectClass();
    }
}
