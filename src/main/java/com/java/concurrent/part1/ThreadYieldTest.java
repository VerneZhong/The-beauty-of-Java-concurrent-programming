package com.java.concurrent.part1;

/**
 * 线程执行yield静态方法会让出CPU执行权，但线程调度器不一定会响应
 * @author Mr.zxb
 * @date 2019-04-23 16:01
 */
public class ThreadYieldTest implements Runnable {

    public ThreadYieldTest() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            if (i % 5 == 0) {
                System.out.println(Thread.currentThread() + " yield cpu..");

                // 当前线程让出CPU执行权，放弃时间片，进入下一轮调度
                Thread.yield();
            }
        }
        System.out.println(Thread.currentThread() + " is over.");
    }

    public static void main(String[] args) {

        new ThreadYieldTest();
        new ThreadYieldTest();
        new ThreadYieldTest();
    }
}
