package com.java.concurrent.part1;

/**
 * 守护线程与用户线程
 *
 * @author Mr.zxb
 * @date 2019-06-06 14:18
 */
public class ThreadDaemonTest {

    public static void main(String[] args) {

        final Thread thread = new Thread(() -> {
            while (true) {

            }
        });

        // 设置守护线程
//        thread.setDaemon(true);
        thread.start();
    }
}
