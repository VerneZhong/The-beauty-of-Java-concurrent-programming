package com.java.concurrent.part2;

/**
 * 什么是伪共享：
 * 由于计算机系统中主内存与CPU之间运行速度差问题，会在cpu与主内存之间添加一级或者多级高速缓冲存储器，
 * 在CPU cache内部是按行存储的，其中每一行称为cache行。cache行是cache与主内存进行数据交换的单位，cache行的大小一般为2的幂次数字节。
 * 当CPU访问某个变量时，首先会看CPU cache是否有该变量，如果有则直接从中获取，否则就去主内存里面获取该变量，然后把该变量所在内存区域的一个cache行
 * 大小的内存复制到cache中。由于存放到cache行的是内存块而不是单个变量，所有可能会把多个变量存放到一个cache行中。当多个线程同时修改一个缓存行里面的
 * 多个变量时，由于同时只能有一个线程操作缓存行，所以相比将每个变量放到一个缓存行，性能会有所下降，这就是伪共享。
 *
 * 为何会出现伪共享：
 * 伪共享的产生是因为多个变量被放入了一个缓存行中，并且多个线程同时去写入缓存行中不同的数据。那么为何多个变量会被放入缓存行呢？其实是因为缓存与内存交换
 * 数据的单位就是缓存行，当CPU要访问的变量没有在缓存中找到时，根据程序运行的局部性原理，会把该变量所在内存中大小为缓存行的内存放入缓存行。
 *
 * @author Mr.zxb
 * @date 2019-06-12 15:12
 */
public class CacheTest {

    public static final int LINE_NUM = 1024;

    public static final int COLUM_NUM = 1024;

    /**
     * 填充满的方式，顺序访问数组元素
     * 效率高是因为数组内数组元素的内存地址是连续的，当访问数组第一个元素时，会把第一个元素后的若干元素一块放入缓存行，这样顺序访问数组元素时会在缓存中直接命中，
     * 因而就不会去主内存读取，后续访问也这样。也就是说，当顺序访问数组里面元素时，如果当前元素在缓存没有命中，那么会从主内存一下子读取后续若干个元素到缓存，
     * 也就是一次内存访问可以让后续多次访问直接在缓存中命中。
     */
    public static void test() {
        int[][] array = new int[LINE_NUM][COLUM_NUM];
        long start = System.currentTimeMillis();
        for (int i = 0; i < LINE_NUM; i++) {
            for (int j = 0; j < COLUM_NUM; j++) {
                array[i][j] = i * 2 + j;
            }
        }
        long end = System.currentTimeMillis() - start;
        System.out.println("test end = " + end);
    }

    /**
     * 填充满的方式，跳跃式访问数组元素
     * 不是顺序的，这破坏了程序访问的局部性原则，并且缓存是有容量控制的，当缓存满了时会根据一定淘汰算法替换缓存行，这会导致从内存置换过来的缓存行的元素还没等到
     * 被读取就被替换掉了。
     * 所以在单线程环境下修改一下缓存行中的多个变量，会充分利用程序运行的局部性原则，从而加速了程序的运行。而在多线程下并发修改一个缓存行中的多个变量就会竞争
     * 缓存行，从而降低程序运行的性能。
     *
     */
    public static void test2() {
        int[][] array = new int[LINE_NUM][COLUM_NUM];
        long start = System.currentTimeMillis();
        for (int i = 0; i < LINE_NUM; i++) {
            for (int j = 0; j < COLUM_NUM; j++) {
                array[j][i] = i * 2 + j;
            }
        }
        long end = System.currentTimeMillis() - start;
        System.out.println("test2 end = " + end);
    }

    public static void main(String[] args) {
//        test();
        test2();
    }

}
