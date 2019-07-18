package com.java.concurrent.dailyquestion;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Mr.zxb
 * @date 2019-07-12 10:05
 */
public class Reflector {

    public static void main(String[] args) throws Exception {

        Set s = new HashSet();
        s.add("foo");
        Iterator i = s.iterator();
        Method m = i.getClass().getMethod("hasNext", new Class[0]);
        // public final方法不允许反射调用
        m.setAccessible(false);
        System.out.println(m.invoke(i, new Object[0]));
    }
}
