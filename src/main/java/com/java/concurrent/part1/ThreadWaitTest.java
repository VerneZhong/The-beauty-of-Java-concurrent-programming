package com.java.concurrent.part1;

import java.util.concurrent.TimeUnit;

/**
 * 需要注意的是：当前线程调用共享变量的wait方法后，只会释放当前共享变量上的锁，
 * 如果当前线程还持有其他共享变量的锁，则这些锁是不会被释放
 * @author Mr.zxb
 * @date 2019-04-22 15:51
 */
public class ThreadWaitTest {

    /**
     * lock资源
     */
    private static volatile Object lockA = new Object();
    private static volatile Object lockB = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread threadA = new Thread(() -> {
            // 获取lockA共享资源的监视器锁
            synchronized (lockA) {
                System.out.println("ThreadA get lock A");

                // 获取lockB共享资源的监视器锁
                synchronized (lockB) {
                    System.out.println("ThreadA get lock B");
                    try {
                        // 线程A阻塞，并释放lockA的监视器锁
                        System.out.println("ThreadA release lock A");
                        lockA.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "ThreadA");

        Thread threadB = new Thread(() -> {

            // 休眠1s
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 获取lockA共享资源的监视器锁
            synchronized (lockA) {
                System.out.println("ThreadB get lock A");

                System.out.println("ThreadB try get lock B...");
                // 获取lockB共享资源的监视器锁
                synchronized (lockB) {
                    System.out.println("ThreadB get lock B");

                    try {
                        // 线程A阻塞，并释放lockA的监视器锁
                        System.out.println("ThreadB release lock B");
                        lockA.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "ThreadB");

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();

        System.out.println("Main over.");
    }

}
