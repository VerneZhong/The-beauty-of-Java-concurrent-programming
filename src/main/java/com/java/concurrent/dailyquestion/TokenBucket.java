package com.java.concurrent.dailyquestion;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 令牌桶，对业务有一定的承载能力
 * @author Mr.zxb
 * @date 2019-07-10 10:02
 */
public class TokenBucket {

    /**
     * 令牌桶的最大容量
     */
    private final int bucketMaxNum;

    /**
     * 令牌生成的速率
     */
    private final int rate;

    /**
     * 当前时间
     */
    private long timestamp;

    /**
     * 当前令牌数量
     */
    private int nowTokens;

    private final Lock lock;

    public TokenBucket() {
        this(100, 1);
    }

    public TokenBucket(int bucketMaxNum, int rate) {
        this.bucketMaxNum = bucketMaxNum;
        this.rate = rate;
        this.lock = new ReentrantLock();
        this.timestamp = getNowTime();
    }

    private long getNowTime() {
        return System.currentTimeMillis();
    }

    public synchronized boolean getToken() {
//        lock.lock();
        // 当前请求时间
        try {
            final long nowTime = getNowTime();
            // 增加令牌
            nowTokens += (nowTime - timestamp) * rate;
            // 如果当前令牌大于最大桶容量，即返回最大的桶容量
            if (nowTokens > bucketMaxNum) {
                nowTokens = bucketMaxNum;
            }
            System.out.println("当前令牌数量 = " + nowTokens);
            // 记录上次的生成令牌时间
            timestamp = nowTime;
            if (nowTokens < 1) {
                return false;
            }
            nowTokens--;
            return true;
        } finally {
//            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TokenBucket tokenBucket = new TokenBucket();

        List<Thread> threads = IntStream.range(0, 10).mapToObj(i -> new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i1 = 0; i1 < 10; i1++) {
                System.out.println(tokenBucket.getToken());
            }
        })).collect(Collectors.toList());

        threads.forEach(Thread::start);

        for (Thread thread : threads) {
            thread.join();
        }
    }

}
