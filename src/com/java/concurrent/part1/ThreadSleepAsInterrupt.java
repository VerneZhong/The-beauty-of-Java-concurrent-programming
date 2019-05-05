package com.java.concurrent.part1;

import com.java.concurrent.common.SleepUtil;

import java.util.concurrent.TimeUnit;

/**
 * 线程在睡眠期间，主线程中断了它，会抛出{@link InterruptedException}异常
 * @author Mr.zxb
 * @date 2019-04-23 15:57
 */
public class ThreadSleepAsInterrupt {

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            System.out.println("child thread is in sleep.");
            SleepUtil.sleep(TimeUnit.SECONDS, 5);
            System.out.println("child thread is in waked.");
        });

        thread.start();

        SleepUtil.sleep(TimeUnit.SECONDS, 2);

        thread.interrupt();
    }
}
