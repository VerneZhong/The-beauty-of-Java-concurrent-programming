package com.java.concurrent.part4;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

/**
 * @author Mr.zxb
 * @date 2019-06-17 09:39
 */
public class AtomicLongTest {

    private static AtomicLong atomicLong = new AtomicLong();

    public static void main(String[] args) throws InterruptedException {

        final Thread thread1 = new Thread(() -> {
            int[] randomArray = getRandomArray();
            for (int i = 0; i < randomArray.length; i++) {
                if (randomArray[i] == 0) {
                    atomicLong.incrementAndGet();
                }
            }
        });

        final Thread thread2 = new Thread(() -> {
            int[] randomArray = getRandomArray();
            for (int i = 0; i < randomArray.length; i++) {
                if (randomArray[i] == 0) {
                    atomicLong.incrementAndGet();
                }
            }
        });
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("count 0 = " + atomicLong.get());

    }

    public static int[] getRandomArray() {
        return IntStream.generate(() -> ThreadLocalRandom.current().nextInt(5)).limit(10).toArray();
    }
}
