package com.haole.core.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by shengjunzhao on 2018/12/2.
 */
public class ProxyFactory {

    private Object tar;

    public ProxyFactory(Object target) {
        this.tar=target;
    }

    public Object createSubjectProxy() {
        return Proxy.newProxyInstance(this.tar.getClass().getClassLoader(),this.tar.getClass().getInterfaces(),new InvocationHandler(){

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = null;//被代理的类型为Object基类
                Class<?> clazz = proxy.getClass();
                System.out.println(clazz.getCanonicalName());
                System.out.println(tar.getClass().getCanonicalName());
                System.out.println("被代理....");
                result = method.invoke(tar, args);
                //在调用具体函数方法后，执行功能处理
                return result;
            }
        });
    }
}
