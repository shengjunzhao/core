package com.haole.core.reflect.demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by shengjunzhao on 2018/11/24.
 */
public class EnclosingOuter {

    public EnclosingOuter() {
        // 构造方法中的匿名内部类
        InnerClass innerClass = new InnerClass() {
            @Override
            public void fun() {
                getEnclosing(this.getClass());
                /**
                 * enclosingClass=class com.haole.core.reflect.demo.EnclosingOuter
                 * enclosingConstructor==public com.haole.core.reflect.demo.EnclosingOuter()
                 * enclosingMethod=null
                 */
            }
        };
        innerClass.fun();
    }

    // 匿名内部类
    static InnerClass innerClass = new InnerClass() {
        public void fun() {
            getEnclosing(this.getClass());
            /**
             * enclosingClass=class com.haole.core.reflect.demo.EnclosingOuter
             * enclosingConstructor=null
             * enclosingMethod=null
             */
        }

        ;
    };

    public static void test() {
        // 方法中的匿名内部类
        InnerClass innerClass = new InnerClass() {
            @Override
            public void fun() {
                getEnclosing(this.getClass());
                /**
                 * enclosingClass=class com.haole.core.reflect.demo.EnclosingOuter
                 * enclosingConstructor=null
                 * enclosingMethod=public static void com.haole.core.reflect.demo.EnclosingOuter.test()
                 */

            }
        };
        innerClass.fun();
    }

    // 内部类
    public static class InnerClass {
        public InnerClass() {

        }

        public void fun() {

        }
    }

    public static void main(String[] args) {
        System.out.println("------内部类------");
        getEnclosing(InnerClass.class);

        System.out.println("------匿名内部类------");
        innerClass.fun();

        System.out.println("------方法中的匿名内部类------");
        EnclosingOuter.test();

        System.out.println("------构造函数中的匿名内部类------");
        new EnclosingOuter();

    }

    /**
     * getEnclosingClass:该类是在那个类中定义的， 比如直接定义的内部类或匿名内部类
     * getEnclosingConstructor：该类是在哪个构造函数中定义的，比如构造方法中定义的匿名内部类
     * getEnclosingMethod：该类是在哪个方法中定义的，比如方法中定义的匿名内部类
     *
     * @param cls
     */
    private static void getEnclosing(Class cls) {
        Class enclosingClass = cls.getEnclosingClass();
        Constructor enclosingConstructor = cls.getEnclosingConstructor();
        Method enclosingMethod = cls.getEnclosingMethod();
        System.out.println("getName=" + cls.getName());
        System.out.println("getTypeName=" + cls.getTypeName());
        System.out.println("getCanonicalName=" + cls.getCanonicalName());
        System.out.println("enclosingClass=" + enclosingClass);
        System.out.println("enclosingConstructor=" + enclosingConstructor);
        System.out.println("enclosingMethod=" + enclosingMethod);

    }

    private static void getDecalared() {

    }

}



