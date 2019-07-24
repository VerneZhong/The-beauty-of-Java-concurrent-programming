package com.java.concurrent.part7;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 优先级的阻塞队列
 *  PriorityBlockingQueue队列在内部使用二叉树堆维护元素的优先级，使用数组作为元素存储的数据结构，这个数组是可扩容的。当当前元素个数>=最大
 *  容量时会通过CAS算法扩容，出队时始终保持出队的元素是堆树的根节点，而不是在队列里面停留时间最长的元素。使用元素compareTo方法提供默认的
 *  元素优先级比较规则，用户可以自定义优先级的比较规则。
 *  PriorityBlockingQueue类似于ArrayBlockingQueue，在内部使用一个独占锁来控制同时只有一个线程可以进行入队和出队的操作。另外前者只使用
 *  了一个notEmpty的条件变量而没有使用notFull，这是因为前者是无界队列，执行put操作时永远不会await状态，所以也不需要被唤醒。而take方法是
 *  阻塞方法，而且是可以被中断的。当需要存放有优先级的元素时该队列比较有用。
 * @author Mr.zxb
 * @date 2019-07-23 15:02
 */
public class PriorityBlockingQueueTest {

    static class Task implements Comparable<Task> {

        private int priority = 0;

        private String taskName;

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public int compareTo(Task o) {
            if (this.priority >= o.getPriority()) {
                return -1;
            }
            return 1;
        }
        
        public void doSomeThing() {
            System.out.println(taskName + ":" + priority);
        }
    }

    public static void main(String[] args) {

        // 创建队列
        BlockingQueue<Task> queue = new PriorityBlockingQueue<>();

        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            final Task task = new Task();
            task.setPriority(random.nextInt(10));
            task.setTaskName("taskName" + i);
            queue.offer(task);
        }

        while (!queue.isEmpty()) {
            Task task = queue.poll();
            if (task != null) {
                task.doSomeThing();
            }
        }
        
    }
}
