package com.java.concurrent.part2;

import sun.misc.Contended;

/**
 * Java8字节填充的方式
 * Jdk8提供了一个sum.misc.Contended注解，用来解决伪共享问题，该注解可以用来修饰类，也可以修饰变量，比如在Thread类中
 * 需要注意的是，在默认情况下，@Contended注解只用于Java核心类，比如rt包下的类。如果用户类路径下的类需要使用这个注解，
 * 则需要添加JVM参数：-XX:-RestrictContended，填充的宽度默认是128，要自定义宽度则可以设置：-XX:ContendedPaddingWidth参数。
 * @author Mr.zxb
 * @date 2019-06-14 13:41
 */
@Contended
public class FilledLongFor8 {

    public volatile long value = 0L;

}
