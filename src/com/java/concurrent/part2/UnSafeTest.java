package com.java.concurrent.part2;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * JDK的rt.jar包中提供了硬件级别的原子性操作，Unsafe类中的方法都是native方法，它们使用JNI的方式访问本地C++实现库
 *
 * long objectFieldOffset(Field var1)：返回指定变量在所属类中的内存偏移量地址，该偏移地址仅仅在该Unsafe函数中访问指定字段时使用
 * int arrayBaseOffset(Class var1)：获取数组中第一个元素的地址
 *
 * int arrayIndexScale(Class var1)：获取数组中一个元素占用的字节
 *
 * boolean compareAndSwapLong(Object obj, long offset, long expect, long update)：比较对象obj中偏移量为offset的变量的值是否与expect相等，
 * 相等则使用update更新，然后返回true，否则返回false
 *
 * long getLongVolatile(Object obj, long offset):获取对象obj中偏移量为offset的变量对应volatile语义的值
 *
 * void putLongVolatile(Object obj, long offset, long value)：设置obj对象中offset偏移量的类型为long的field的值为value，支持volatile语义
 *
 * void putOrderedLong(Object obj, long offset, long value)：设置obj对象中offset偏移地址对应的long型field的值为value。这是个有延迟的方法
 * ，并且不保证值修改对其他线程立刻可见。只有在变量使用volatile修饰并且预计会被意外修改时才能使用该方法
 *
 * void park(boolean isAbsolute, long time)：阻塞当前线程，其中参数isAbsolute等于false且time为0表示一直阻塞。
 * time大于0表示等待指定的time后阻塞线程会被唤醒，这个time是个相对值，是个增量值，也就是相对当前时间累加time后当前线程就会被唤醒。
 * 如果isAbsolute为true，并且time大于0，则表示阻塞的线程到指定的时间会被唤醒，这里time是个绝对值，是将某个时间点换算为ms后的值。
 * 另外当其他线程调用了当前阻塞线程的interrupt方法而中断了当前线程时，当前线程也会返回，而当其他线程调用了unpark方法并且把当前线程作为参数时当前线程也会返回
 *
 * void unpark(Object thread)：唤醒调用park后阻塞的线程
 *
 * long getAndSetLong(Object obj, long offset, long update)：获取对象obj中偏移量为offset的变量volatile语义的当前值，并设置变量volatile语义的值为update
 *
 * long getAndAddLong(Object obj, long offset, long update)：获取对象obj中偏移量为offset的变量volatile语义的当前值，并设置变量值为原始值++addValue
 *
 * @author Mr.zxb
 * @date 2019-06-06 15:28
 */
public class UnSafeTest {

    /**
     * 获取Unsafe实例
     */
    private static final Unsafe unsafe;

    /**
     * 记录变量state在类中的偏移值
     */
    private static final long stateOffset;

    /**
     * 变量
     */
    private int state = 0;

    static {
        try {
            // 通过反射获取Unsafe的成员变量theUnsafe
            final Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");

            // 设置变量可存取
            theUnsafe.setAccessible(true);

            // 获取该变量的值
            unsafe = (Unsafe) theUnsafe.get(null);

            // 获取state变量在类TestUnsafe中的偏移值
            stateOffset = unsafe.objectFieldOffset(UnSafeTest.class.getDeclaredField("state"));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            throw new Error(e);
        }
    }

    public static void main(String[] args) {

        final UnSafeTest unSafeTest = new UnSafeTest();

        // 如果stateOffset为expect就更新成1
        final boolean b = unsafe.compareAndSwapInt(unSafeTest, stateOffset, 0, 1);

        System.out.println(b);

        System.out.println(unSafeTest.state);
    }
}
