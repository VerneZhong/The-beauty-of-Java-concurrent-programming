package com.java.concurrent.part2;

import com.java.concurrent.common.SleepUtil;

import java.util.concurrent.TimeUnit;

/**
 * Java指令重排序
 * 写volatile变量时，可以确保volatile写之前的操作不会被编译器重排序到volatile写之后。
 * 读volatile变量时，可以确保volatile读之后的操作不会被编译器重排序到volatile读之前
 * @author Mr.zxb
 * @date 2019-06-13 10:26
 */
public class ReadThread extends Thread {

    private static int num = 0;

    private static volatile boolean ready = false;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (ready) {
                System.out.println(num + num);
            }
        }
    }

    public static class WriteThread extends Thread {
        @Override
        public void run() {
            num = 2;
            // 如果未将ready设置成volatile，可能会被排序到num前面执行
            ready = true;
            System.out.println("writeThread set over...");
        }
    }

    public static void main(String[] args) {
        ReadThread rt = new ReadThread();
        rt.start();

        final WriteThread writeThread = new WriteThread();
        writeThread.start();

        SleepUtil.sleep(TimeUnit.MILLISECONDS, 50);
        rt.interrupt();

        System.out.println("main exit");
    }

}
