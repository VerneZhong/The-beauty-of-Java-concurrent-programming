package com.java.concurrent.part1;

/**
 * 了解 interrupted()和isInterrupted()的区别
 * @author Mr.zxb
 * @date 2019-06-06 11:16
 */
public class ThreadInterruptedTest3 {

    public static void main(String[] args) {

        final Thread thread = new Thread(() -> {
            while (true) {

            }
        }, "thread-1");

        // 启动线程
        thread.start();

        // 设置中断标志
        thread.interrupt();

        // 获取中断标志  true
        System.out.println("isInterrupted: " + thread.isInterrupted());

        // 获取中断标志并重置 false ，是获取当前线程也就是主线程的中断状态
        System.out.println("interrupted: " + thread.interrupted());

        // 获取中断标志并重置 false ，是获取当前线程也就是主线程的中断状态
        System.out.println("interrupted: " + Thread.interrupted());

        // 获取中断标志 true
        System.out.println("isInterrupted: " + thread.isInterrupted());
    }
}
