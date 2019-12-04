package com.java.concurrent.part6;

import com.java.concurrent.common.SleepUtil;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Mr.zxb
 * @date 2019-07-02 10:20
 */
public class CyclicBarrierTest {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(6, () -> {
            System.out.println("parent thread");
        });

        final List<Thread> threads = IntStream.range(0, 5).mapToObj(i -> new Thread(() -> {
            SleepUtil.sleep(TimeUnit.SECONDS, i);
            System.out.println(Thread.currentThread().getName() + " Ready");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "thread" + i)).collect(Collectors.toList());

        threads.forEach(Thread::start);

        cyclicBarrier.await();
        System.out.println("all threads ready.");
    }
}
