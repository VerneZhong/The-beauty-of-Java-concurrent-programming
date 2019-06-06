package com.java.concurrent.part1;

import com.java.concurrent.common.SleepUtil;

import java.util.concurrent.TimeUnit;

/**
 * 线程sleep状态被中断
 * @author Mr.zxb
 * @date 2019-06-06 11:04
 */
public class ThreadInterruptedTest2 {

    public static void main(String[] args) throws InterruptedException {

        final Thread thread = new Thread(() -> {
            System.out.println("thread begin sleep for 2000s");
            SleepUtil.sleep(TimeUnit.SECONDS, 2);
            System.out.println("thread awaking.");
        }, "thread-1");

        // 启动线程
        thread.start();

        // 确保子线程进入休眠状态
        SleepUtil.sleep(TimeUnit.SECONDS, 1);

        // 中断子线程的休眠状态
        thread.interrupt();

        // 等待子线程返回
        thread.join();

        System.out.println("main thread is over.");
    }
}
