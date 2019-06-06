package com.java.concurrent.part1;

/**
 * ThreadLocal：它提供了线程本地变量，如果创建了一个ThreadLocal变量，那么访问这个变量的每个线程都会有这个变量的一个本地副本，创建
 * ThreadLocal后，每个线程都会复制一个变量到自己的本地内存
 *
 * 案列：开启2个线程，在每个线程内部都设置了本地变量的值
 *
 * 总结：在每个线程内部都有一个名为threadLocals的成员变量，该变量的类型为HashMap，其中key为我们定义的ThreadLocal变量的this引用，
 * value则为我们set方法设置的值。每个线程的本地变量存放在线程自己的内存变量threadLocals中，如果当前线程一直不消亡，那么这些本地变量会一直存在，
 * 所以可能会造成内存溢出，因此在使用完毕后要调用remove的方法删除对应的threadLocals中的本地变量.
 * @author Mr.zxb
 * @date 2019-06-06 14:28
 */
public class ThreadLocalTest {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    static void print(String s) {
        // 打印当前线程本地内存中ThreadLocal的值
        System.out.println(s + ":" + threadLocal.get());
        // 清除当前线程本地内存ThreadLocal的值
        threadLocal.remove();
    }

    public static void main(String[] args) {

        final Thread threadA = new Thread(() -> {
            // 设置线程a中本地变量的值
            threadLocal.set("threadA-100");
            // 打印当前本地变量的值
            print(Thread.currentThread().getName());
            // 清除本地变量后，打印本地变量的值
            System.out.println("threadA remove after: " + threadLocal.get());
        }, "threadA");

        final Thread threadB = new Thread(() -> {
            // 设置线程a中本地变量的值
            threadLocal.set("threadB-100");
            // 打印当前本地变量的值
            print(Thread.currentThread().getName());
            // 清除本地变量后，打印本地变量的值
            System.out.println("threadB remove after: " + threadLocal.get());
        }, "threadB");

        threadA.start();
        threadB.start();
    }
}
