package com.java.concurrent.part1;

/**
 * 锁是线程，锁对象执行完毕后，会调用自身对象上的notify()
 * @author Mr.zxb
 * @date 2019-04-23 11:19
 */
public class ThreadTestl {

    public static void main(String[] args) throws InterruptedException {

        MyLockThread lock = new MyLockThread();

        Thread thread = new Thread(() -> {
            // 当前线程获取了lock共享变量监视器锁
            synchronized (lock) {
                System.err.println("lock will be wait.");
                try {
                    // 当前线程释放了lock监视器锁，其他处于waiting的线程开始竞争获取CPU调度
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.println("lock be over.");
            }
        });

        thread.start();

        lock.start();
        System.out.println("main over.");
    }

    static class MyLockThread extends Thread {
        @Override
        public void run() {
            System.err.println("myLockThread");
        }
    }
}
