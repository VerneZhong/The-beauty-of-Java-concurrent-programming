package com.java.concurrent.part1;

import java.util.concurrent.TimeUnit;

/**
 * @author Mr.zxb
 * @date 2019-04-22 16:42
 */
public class ThreadWaitNotifyTest {

    /**
     * 共享资源
     */
    private static volatile Object lockA = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread threadA = new Thread(() -> {
            // 获取lockA共享资源的监视器锁
            synchronized (lockA) {
                System.out.println("threadA get lockA");
                System.out.println("threadA begin wait");
                try {
                    lockA.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("threadA end wait");
            }
        });

        Thread threadB = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("threadB get lockA");
                System.out.println("threadB begin wait");
                try {
                    lockA.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("threadB end wait");
            }
        });

        Thread threadC = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("threadC begin notify");
                // nofity方法只会唤醒一个该共享变量上被挂起的线程
//                lockA.notify();
                // notifyAll会唤醒所有在调用前被该共享变量上挂起的线程，注意之后被挂起的线程不会被唤醒。
                lockA.notifyAll();
            }
        });

        threadA.start();
        threadB.start();

        // 注释主线程休眠,就会发现notifyAll之后wait的线程不会被唤醒.依然处于WAITING状态
        TimeUnit.SECONDS.sleep(1);

        threadC.start();

        // 线程是否存活，自旋的方式
        while (threadA.isAlive() || threadB.isAlive() || threadC.isAlive()) {}
//        threadA.join();
//        threadB.join();
//        threadC.join();

        System.out.println("Main over");
    }

}
