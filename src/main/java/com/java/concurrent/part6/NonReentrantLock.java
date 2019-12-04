package com.java.concurrent.part6;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

/**
 * 基于AQS实现自定义同步器
 * 基于AQS实现不可重入的独占锁，这里我们定义state为0表示目前锁没有被线程持有，state为1表示锁已经被某一个线程持有，由于是不可重入锁，
 * 所以不需要记录持有锁的线程获取锁的次数。另外，我们自定义锁支持条件变量
 *
 * 一个锁对应一个AQS阻塞队列，对应多个条件变量，每个条件变量有自己的一个条件队列。
 * @author Mr.zxb
 * @date 2019-06-20 10:36
 */
public class NonReentrantLock {

    /**
     * 继承于AQS的内部类，重新AQS的一些方法，实现具体的锁的操作
     */
    private static class Sync extends AbstractQueuedSynchronizer {

        /**
         * 锁是否已经被持有
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        /**
         * 如果state为0，则尝试获取锁
         * @param arg
         * @return
         */
        @Override
        protected boolean tryAcquire(int arg) {
            assert arg == 1;
            if (compareAndSetState(0, 1)) {
                // 设置锁被当前线程占有
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        /**
         * 尝试释放锁，将state设为0
         * @param arg
         * @return
         */
        @Override
        protected boolean tryRelease(int arg) {
            assert arg == 1;
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            // 释放占有线程对象为null
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        /**
         * 提供条件变量接口
         * @return
         */
        Condition newCondition() {
            return new ConditionObject();
        }
    }

    /**
     * 实例化一个Sync对象
     */
    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire(1);
    }

    public void tryLock() {
        sync.tryAcquire(1);
    }

    public void unlock() {
        sync.release(1);
    }

    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }

}
