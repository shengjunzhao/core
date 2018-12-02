package com.haole.core.proxy;

/**
 * Created by shengjunzhao on 2018/12/2.
 */
public class ProxyDemo {

    public static void main(String[] args) {
//        ProxyHandler proxy = new ProxyHandler();
//        //绑定该类实现的所有接口
//        Subject sub = (Subject) proxy.bind(new RealSubject());
        ProxyFactory factory = new ProxyFactory(new RealSubject());
        Subject sub = (Subject)factory.createSubjectProxy();
        sub.doSomething();
    }

}
