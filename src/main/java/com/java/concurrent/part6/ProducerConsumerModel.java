package com.java.concurrent.part6;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;

/**
 * 使用自定义锁实现生产-消费模型
 *
 * @author Mr.zxb
 * @date 2019-06-20 11:02
 */
public class ProducerConsumerModel {

    private final static Queue<String> QUEUE = new LinkedBlockingQueue<>();

    private final static int QUEUE_SIZE = 10;

    public static void main(String[] args) {

        final NonReentrantLock lock = new NonReentrantLock();
        final Condition full = lock.newCondition();
        final Condition empty = lock.newCondition();

        final Thread producer = new Thread(() -> {
            // 获取独占锁
            lock.lock();
            try {
                // 如果队列满了，则等待
                while (QUEUE.size() == QUEUE_SIZE) {
                    full.await();
                }

                // add element to queue
                QUEUE.add("ele");

                // wake consumer thread
                empty.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // release lock
                lock.unlock();
            }
        });

        final Thread consumer = new Thread(() -> {
            // Get exclusive lock
            lock.lock();
            try {
                // queue is empty, await
                while (QUEUE.size() == 0) {
                    empty.await();
                }

                // consumer one element
                String ele = QUEUE.poll();
                System.out.println(ele);
                // wake producer thread
                full.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // release lock
                lock.unlock();
            }
        });

        producer.start();
        consumer.start();

    }
}
