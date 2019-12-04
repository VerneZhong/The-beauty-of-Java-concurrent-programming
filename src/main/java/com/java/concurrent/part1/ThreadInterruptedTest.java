package com.java.concurrent.part1;

import com.java.concurrent.common.SleepUtil;

import java.util.concurrent.TimeUnit;

/**
 * 线程中断
 * void interrupt()：中断线程，该方法仅仅是设置中断标志，会在wait、join和sleep方法阻塞的时候中断后抛出{@link InterruptedException}异常而返回
 * boolean isInterrupted()：检测当前线程是否被中断，并不会清除中断标志
 * boolean interrupted()：检测当前线程是否被中断，与isInterrupted方法不同的是，该方法如果发现当前线程被中断，则会清除中断标志，该方法是个static方法，
 * 可以通过Thread类直接调用，而interrupted()方法的内部是获取当前调用线程的中断标志而不是调用interrupted()方法的实例对象的中断标志
 * @author Mr.zxb
 * @date 2019-06-06 10:35
 */
public class ThreadInterruptedTest {

    public static void main(String[] args) throws InterruptedException {

        final Thread thread = new Thread(() -> {
            // 如果当前线程被中断则退出
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread() + " hello");
            }
        }, "thread-1");

        // 启动线程
        thread.start();

        // 主线程休眠1s，中断子线程
        SleepUtil.sleep(TimeUnit.SECONDS, 1);

        // 中断子线程
        System.out.println("main thread interrupt child thread");
        thread.interrupt();

        // 等待子线程执行完毕
        thread.join();
        System.out.println("main is over");
    }
}
