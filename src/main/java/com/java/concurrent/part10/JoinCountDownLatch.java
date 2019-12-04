package com.java.concurrent.part10;

import com.java.concurrent.common.SleepUtil;
import com.java.concurrent.common.ThreadPoolUtil;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * {@link CountDownLatch} 示例, CountDownLatch是基于AQS实现，调用 #countDown() 方法将 state值减1，直到state值为0，
 * 才从 await() 方法返回
 *
 * @author Mr.zxb
 * @date 2019-11-17 10:32
 */
public class JoinCountDownLatch {

    private static final CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = ThreadPoolUtil.getThreadPool();

        executorService.submit(() -> {
            SleepUtil.sleep(TimeUnit.SECONDS, 1);
            System.out.printf("%s 执行完成.\n", Thread.currentThread());
            countDownLatch.countDown();
        });

        executorService.submit(() -> {
            SleepUtil.sleep(TimeUnit.SECONDS, 1);
            System.out.printf("%s 执行完成.\n", Thread.currentThread());
            countDownLatch.countDown();
        });

        System.out.println("all child thread over.");
        // 等待子线程执行完毕
        countDownLatch.await();
        System.out.println("all thread over.");
        executorService.shutdown();
    }

}
