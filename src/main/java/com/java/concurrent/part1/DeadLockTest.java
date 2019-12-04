package com.java.concurrent.part1;

import com.java.concurrent.common.SleepUtil;


import java.util.concurrent.TimeUnit;

/**
 * 死锁：是指两个或两个以上的线程在执行过程中，因争夺资源而造成的互相等待的现象，在无外力作用的情况下，会一直等待下去
 * 死锁的产生必须具备的4个条件：
 * 1.互斥条件：指线程已经获取到资源进行排他性使用，即该资源同时只由一个线程占用.
 * 2.请求并持有条件：指一个线程已经持有了至少一个资源，但又提出了新的资源请求，而新资源已被其他线程占有，所以当前线程会被阻塞，阻塞的同时并不会释放自己已获取的资源
 * 3.不可剥夺条件：指线程获取到的资源在自己使用完之前不能被其他线程抢占，只有自己使用完毕后才由自己释放该资源.
 * 4.环路等待条件：指在发生死锁时，比如存在一个线程一资源的环形链.
 * @author Mr.zxb
 * @date 2019-06-06 14:00
 */
public class DeadLockTest {

    private static Object resourceA = new Object();
    private static Object resourceB = new Object();

    public static void main(String[] args) {

        final Thread threadA = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println(Thread.currentThread() + " get resourceA");
                SleepUtil.sleep(TimeUnit.SECONDS, 1);
                System.out.println(Thread.currentThread() + " waiting get resourceB");
                synchronized (resourceB) {
                    System.out.println(Thread.currentThread() + " get resourceB");
                }
                System.out.println(Thread.currentThread() + " release resourceB");
            }
            System.out.println(Thread.currentThread() + " release resourceA");
        }, "threadA");

        // 将资源改成有顺性就能避免死锁，资源的有序性破坏了资源的请求并持有条件和环路等待条件，因此避免了死锁.
        final Thread threadB = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println(Thread.currentThread() + " get resourceB");
                SleepUtil.sleep(TimeUnit.SECONDS, 1);
                System.out.println(Thread.currentThread() + " waiting get resourceA");
                synchronized (resourceB) {
                    System.out.println(Thread.currentThread() + " get resourceA");
                }
                System.out.println(Thread.currentThread() + " release resourceA");
            }
            System.out.println(Thread.currentThread() + " release resourceB");
        }, "threadB");

        threadA.start();
        threadB.start();
    }

}
