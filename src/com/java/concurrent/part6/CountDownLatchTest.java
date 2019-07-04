package com.java.concurrent.part6;

import com.java.concurrent.common.SleepUtil;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Mr.zxb
 * @date 2019-07-02 09:24
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {

        final CountDownLatch countDownLatch = new CountDownLatch(5);

        final List<Thread> threads = IntStream.range(0, 5).mapToObj(i -> new Thread(() -> {
            SleepUtil.sleep(TimeUnit.SECONDS, i);
            System.out.println(Thread.currentThread().getName() + " Ready");
            countDownLatch.countDown();
        }, "thread" + i)).collect(Collectors.toList());

        threads.forEach(Thread::start);

        countDownLatch.await();
        System.out.println("all threads ready.");
    }
}
