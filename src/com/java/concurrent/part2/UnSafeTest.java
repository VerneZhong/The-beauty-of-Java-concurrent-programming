package com.java.concurrent.part2;

import sun.misc.Unsafe;

/**
 * JDK的rt.jar包中提供了硬件级别的原子性操作，UNsafe类中的方法都是native方法，它们使用JNI的方式访问本地C++实现库
 * @author Mr.zxb
 * @date 2019-06-06 15:28
 */
public class UnSafeTest {

    /**
     * 获取UNsafe实例
     */
    private static final Unsafe unsafe = Unsafe.getUnsafe();

    /**
     * 记录变量state在类中的偏移量
     */
//    private static final long stateOffset;
}
