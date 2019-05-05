package com.java.concurrent.part1;

import com.java.concurrent.common.SleepUtil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread类的静态sleep方法，调用该方法的线程会暂时让出指定时间的执行权，这期间不参与CPU调度，
 * 但是该线程所持有的监视器资源是不会释放的。
 *
 * @author Mr.zxb
 * @date 2019-04-23 15:44
 */
public class ThreadSleepTest {

    /**
     * 独占锁
     */
    private static final Lock LOCK = new ReentrantLock();

    public static void main(String[] args) {

        Thread threadA = new Thread(() -> {
            // 获取独占锁
            LOCK.lock();
            try {
                System.out.println("child threadA is in sleep.");
                SleepUtil.sleep(TimeUnit.SECONDS, 2);
                System.out.println("child threadA is in waked.");
            } finally {
                // 释放锁
                LOCK.unlock();
            }
        });

        Thread threadB = new Thread(() -> {
            LOCK.lock();
            try {
                System.out.println("child threadB is in sleep.");
                SleepUtil.sleep(TimeUnit.SECONDS, 2);
                System.out.println("child threadB is in waked.");
            } finally {
                LOCK.unlock();
            }
        });

        threadA.start();
        threadB.start();
    }
}
