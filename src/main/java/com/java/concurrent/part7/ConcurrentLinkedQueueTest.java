package com.java.concurrent.part7;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Mr.zxb
 * @date 2019-07-24 08:48
 */
public class ConcurrentLinkedQueueTest {

    static class Task {
        private String taskName;

        public Task(String taskName) {
            this.taskName = taskName;
        }

        public void doing() {
            System.out.printf("[%s] is running.\n", taskName);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // 创建无界无阻塞队列，基于cas实现入队和出队，底层数据结果是单向链表
        Queue<Task> queue = new ConcurrentLinkedQueue<>();
        // 一定程度上的有界阻塞队列，独占锁来维护出队和入队的原子性，putLock和takeLock来保证，底层数据结果是单向链表
//        BlockingQueue<Task> queue = new LinkedBlockingQueue<>(50);
        // 有界阻塞队列，独占锁来维护出队和入队的原子性，lock来保证，底层数据结构是数组
//        BlockingQueue<Task> queue = new ArrayBlockingQueue<>(50);

        long start = System.currentTimeMillis();

        // 创建线程往队列里添加数据
        final ArrayList<Thread> arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                for (int i1 = 0; i1 < 100; i1++) {
                    try {
                        queue.offer(new Task(Thread.currentThread().getName() + "-task-" + i1));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            arrayList.add(thread);
        }

        // 出队
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(() -> {
                while (!queue.isEmpty()) {
                    try {
                        queue.poll().doing();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            arrayList.add(thread);
        }

        // 启动线程
        arrayList.forEach(Thread::start);

//        for (Thread thread : arrayList) {
//            thread.join();
//        }
        while (!queue.isEmpty()) {

        }

        System.out.printf("main is over. duration [%s]\n", (System.currentTimeMillis() - start));
    }
}
