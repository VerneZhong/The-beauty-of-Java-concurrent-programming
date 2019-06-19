package com.java.concurrent.part6;

import java.util.concurrent.locks.LockSupport;

/**
 *  使用诊断工具可以观察线程被阻塞的原因，诊断工具是通过调用getBlocker(thread)方法来获取blocker对象的，所以jdk推荐我们使用带有blocker参数的
 *  park方法，并且blocker被设置为this，这样当在打印堆栈排查问题时就能知道是哪个类被阻塞了。
 * @author Mr.zxb
 * @date 2019-06-18 15:21
 */
public class ParkBlockerTest {

    public void park() {
        // 没有下面的堆栈
//        LockSupport.park();

        // - parking to wait for  <0x000000076b8afba8> (a com.java.concurrent.part6.ParkBlockerTest)
        LockSupport.park(this);
    }

    public static void main(String[] args) {
        final ParkBlockerTest parkBlockerTest = new ParkBlockerTest();

        parkBlockerTest.park();
    }
}
