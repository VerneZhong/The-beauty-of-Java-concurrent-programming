package com.java.concurrent.part7;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * DelayQueue是一个无界阻塞延迟队列，队列中的每个元素都有个过期时间，当从队列获取元素时，只有过期的才会出队列。队列头元素是最快要过期的元素。
 * DelayQueue内部是使用PriorityQueue存放数据，使用ReentrantLock实现线程同步。另外队列里面的元素要实现Delayed接口，其中一个是获取当前元素到过期时间
 * 剩余时间的接口，在出队列时判断元素是否过期了，一个是元素之间比较的接口，因为这是个有优先级的队列
 * @author Mr.zxb
 * @date 2019-07-23 16:04
 */
public class DelayQueueTest {

    static class DelayedElement implements Delayed {

        /**
         * 延迟时间
         */
        private final long delayTime;

        /**
         * 过期时间
         */
        private final long expire;

        /**
         * 任务名称
         */
        private String taskName;

        public DelayedElement(long delayTime, String taskName) {
            this.delayTime = delayTime;
            this.expire = System.currentTimeMillis() + delayTime;
            this.taskName = taskName;
        }

        /**
         * 剩余时间=到期时间-当前时间
         *
         * @param unit
         * @return
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        /**
         * 优先级队列里面的优先级规则
         *
         * @param o
         * @return
         */
        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public String toString() {
            final StringBuilder stringBuilder = new StringBuilder("DelayElement{");
            stringBuilder.append("delay=").append(delayTime);
            stringBuilder.append(", expire=").append(expire);
            stringBuilder.append(", taskName='").append(taskName).append("'}");
            return stringBuilder.toString();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 创建delay队列
        BlockingQueue<DelayedElement> queue = new DelayQueue<>();

        // 创建延迟任务
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            DelayedElement element = new DelayedElement(random.nextInt(5000), "task:" + i);
            queue.offer(element);
        }

        // 依次取出并打印任务
        DelayedElement element;
        // 循环，如果想避免虚假唤醒，则不能把全部元素都打印出来
        for (; ; ) {
            // 获取过期任务并打印
            while ((element = queue.take()) != null) {
                System.out.println(element);
            }
        }
    }

}
