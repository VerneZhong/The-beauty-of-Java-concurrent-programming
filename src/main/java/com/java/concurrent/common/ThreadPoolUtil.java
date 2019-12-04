package com.java.concurrent.common;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-11-17 11:00
 */
public class ThreadPoolUtil {

    public static ExecutorService getThreadPool(int coreSize) {
        return new ThreadPoolExecutor(coreSize, coreSize, 60,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new MyThreadFactory());
    }

    public static ExecutorService getThreadPool() {
        return new ThreadPoolExecutor(2, 2, 60,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new MyThreadFactory());
    }

    private static class MyThreadFactory implements ThreadFactory {

        private static final AtomicLong atomicLong = new AtomicLong(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("Thread-" + atomicLong.incrementAndGet());
            return thread;
        }
    }
}
