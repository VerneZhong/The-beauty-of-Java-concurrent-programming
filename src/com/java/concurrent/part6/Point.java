package com.java.concurrent.part6;

import java.util.concurrent.locks.StampedLock;

/**
 * {@link StampedLock} 提供了三种读写模式的锁分别如下：
 *
 * 写锁writeLock：是一个排他锁或独占锁，某时只有一个线程可以获取该锁，当一个线程获取该锁后，其他请求线程读锁和写锁的线程必须等待，这类似于
 * {@link java.util.concurrent.locks.ReentrantReadWriteLock}的写锁(不同的是这里的写锁是不可重入锁)；当目前没有线程持有读锁或写锁时才可以
 * 获取到该锁。请求该锁成功后会返回一个stamp变量用来表示该锁的版本，当释放该锁时需要调用unlockWrite方法并传递获取锁时的stamp参数。并且它提供了
 * 非阻塞的tryWriteLock方法。
 *
 * 悲观读锁readLock：是一个共享锁，在没有线程获取独占锁写锁的情况下，多个线程可以同时获取该锁。如果已经有线程持有写锁，则其他线程请求获取该锁会被
 * 阻塞，这类似于{@link java.util.concurrent.locks.ReentrantReadWriteLock}的写锁（不同的是这里的读锁是不可重入锁）。这里说的悲观是指在具体操作
 * 数据前其会悲观地认为其他线程可能要对自己的操作的数据进行修改，所以需要先对数据加锁，这是在读少写多的情况下的一种考虑。请求该锁成功后返回一个stamp
 * 参数变量用来表示该锁的版本，当释放该锁时需要调用unlockRead方法并传递stamp参数。并且它提供了非阻塞的tryReadLock方法。
 *
 * 乐观读锁tryOptimisticRead：它是相对悲观读锁来说的，在操作数据前并没有通过CAS设置锁的状态，仅仅通过位运算测试。如果当前没有线程持有写锁，则简单
 * 地返回一个非0的stamp版本信息。获取该stamp后在具体操作数据前还需要调用validate方法验证该stamp是否已经不可用，也就是看当调用tryOptimisticRead返回
 * stamp后到当前时间期间是否有其他线程持有了写锁，如果是则validate会返回0，否则就可以使用该stamp版本的锁对数据进行操作。由于tryOptimisticRead并没有
 * 使用CAS设置锁的状态，所以不需要显示地释放该锁。 该锁的一个特点是适用于读多写少的场景。因为获取读锁只是使用位操作进行检验，不涉及CAS操作，所以效率
 * 会高很多，但是同时由于没有使用真正的锁，在保证数据一致性上需要复制一份要操作的变量到方法栈，并且在操作数据时可能其他写线程已经修改了数据，而我们操作的
 * 是方法栈里的数据，也就是一个快照，所以最多返回的不少最新的数据，但是一致性还是得到保障。
 *
 * StampedLock还支持三种锁在一定条件下进行相互转换。tryConvertToWriteLock(long stamp)期望把stamp标示的锁升级为写锁，这个方法会在下面几种情况下返回
 * 一个有效的stamp（也就是晋升写锁成功）：
 * 当前锁已经是写锁模式了
 * 当前锁处于读锁模式，并且没有其他线程是读锁模式
 * 当前处于乐观读模式，并且当前写锁可用
 *
 * 另外StampedLock的读写锁都是不可重入锁，所以在获取锁后释放锁前不应该在调用获取锁的操作，以避免造成调用线程被阻塞。当多个线程同时尝试获取读锁和写锁时，
 * 谁先获取锁没有一定的规则，完全都是尽力而为，是随机的。并且该锁不是直接实现Lock和ReadWriteLock接口，而是在内部自己维护了一个双向阻塞队列。
 *
 * @author Mr.zxb
 * @date 2019-06-26 16:23
 */
public class Point {

    /**
     * 成员变量x，y
     */
    private double x, y;

    /**
     * 锁实例
     */
    private final StampedLock stampedLock = new StampedLock();

    /**
     * 独占锁-写锁
     * @param deltaX
     * @param deltaY
     */
    void move(double deltaX, double deltaY) {
        final long stamp = stampedLock.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    /**
     * 乐观读锁
     * @return
     */
    double distanceFromOrigin() {
        // 尝试获取乐观读锁
        long stamp = stampedLock.tryOptimisticRead();
        // 将全部变量复制到方法体栈内
        double currentX = x, currentY = y;
        // 检查获取了读锁戳记后，锁有没被其他线程排他性抢占
        if (!stampedLock.validate(stamp)) {
            // 如果被抢占则获取一个共享读锁(悲观获取)
            stamp = stampedLock.readLock();
            try {
                // 将全部变量复制到方法体栈内
                currentX = x;
                currentY = y;
            } finally {
                // 释放共享读锁
                stampedLock.unlockRead(stamp);
            }
        }
        // 返回计算结果
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    /**
     * 使用悲观锁获取读锁，并尝试转换写锁
     * @param newX
     * @param newY
     */
    void moveIfAtOrigin(double newX, double newY) {
        // 这里可以使用乐观读锁替换
        long stamp = stampedLock.readLock();
        try {
            // 如果当前点在原点则移动
            while (x == 0.0 && y == 0.0) {
                // 尝试将获取的读锁升级为写锁
                final long writeStamp = stampedLock.tryConvertToWriteLock(stamp);
                // 升级成功，则更新新戳记，并设置坐标值，然后退出循环
                if (writeStamp == 0) {
                    stamp = writeStamp;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    // 读锁升级写锁失败则释放读锁，显式获取独占写锁，然后循环重试
                    stampedLock.unlockRead(stamp);
                    stamp = stampedLock.writeLock();
                }
            }
        } finally {
            // 释放锁
            stampedLock.unlock(stamp);
        }
    }

}
