package com.java.concurrent.dailyquestion;

/**
 * 每日一题：静态变量的坑，出现死锁互相等待
 * @author Mr.zxb
 * @date 2019-07-18 13:45
 */
public class Lazy {

    private static boolean initialized = false;

    static {
        //initialized = true
        Thread t = new Thread(() -> {});
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new AssertionError(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(initialized);
    }
}
