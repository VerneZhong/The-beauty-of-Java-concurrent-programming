package com.java.concurrent.part1;

import com.java.concurrent.common.SleepUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author Mr.zxb
 * @date 2019-04-23 15:35
 */
public class ThreadJoinAsInterrupt {

    public static void main(String[] args) throws InterruptedException {

        // 子线程1
        Thread thread = new Thread(() -> {
            System.out.println("threadOne begin run.");
            while (true) {

            }
        });

        // 主线程
        Thread current = Thread.currentThread();

        Thread thread2 = new Thread(() -> {
            SleepUtil.sleep(TimeUnit.SECONDS, 1);
            // 中断主线程，为什么中断主线程？
            // 当前主线程处于WAITING状态，thread2还未创建，只能中断主线程
            current.interrupt();
        });

        thread.start();
        thread2.start();

        thread.join();
    }
}
