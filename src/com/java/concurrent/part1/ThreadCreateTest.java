package com.java.concurrent.part1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Mr.zxb
 * @date 2019-04-22 15:05
 */
public class ThreadCreateTest {

    static class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println("Thread -> I am a child Thread.");
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 创建线程第一种方式
        MyThread myThread = new MyThread();

        myThread.start();

        // 创建线程第二种方式
        Thread thread = new Thread(new RunnableTask());

        thread.start();

        // 创建线程第三种方式
        FutureTask<String> futureTask = new FutureTask<>(() -> "FutureTask -> I am a child Thread.");

        // 执行异步任务
        Thread thread1 = new Thread(futureTask);

        thread1.start();

        // 等待任务执行完毕，并返回结果
        System.out.println(futureTask.get());
    }

    static class RunnableTask implements Runnable {

        @Override
        public void run() {
            System.out.println("Runnable -> I am a child Thread.");
        }
    }




}
