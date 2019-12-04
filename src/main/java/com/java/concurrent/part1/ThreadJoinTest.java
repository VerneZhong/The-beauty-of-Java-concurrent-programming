package com.java.concurrent.part1;

import com.java.concurrent.common.SleepUtil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程执行结束后调用native exit方法,exit方法会执行ensure_join，ensure_join里会lock.notify_all(thread);
 * 所以线程执行完后，会通知其他处于waiting的线程。
 * @author Mr.zxb
 * @date 2019-04-23 10:26
 */
public class ThreadJoinTest {

    public static void main(String[] args) throws InterruptedException {

        Thread threadOne = new Thread(() -> {
            SleepUtil.sleep(TimeUnit.SECONDS, 1);
            System.out.println("child threadOne 1 over.");
        });

        Thread threadTwo = new Thread(() -> {
            SleepUtil.sleep(TimeUnit.SECONDS, 1);
            System.out.println("child threadTwo 2 over.");
        });

        // start child threads
//        threadOne.start();
//        threadTwo.start();

        // 类似于join的实现
//        threadStartAndWait(threadOne);
//        threadStartAndWait(threadTwo);

        // 基于sleep的实现
        threadSleep(threadOne);
        threadSleep(threadTwo);

        // wait child thread Finished return
        // join方法后被阻塞，直到线程执行完毕后返回
//        while (threadOne.isAlive()) {
//            自旋的方式
//        }
//        while (threadTwo.isAlive()) {
//
//        }

        // java join的方式
//        threadOne.join();
//        threadTwo.join();

        System.out.println("all child thread over.");
    }

    public static void threadStartAndWait(Thread thread) {
        if (Thread.State.NEW.equals(thread.getState())) {
            thread.start();
        }

        // Java Thread 对象和实际 JVM 执行的 OS Thread 不是相同对象
        // JVM Thread 回调 Java Thread.run() 方法，
        // 同时 Thread 提供一些 native 方法来获取 JVM Thread 状态
        // 当 JVM Thread 执行后，自动就 notify() 了
        // Thread 特殊的 Object
        while (thread.isAlive()) {
            // 当线程 Thread isAlive() == false 时，thread.wait() 操作会被自动释放
            synchronized (thread) {
                try {
                    // 到底是谁通知 Thread -> thread.notify()
                    thread.wait();
//                    LockSupport.park(); // 死锁发生
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void threadSleep(Thread thread) throws InterruptedException {

        thread.start();

        while (thread.isAlive()) {
            // sleep
            Thread.sleep(0);
        }
    }



}
