package com.haole.core.reflect.type.lx;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * ClassName: TestHelper
 * Description:
 * Author: shengjunzhao
 * Date: 2018/11/15 14:46
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class TestHelper {
    public static void testParameterizedType() {
        Field f = null;
        try {
            Field[] fields = ParameterizedTypeBean.class.getDeclaredFields();
            // 打印出所有的 Field 的 TYpe 是否属于 ParameterizedType
            for (int i = 0; i < fields.length; i++) {
                f = fields[i];
                System.out.println(f.getName() + " getGenericType() instanceof ParameterizedType " + (f
                        .getGenericType() instanceof ParameterizedType));
//                System.out.println(f.getName() + " getGenericType() instanceof TypeVariable " + (f
//                        .getGenericType() instanceof TypeVariable));
            }
            System.out.println("---------------------");
            getParameterizedTypeMes("map");
            System.out.println("---------------------");
            getParameterizedTypeMes("entry");

        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void getParameterizedTypeMes(String fieldName) throws NoSuchFieldException {
        Field f;
        f = ParameterizedTypeBean.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        System.out.println(f.getGenericType());
        boolean b = f.getGenericType() instanceof ParameterizedType;
        System.out.println(b);
        if (b) {
            ParameterizedType pType = (ParameterizedType) f.getGenericType();
            System.out.println(pType.getRawType());
            for (Type type : pType.getActualTypeArguments()) {
                System.out.println(type);
            }
            System.out.println(pType.getOwnerType()); // null
        }
    }

    public static void main(String[] args) {
        testParameterizedType();

    }

}
