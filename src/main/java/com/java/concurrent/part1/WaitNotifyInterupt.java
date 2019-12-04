package com.java.concurrent.part1;

/**
 * 当一个线程调用共享对象的wait方法被阻塞挂起后，如果其他线程中断了该线程，则该线程会抛出{@link InterruptedException}异常并返回
 * @author Mr.zxb
 * @date 2019-04-22 16:11
 */
public class WaitNotifyInterupt {

    static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            System.out.println("---begin---");

            // 阻塞当前线程
            synchronized (obj) {
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    System.out.println("thread be interrupt.");
                }
            }
            System.out.println("---end---");
        });

        thread.start();

        Thread.sleep(1000);

        System.out.println("begin interrupt thread");
        thread.interrupt();
        System.out.println("end interrupt thread");
    }
}
