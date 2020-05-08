package com.jia.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//模拟一个消费者
public class Client {


    public static void main(String[] args) {
        final Producer producer=new Producer();
      //  producer.saleProduct(10000);


        /**
         * 动态代理的特点：
         * 字节码随用随创建，随用随加载
         * 动态代理的作用：
         * 不修改源码的基础上，增强方法
         * 动态代理的分类：
         *动态代理的分类：
         * 1.基于接口的
         *   涉及的类：Proxy
         *   提供者：JDK 官方
         *   创建代理对象：使用 Proxy 类中的 newProxyInstance 方法，
         *     要求：被代理类至少实现一个接口，否则不能使用
         * newProxyInstance 的参数：①ClassLoader 类加载器
         *                          用于加载代理对象字节码，和被代理对象使用相同的类加载器
         *                         ②Class[] 字节码数组
         *                          用于让代理对象和被代理对象有相同方法
         *                         ③InvocationHandler 用于提供增强的代码
         *                          让写如何代理的,一般写该接口的实现类，通常情况下都是匿名内部类
         *        2.基于子类的
         *
         *
         */


        IProducer proxyProducer=(IProducer)Proxy.newProxyInstance(producer.getClass().getClassLoader(), producer.getClass().getInterfaces(), new InvocationHandler() {

            //执行被代理对象的任何接口方法都会经过该方法，即该方法有拦截的功能

            /**
             *
             * @param proxy 代理对象的引用
             * @param method 当前执行的方法
             * @param args 当前执行方法所需的参数
             * @return 和被代理对象有相同的返回值
             * @throws Throwable
             */
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //提供增强的代码

                Object returnValue=null;
               //1.获取方法执行的参数
                Float money=(Float)args[0];

                //2.判断当前方法是不是销售
                if("saleProduct".equals(method.getName()))

                    //对于生产者来说，只能拿到八成的钱
                            returnValue=method.invoke(producer,money*(float)0.8);
                return returnValue;
            }

        });

proxyProducer.saleProduct(10000);

    }
}
