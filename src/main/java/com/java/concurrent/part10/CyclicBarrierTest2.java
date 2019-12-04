package com.java.concurrent.part10;

import com.java.concurrent.common.SleepUtil;
import com.java.concurrent.common.ThreadPoolUtil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * {@link CyclicBarrier}
 *
 * @author Mr.zxb
 * @date 2019-11-17 10:55
 */
public class CyclicBarrierTest2 {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
        System.out.println(Thread.currentThread() + " merge result.");
    });

    public static void main(String[] args) {

        ExecutorService executorService = ThreadPoolUtil.getThreadPool(2);

        executorService.execute(() -> {
            SleepUtil.sleep(TimeUnit.SECONDS, 1);
            System.out.printf("%s 执行任务.\n", Thread.currentThread());
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.printf("%s 完成任务.\n", Thread.currentThread());
        });

        executorService.execute(() -> {
            SleepUtil.sleep(TimeUnit.SECONDS, 1);
            System.out.printf("%s 执行任务.\n", Thread.currentThread());
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.printf("%s 完成任务.\n", Thread.currentThread());
        });

        System.out.println("------------");

        executorService.execute(() -> {
            System.out.println(Thread.currentThread() + " set1");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread() + " set2");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread() + " set3");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        executorService.execute(() -> {
            System.out.println(Thread.currentThread() + " set1");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread() + " set2");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread() + " set3");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });


        executorService.shutdown();
    }
}
