package com.haole.core.reflect.annotation.lx;

import java.lang.annotation.*;

/**
 * 使用Java8新增@Repeatable原注解
 * Created by shengjunzhao on 2018/11/18.
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
//@Inherited //添加可继承元注解
@Repeatable(FilterPaths.class)
public @interface FilterPath {
    String value();
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
//@Inherited //添加可继承元注解
@interface FilterPaths {
    FilterPath[] value();
}

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
//@Inherited //添加可继承元注解
@interface FilterPathCommon {
    String value();
}

@FilterPath("/web/list")
// @FilterPath("/web/list1")
@FilterPathCommon("/web/list1")
class CC {
}

//使用案例
@FilterPath("/web/update")
@FilterPath("/web/add")
@FilterPath("/web/delete")
@FilterPathCommon("/web/common")
class AA extends CC {
    public static void main(String[] args) {

        Class<?> clazz = AA.class;
        //通过getAnnotationsByType方法获取所有重复注解
        FilterPath[] annotationsByType = clazz.getAnnotationsByType(FilterPath.class);
        FilterPath[] annotationsByType2 = clazz.getDeclaredAnnotationsByType(FilterPath.class);
        FilterPathCommon[] annotationsByType3 = clazz.getAnnotationsByType(FilterPathCommon.class);
        if (annotationsByType != null) {
            for (FilterPath filter : annotationsByType) {
                System.out.println("1:" + filter.value());
            }
        }
        System.out.println("-----------------");
        if (annotationsByType2 != null) {
            for (FilterPath filter : annotationsByType2) {
                System.out.println("2:" + filter.value());
            }
        }
        System.out.println("-----------------");
        if (annotationsByType3 != null) {
            for (FilterPathCommon filter : annotationsByType3) {
                System.out.println("3:" + filter.value());
            }
        }

        System.out.println("使用getAnnotation的结果:" + clazz.getAnnotation(FilterPath.class));
        System.out.println("使用getAnnotation的结果:" + clazz.getAnnotation(FilterPathCommon.class));

        /**
         * 执行结果(当前类拥有该注解FilterPath,则不会从CC父类寻找)
         1:/web/update
         1:/web/add
         1:/web/delete
         -----------------
         2:/web/update
         2:/web/add
         2:/web/delete
         使用getAnnotation的结果:null
         */


    }
}
