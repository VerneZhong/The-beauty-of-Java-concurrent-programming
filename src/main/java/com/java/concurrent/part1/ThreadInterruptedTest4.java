package com.java.concurrent.part1;

/**
 * @author Mr.zxb
 * @date 2019-06-06 11:25
 */
public class ThreadInterruptedTest4 {

    public static void main(String[] args) throws InterruptedException {

        final Thread thread = new Thread(() -> {
            // 清除了当前线程的中断标志，中断标志为true，退出循环
            while (!Thread.currentThread().interrupted()) {

            }
            System.out.println("thread-1 isInterrupted: " + Thread.currentThread().isInterrupted());
        }, "thread-1");

        // 启动线程
        thread.start();

        // 设置中断标志，将中断标识设为true
        thread.interrupt();

        thread.join();
        System.out.println("main is over.");
    }
}
