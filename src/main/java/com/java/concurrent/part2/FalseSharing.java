package com.java.concurrent.part2;

/**
 * 伪共享案列，无声的性能杀手
 * 为了展示其性能影响，我们启动几个线程，每个都更新它自己独立的计数器。计数器是volatile long类型的，
 * 所以其它线程能看到它们的进展
 * @author Mr.zxb
 * @date 2019-06-14 14:52
 */
public final class FalseSharing implements Runnable  {

    /**
     * 线程数
     */
    public static final int NUM_THREADS = 4;

    public static final long ITERATIONS = 500L * 1000L * 1000L;

    private final int arrayIndex;

    private static FilledLong[] longs = new FilledLong[NUM_THREADS];

    static {
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new FilledLong();
        }
    }

    public FalseSharing(int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    private static void runTest() throws InterruptedException {
        final Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing(i));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    @Override
    public void run() {
        long l = ITERATIONS + 1;
        while (0 != --l) {
            longs[arrayIndex].value = l;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final long start = System.currentTimeMillis();
        runTest();
        System.out.println("duration = " + (System.currentTimeMillis() - start));
    }
}
