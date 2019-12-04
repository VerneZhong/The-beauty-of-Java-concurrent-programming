package com.java.concurrent.part10;

import com.java.concurrent.common.SleepUtil;

import java.util.concurrent.TimeUnit;

/**
 * 自定义 CountDownLatch class
 *
 * @author Mr.zxb
 * @date 2019-12-03 14:57
 */
public class CountDownLatchOnWait {

    private int count;

    private Object lock = new Object();

    public CountDownLatchOnWait(int count) {
        this.count = count;
    }

    private void await() throws InterruptedException {
        synchronized (lock) {
            while (count != 0) {
                lock.wait();
            }
        }
    }

    private void countDown() {
        synchronized (lock) {
            if (count > 0) {
                count--;
            }
            if (this.count == 0) {
                lock.notifyAll();
            }
        }
    }

    public int getCount() {
        return this.count;
    }

    public static void main(String[] args) {

        CountDownLatchOnWait countDownLatchOnWait = new CountDownLatchOnWait(2);

        new Thread(() -> {
            try {
                countDownLatchOnWait.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "线程执行完毕");
        }).start();

        SleepUtil.sleep(TimeUnit.SECONDS, 2);
        System.out.println("Main thread 放行。");
        countDownLatchOnWait.countDown();
    }
}
