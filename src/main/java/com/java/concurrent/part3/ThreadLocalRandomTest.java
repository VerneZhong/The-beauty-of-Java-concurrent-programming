package com.java.concurrent.part3;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 在每个Random实例里面都有一个原子性的种子变量用来记录当前的种子值，当要生成新的随机数时需要根据当前种子计算新的种子并更新回原子变量。
 * 在多线程使用单个Random实例生成随机数时，当多个线程同时计算随机数来计算新的种子时，多个线程会竞争同一个原子变量的更新操作，由于原子变量的
 * 更新是CAS操作，同时只有一个线程会成功，所以会造成大量线程进行自旋重试，这会降低并发性能，所以ThreadLocalRandom应运而生。
 *
 * ThreadLocalRandom使用ThreadLocal的原理，让每个线程都持有一个本地的种子变量，该种子变量只有在使用随机数时才会被初始化。
 * 在多线程环境下计算新种子时是根据自己线程内部维护的种子变量进行更新，从而避免了竞争。
 * @author Mr.zxb
 * @date 2019-06-14 16:41
 */
public class ThreadLocalRandomTest {

    public static void main(String[] args) {

        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(5));
        }
    }
}
