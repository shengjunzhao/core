package com.haole.core.reflect.demo;

/**
 * Created by shengjunzhao on 2018/11/24.
 */
public class Main {

    private static class Inner {
    }
    static void checkSynthetic (String name) {
        try {
            System.out.println (name + " : " + Class.forName (name).isSynthetic ());
        } catch (ClassNotFoundException exc) {
            exc.printStackTrace (System.out);
        }
    }
    public static void main(String[] args) throws Exception
    {
        new Inner ();  // 如无此句，则checkSynthetic ("com.haole.core.reflect.demo.Main$1"); 报错
        checkSynthetic ("com.haole.core.reflect.demo.Main");
        checkSynthetic ("com.haole.core.reflect.demo.Main$Inner");
        checkSynthetic ("com.haole.core.reflect.demo.Main$1");
    }

    /**
     * com.haole.core.reflect.demo.Main : false
     * com.haole.core.reflect.demo.Main$Inner : false
     * com.haole.core.reflect.demo.Main$1 : true
     */

}
