package com.example.xunyou.tool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyInvocationHandle implements InvocationHandler {

    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }


    //生成代理类
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), target.getClass().getInterfaces(),this);
    }




    public void log(String methodName){
        System.out.println("执行了"+methodName+"方法");
    }

    //proxy:代理类
    //method:代理类的调用处理程序的方法对象
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log(method.getName());
        Object result = method.invoke(target,args);
        return result;
    }
}
