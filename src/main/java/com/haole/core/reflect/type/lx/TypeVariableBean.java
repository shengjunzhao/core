package com.haole.core.reflect.type.lx;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

/**
 * Created by shengjunzhao on 2018/11/18.
 */
public class TypeVariableBean<K extends InputStream & Closeable, V> {
    // K 的上边界是 InputStream
    K key;  // TypeVariable
    // 没有指定的话 ，V 的 上边界 属于 Object
    V value; // TypeVariable
    // 不属于 TypeTypeVariable
    V[] values;  // ParameterizedType
    String str;
    List<K> kList;  // ParameterizedType

    public static void main(String[] args) throws NoSuchFieldException {
        TypeVariableBean bean = new TypeVariableBean<FileInputStream, String>();
        Field fk = TypeVariableBean.class.getDeclaredField("key");
        TypeVariable keyType = (TypeVariable) fk.getGenericType();
        System.out.println(keyType.getName());
        System.out.println(keyType.getGenericDeclaration());
        System.out.println(keyType.getBounds().length);
        Type[] bounds = keyType.getBounds();
        for (Type type : bounds)
            System.out.println(type);
        AnnotatedType[] aTypes = keyType.getAnnotatedBounds();
        for (AnnotatedType aType : aTypes)
            System.out.println(aType.getAnnotations());
    }


}
