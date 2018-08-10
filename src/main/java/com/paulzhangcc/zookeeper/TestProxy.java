package com.paulzhangcc.zookeeper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author paul
 * @description
 * @date 2018/8/9
 */
public class TestProxy {
    public static void main(String[] args) {
        System.out.println(TestEnum.one);

           Foo f = (Foo) Proxy.newProxyInstance(Foo.class.getClassLoader(),
                   new Class[] { Foo.class },new InvocationHandler(){

                       @Override
                       public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                           return "hello";
                       }
                   });
        String zjf = f.print("zjf");
        System.out.println(zjf);
    }

}
interface Foo{
    String print(String str);
}
