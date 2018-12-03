package com.haole.core.proxy;

/**
 * Created by shengjunzhao on 2018/12/2.
 */
public class RealSubject implements Subject {
    @Override
    public void doSomething() {
        System.out.println( "call doSomething()" );
    }
}
