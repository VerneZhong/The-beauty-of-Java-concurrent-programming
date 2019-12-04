package com.java.concurrent.common;

import java.util.concurrent.TimeUnit;

/**
 * @author Mr.zxb
 * @date 2019-04-23 10:27
 */
public class SleepUtil {

    public static void sleep(TimeUnit unit, long time) {
        try {
            unit.sleep(time);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread() + " be interrupted.");
        }
    }
}
