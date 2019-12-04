package com.java.concurrent.part10;

import com.java.concurrent.common.SleepUtil;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-12-04 16:40
 */
public class SemaphoreTest {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(1);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    // 申请许可证
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 获得许可证");

                SleepUtil.sleep(TimeUnit.SECONDS, 2);
                System.out.println(Thread.currentThread().getName() + " 释放许可证");
                semaphore.release();
            }).start();
        }
    }
}
