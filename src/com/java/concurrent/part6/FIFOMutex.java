package com.java.concurrent.part6;

import com.java.concurrent.common.SleepUtil;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * 先进先出的锁，也就只有队列的首元素可以获取锁
 * @author Mr.zxb
 * @date 2019-06-18 16:47
 */
public class FIFOMutex {

    private final AtomicBoolean locked = new AtomicBoolean(false);

    private final Queue<Thread> waiters = new ConcurrentLinkedQueue<>();

    public void lock() {
        boolean wasInterrupted = false;

        final Thread current = Thread.currentThread();
        waiters.add(current);

        // 只有队首的线程可以获取锁
        // 如果当前线程不是队首或者当前锁已经被其他线程获取，则调用park方法挂起自己
        while (waiters.peek() != current || !locked.compareAndSet(false, true)) {
            LockSupport.park(this);
            // 如果park方法是被中断返回，则忽略中断，并且重置中断标志，做个标记，然后再次判断当前线程是不是队首元素或者当前锁是否已经被其他线程获取
            // 如果是则继续调用park方法挂起自己
            if (Thread.interrupted()) {
                wasInterrupted = true;
            }
        }
        waiters.remove();
        // 判断标记，如果标记为true则中断该线程，其实就是其他线程中断了该线程，虽然我对中断信号不感兴趣，忽略它，但是不代表其他线程对该标志不感兴趣。
        if (wasInterrupted) {
            current.interrupt();
        }
    }

    public void unlock() {
        locked.set(false);
        // 唤醒调用park后阻塞的线程
        LockSupport.unpark(waiters.peek());
    }

    public static void main(String[] args) throws InterruptedException {

        FIFOMutex mutex = new FIFOMutex();

        final Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " get lock.");
            // 获取锁
            mutex.lock();

            SleepUtil.sleep(TimeUnit.SECONDS, 2);
            System.out.println(Thread.currentThread().getName() + " release lock.");
            // 释放锁
            mutex.unlock();
        }, "thread-1");

        final Thread thread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " get lock.");
            // 获取锁
            mutex.lock();

            SleepUtil.sleep(TimeUnit.SECONDS, 1);
            System.out.println(Thread.currentThread().getName() + " release lock.");
            // 释放锁
            mutex.unlock();
        }, "thread-2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("main over.");
    }
}
