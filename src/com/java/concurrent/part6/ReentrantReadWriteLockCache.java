package com.java.concurrent.part6;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ReentrantReadWriteLock实现一个简单的缓存
 * 缓存主要提供两个功能：读和写。
 * 读时如果缓存中存在数据，则立即返回数据。
 * 读时如果缓存中不存在数据，则需要从其他途径获取数据，同时写入缓存。
 * 在写入缓存的同时，为了避免其他线程同时获取这个缓存中不存在的数据，需要阻塞其他读线程。
 *
 * ReentrantReadWriteLock它的底层是使用AQS实现的。ReentrantReadWriteLock巧妙地使用了AQS的状态值的高16位表示获取到读锁的个数，
 * 低16位表示获取写锁的线程的可重入次数，并通过CAS对其进行操作实现了读写分离，这在读多写少的场景比较适用。
 *
 * @author Mr.zxb
 * @date 2019-06-26 10:55
 */
public class ReentrantReadWriteLockCache<K, V> {

    /**
     * 缓存存储容器
     */
    private final Map<K, V> cache;

    /**
     * 非公平的读写锁
     */
    private final ReentrantReadWriteLock lock;

    /**
     * 读锁
     */
    private final ReentrantReadWriteLock.ReadLock readLock;

    /**
     * 写锁
     */
    private final ReentrantReadWriteLock.WriteLock writeLock;

    public ReentrantReadWriteLockCache() {
        cache = new ConcurrentHashMap<>();
        lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    public V put(K k, V v) {
        writeLock.lock();
        try {
            return cache.putIfAbsent(k, v);
        } finally {
            writeLock.unlock();
        }
    }

    public V get(K k) {
        readLock.lock();
        try {
            return cache.get(k);
        } finally {
            readLock.unlock();
        }
    }

    public V remove(K k) {
        writeLock.lock();
        try {
            return cache.remove(k);
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantReadWriteLockCache<String, String> cache = new ReentrantReadWriteLockCache();

        // 10个读线程
        List<Thread> readThreads = IntStream.range(0, 20)
                .mapToObj(i -> new Thread(() -> {
//                    if (i % 2 == 0) {
                        System.out.println(Thread.currentThread().getName() +" " + cache.get(i + ""));
//                    } else {
//                        System.out.println(Thread.currentThread().getName() +" " + cache.get("1"));
//                    }
                }))
                .collect(Collectors.toList());

        // 2个写线程
        List<Thread> writeThreads = IntStream.range(0, 2)
                .mapToObj(i -> new Thread(() -> cache.put(i + "", "cache:" + i)))
                .collect(Collectors.toList());

        final long start = System.currentTimeMillis();
        writeThreads.forEach(Thread::start);
        readThreads.forEach(Thread::start);

        for (Thread writeThread : writeThreads) {
            writeThread.join();
        }
        for (Thread readThread : readThreads) {
            readThread.join();
        }

        System.out.println("main over, time cost：" + (System.currentTimeMillis() - start));
    }
}
