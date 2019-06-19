package com.java.concurrent.part6;

import com.java.concurrent.common.SleepUtil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport类与每个使用它的线程都会关联一个许可证，在默认情况下调用LockSupport类的方法的线程是不持有许可证的。
 * LockSupport是使用Unsafe类实现的
 *
 * void park()：调用park方法的线程已经拿到了与LockSupport关联的许可证，则调用LockSupport.park()时会马上返回，否则调用线程会被禁止参与线程调度，也就是会被阻塞挂起
 * void unpark()：当一个线程调用unpark方法时，如果参数thread线程没有持有thread与LockSupport类关联的许可证，则让thread线程持有。如果thread之前因调用park()而被
 * 阻塞挂起，则调用unpark方法后，该线程会被唤醒。如果thread之前没有调用park方法，则调用unpark方法后，再调用park方法会立即返回。
 *
 * void parkNanos(long nanos)：和park方法类似，如果调用park方法的线程已经拿到了与LockSupport关联的许可证，则调用LockSupport.parkNanos(long nanos)方法后会马上
 * 返回。该方法的不同在于，如果没有拿到许可证，则调用线程会被挂起nanos时间后修改为自动返回。
 *
 * park(Object blocker)：park方法还支持带有blocker参数的方法void park(Object blocker)方法， 当线程在没有持有许可证的情况下调用park方法而被阻塞挂起时，
 * 这个blocker对象会被记录到该线程内部。
 *
 * void parkNanos(Object blocker, long nanos)：相比park(Object blocker)方法对了个超时时间
 *
 * void parkUntil(Object blocker, long deadline)：其中参数deadline的时间单位为ms，该时间是从1970年到现在某一时刻点的毫秒值。该方法和parkNanos的区别是，
 * 后者是从当前时间算等待nanos秒时间，而前者是指定一个时间点。
 *
 *
 * @author Mr.zxb
 * @date 2019-06-18 13:36
 */
public class LockSupportTest {

    public static void main(String[] args) {


        final Thread thread = new Thread(() -> {
            System.out.println("child begin park!");
            // 只有被中断才会退出循环
            while (!Thread.currentThread().isInterrupted()) {
                // 调用park方法，阻塞当前线程
                LockSupport.park();
            }
            System.out.println("child thread unpark");
        });
        thread.start();

        SleepUtil.sleep(TimeUnit.SECONDS, 1);
        // 使当前线程获取到许可证
        System.out.println("main thread unpark!");

        // 调用unpark方法让thread线程持有许可证，然后park方法返回
//        LockSupport.unpark(thread);
        thread.interrupt();

    }
}
