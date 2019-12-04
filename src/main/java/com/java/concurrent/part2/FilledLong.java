package com.java.concurrent.part2;

/**
 * 如何避免伪共享
 * jdk8之前是通过字节填充的方式来避免，也就是创建一个变量时使用填充字段该变量所在的缓存行，这样就避免了将多个变量存放同一个缓存行中
 * jdk8提供了一个sum.misc.Contended注解，用来解决伪共享的问题
 * @author Mr.zxb
 * @date 2019-06-14 11:22
 */
public class FilledLong {

    /**
     * long padding避免false sharing 按理说jdk7以后long padding应该被优化掉了，但是从测试结果看padding仍然起作用
     */
   public long q0, q1, q2, q3, q4, q5, q6;

    public volatile long value = 0L;

    /**
     * 假如缓存行64字节，那么我们在FilledLong类里面填充了6个long类型的变量，每个long类型的变量占用8字节，加上value变量的8字节总共56字节。
     * 另外，这里FilledLong是一个类对象，而类对象的字节码的对象头占用8字节，所以一个FilledLong对象实际会占用64字节的内存，这正好可以放入一个缓存行。
     */
    public long p0, p1, p2, p3, p4, p5, p6;
}
