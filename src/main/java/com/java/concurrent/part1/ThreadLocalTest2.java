package com.java.concurrent.part1;

/**
 * ThreadLocal不支持继承性
 * @author Mr.zxb
 * @date 2019-06-06 15:00
 */
public class ThreadLocalTest2 {

    /**
     * 创建线程变量
     */
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {

        // 主线程设置线程变量
        threadLocal.set("100");

        // 子线程是无法获取到父线程设置的本地变量，因为子线程调用get方法是thread线程，而set方法里设置是的main线程，两者是不同的线程
        // 所以子线程访问的是null
        final Thread thread = new Thread(() -> System.out.println("child thread:" + threadLocal.get()), "thread-1");

        thread.start();

        System.out.println("main:" + threadLocal.get());
    }
}
