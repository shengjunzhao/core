package com.haole.core.reflect.demo;

import com.haole.core.cache.impl.EhcacheServiceImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by shengjunzhao on 2018/11/24.
 */
public class ReflectClassDemo {

    public static void reflectClass() throws NoSuchMethodException, NoSuchFieldException {
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
        System.out.println("String[].class getCanonicalName=" + String[].class.getCanonicalName());// class [Ljava.lang.String;
        System.out.println("String[].class getName=" + String[].class.getName());// class [Ljava.lang.String;
        System.out.println(
                "String[].class getCanonicalName=" + String[].class.getCanonicalName());// class [Ljava.lang.String;
        Class<?>[] mapClasses = mapClass.getClasses();
        for (Class<?> c : mapClasses)
            System.out.println("getClasses=" + c);
        System.out.println("String[].class getClasses=" + String[].class.getClasses());
        Annotation[] annotations = mapClass.getDeclaredAnnotations();
        for (Annotation a : annotations)
            System.out.println("getDeclaredAnnotations=" + a);
        Field[] fields = mapClass.getDeclaredFields();
        for (Field f : fields)
            System.out.println("getDeclaredFields=" + f.toGenericString());
//        Method[] methods = mapClass.getMethods();
//        for (Method m : methods)
//            System.out.println("getMethods=" + m.toGenericString());
            System.out.println("getClasses=" + c);
        Constructor<?>[] cons = mapClass.getConstructors();
        System.out.println("---------------------------------");
        for (Constructor<?> c : cons) {
            System.out.println("getConstructors=" + c.toGenericString());
            Class<?>[] pcs = c.getParameterTypes();
            for (Class cl : pcs)
                System.out.println("getParameterTypes=" + cl.toGenericString());
            TypeVariable<?>[] tvs = c.getTypeParameters();
            for (TypeVariable<?> tv : tvs)
                System.out.println("getTypeParameters=" + tv.getTypeName());
            Type[] types = c.getGenericParameterTypes();
            for (Type t : types)
                System.out.println("getGenericParameterTypes=" + t.getTypeName());
        }
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
        Constructor<HashMap<String, Integer>> con = (Constructor<HashMap<String, Integer>>) mapClass
                .getConstructor(Map.class);
        //        Class<HashMap<String, Integer>>[] params = (Class<HashMap<String, Integer>>[]) con.getParameterTypes();
        //        Class<?>[] params = con.getParameterTypes();
        //        Class<HashMap<String, Integer>> param0 = params[0];
        Type[] params = con.getGenericParameterTypes();
        Type param0 = params[0];
        System.out.println("is ParameterizedType=" + (param0 instanceof ParameterizedType));
        System.out.println("is TypeVariable=" + (param0 instanceof TypeVariable));
        if (param0 instanceof ParameterizedType) {
            ParameterizedType pp = (ParameterizedType) param0;
            Type[] typeVs = pp.getActualTypeArguments();
            for (Type tv : typeVs) {
                System.out.println("is WildcardType=" + (tv instanceof WildcardType));
            }
        }
        Class<?>[] expClazz = ReflectClassDemo.class.getMethod("reflectClass").getExceptionTypes();
        for (Class<?> ex : expClazz)
            System.out.println("getExceptionTypes=" + ex);
        Type[] expTypes = ReflectClassDemo.class.getMethod("reflectClass").getGenericExceptionTypes();
        for (Type ex : expTypes)
            System.out.println("getGenericExceptionTypes=" + ex);
        Method m = ReflectClassDemo.class.getMethod("method");
        System.out.println(m.getGenericExceptionTypes()[0]);
        System.out.println(m.getExceptionTypes()[0]);
        System.out.println("getAnnotatedReturnType=" + con.getAnnotatedReturnType().getType().toString());
        System.out.println("getAnnotatedReceiverType=" + con.getAnnotatedReceiverType().getType().toString());
        Method getM = mapClass.getMethod("get", Object.class);
        System.out.println("method getName()=" + getM.getName());
        System.out.println("getAnnotatedReturnType=" + getM.getAnnotatedReturnType().getType().toString());
        System.out.println("getReturnType=" + getM.getReturnType().toString());
        System.out.println("getAnnotatedReceiverType=" + getM.getAnnotatedReceiverType().getType().toString());
        System.out.println("---------------------------------");
        Method[] methods = mapClass.getMethods();
        for (Method method : methods) {
            if ("put".equals(method.getName())) {
                TypeVariable<Method>[] putTvs = method.getTypeParameters();
                for (TypeVariable<Method> tv : putTvs)
                    System.out.println("getTypeParameters=" + tv.getTypeName());
                System.out.println("getReturnType=" + method.getReturnType());
                System.out.println("getGenericReturnType=" + method.getGenericReturnType());

                Class<?>[] cparams = method.getParameterTypes();
                for (Class<?> cp : cparams)
                    System.out.println("method getParameterTypes=" + cp);

                Type[] gtypes = method.getGenericParameterTypes();
                for (Type gt : gtypes)
                    System.out.println("method getGenericParameterTypes=" + gt.getTypeName());
            }
            if ("putAll".equals(method.getName())) {
                Class<?>[] cparams = method.getParameterTypes();
                for (Class<?> cp : cparams)
                    System.out.println("putAll getParameterTypes=" + cp);

                Type[] gtypes = method.getGenericParameterTypes();
                for (Type gt : gtypes)
                    System.out.println("putAll getGenericParameterTypes=" + gt.getTypeName());
            }

            if ("isEmpty".equals(method.getName())) {
                System.out.println("isEmpty getReturnType=" + method.getReturnType());
                System.out.println("isEmpty getGenericReturnType=" + method.getGenericReturnType());
            }
        }

        System.out.println("---------------------------------");
        TypeVariable<? extends Class<? extends HashMap>>[] putTvs = mapClass.getTypeParameters();
        for (TypeVariable<? extends Class<? extends HashMap>> tv : putTvs)
            System.out.println("hashmap getTypeParameters=" + tv.getTypeName());
        System.out.println("---------------------------------");
        Method[] ttMehods = EhcacheServiceImpl.class.getMethods();
        for (Method tm : ttMehods) {
            if ("get".equals(tm.getName())) {
                System.out.println("getReturnType=" + tm.getReturnType());
                System.out.println("getGenericReturnType=" + tm.getGenericReturnType());
                TypeVariable<Method>[] tmm = tm.getTypeParameters();
                for (TypeVariable<Method> tv : tmm) {
                    System.out.println("method getTypeParameters=" + tv.getTypeName());
                }
                Class<?>[] cparams = tm.getParameterTypes();
                for (Class<?> cp : cparams)
                    System.out.println("method getParameterTypes=" + cp);

                Type[] gtypes = tm.getGenericParameterTypes();
                for (Type gt : gtypes)
                    System.out.println("method getGenericParameterTypes=" + gt.getTypeName());
            }
        }

        Field threshold = mapClass.getDeclaredField("table");
        System.out.println("field getType ="+threshold.getType());
        System.out.println("field getGenericType ="+threshold.getGenericType());
        System.out.println("field getClass ="+threshold.getClass());

        Annotation[] anns =threshold.getAnnotations();
        for (Annotation ann : anns)
            System.out.println("getAnnotations="+ann.toString());

        // Annotation ann = threshold.getAnnotation(String.class);




    }

    public static void typeVariable(List<Date> list) {
        System.out.println(list.getClass());
        System.out.println(list.getClass().getGenericSuperclass());
        Class<List<Date>> clazz = (Class<List<Date>>) list.getClass();
        TypeVariable tval[] = clazz.getTypeParameters();
        TypeVariable v = tval[0];
        //        Type t[] = v.getBounds();
        //        System.out.println(t.length);
        //        System.out.println(v.getGenericDeclaration().getTypeParameters()[0]);
        System.out.println(v.getGenericDeclaration());
        System.out.println(v.getName());

        System.out.println(tval.length);
        for (TypeVariable val : tval)
            System.out.println(val);
    }

    public static <T extends NoSuchFieldException> void method() throws T {
    }

    public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
        reflectClass();
        //        typeVariable(new ArrayList<>());
    }
}
