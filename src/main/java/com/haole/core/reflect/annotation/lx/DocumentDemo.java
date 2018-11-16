package com.haole.core.reflect.annotation.lx;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * ClassName: DocumentDemo
 * Description:
 * Author: shengjunzhao
 * Date: 2018/11/16 17:02
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

@DocumentA
class A {
}

@DocumentB
public class DocumentDemo extends A {

    public static void main(String... args) {

        Class<?> clazz = DocumentDemo.class;
        //根据指定注解类型获取该注解
        DocumentA documentA = clazz.getAnnotation(DocumentA.class);
        System.out.println("A:" + documentA);

        //获取该元素上的所有注解，包含从父类继承
        Annotation[] an = clazz.getAnnotations();
        System.out.println("an:" + Arrays.toString(an));
        //获取该元素上的所有注解，但不包含继承！
        Annotation[] an2 = clazz.getDeclaredAnnotations();
        System.out.println("an2:" + Arrays.toString(an2));

        //判断注解DocumentA是否在该元素上
        boolean b = clazz.isAnnotationPresent(DocumentA.class);
        System.out.println("b:" + b);

        /**
         * 执行结果:
         A:@com.haole.core.reflect.annotation.lx.DocumentA()
         an:[@com.haole.core.reflect.annotation.lx.DocumentA(), @com.haole.core.reflect.annotation.lx.DocumentB()]
         an2:[@com.haole.core.reflect.annotation.lx.DocumentB()]
         b:true
         */
    }
}
