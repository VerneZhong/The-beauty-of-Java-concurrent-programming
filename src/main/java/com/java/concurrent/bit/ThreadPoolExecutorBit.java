package com.java.concurrent.bit;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Mr.zxb
 * @date 2019-07-24 17:16
 */
public class ThreadPoolExecutorBit {

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

    public static final int COUNT_BITS  = Integer.SIZE - 3;

    public static final int CAPACITY    = (1 << COUNT_BITS) - 1;

    public static final int RUNNING     = -1 << COUNT_BITS;
    public static final int SHUTDOWN    = 0 << COUNT_BITS;
    public static final int STOP        = 1 << COUNT_BITS;
    public static final int TIDYING     = 2 << COUNT_BITS;
    public static final int TERMINATED  = 3 << COUNT_BITS;

    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }

    private static int workerCountOf(int c) {
        return c & CAPACITY;
    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    private static boolean runStateLessThan(int c, int s) {
        return c < s;
    }

    private static boolean runStateAtLeast(int c, int s) {
        return c >= s;
    }

    private static boolean isRunning(int c) {
        return c < SHUTDOWN;
    }

    private boolean compareAndIncrementWorkerCount(int expect) {
        return ctl.compareAndSet(expect, expect + 1);
    }

    private boolean compareAndDecrementWorkerCount(int expect) {
        return ctl.compareAndSet(expect, expect - 1);
    }

    private void decrementWorkerCount() {
        do {

        } while (!compareAndDecrementWorkerCount(ctl.get()));
    }

    public static void main(String[] args) {

        System.out.println(ThreadPoolExecutorBit.COUNT_BITS);
        System.out.println(ThreadPoolExecutorBit.CAPACITY);
        System.out.println(ThreadPoolExecutorBit.RUNNING);
        System.out.println(ThreadPoolExecutorBit.STOP);
        System.out.println(ThreadPoolExecutorBit.TIDYING);
        System.out.println(ThreadPoolExecutorBit.TERMINATED);
    }
}
