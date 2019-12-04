package com.java.concurrent.dailyquestion;

/**
 * 每日一题：静态变量的坑，出现死锁互相等待
 * @author Mr.zxb
 * @date 2019-07-18 13:45
 */
public class Lazy {

    private static boolean initialized = false;

    // Lazy static块执行时（类不完全初始化），Runnable匿名类随之初始化
    // 如果Runnable匿名类的初始化（组成字节码）

    static {
        // 子线程
        Thread t = new Thread(
                // Case 1：匿名类的方式（不能依赖于外层类的初始化）
                // 如果是匿名类的话，它的类初始化依赖于外部初始化
                // com/java/concurrent/dailyquestion/Lazy$1
                // Lazy$1.class
//                new Runnable() {
//                    @Override
//                    public void run() {
////                        initialized = true;
//                        System.out.printf("线程 [%s] - %s\n" , Thread.currentThread().getName(), "run方法执行");
//                    }
//                }

                // Case 2：Lambda表达式：invokeDynamic指令实现
                // invokeDynamic指令作为Lazy字节码一部分，需要等待Lazy类加载的完成
                // InvokeDynamic #0:run:()Ljava/lang/Runnable;
//                () -> {}

                // Case 3：方法引用：invokeDynamic指令实现（能够运行）
                // System.out.println方法属于System.out对象类java.io.PrintStream，它被Bootstrap ClassLoader加载
                // 早于Lazy.class Application/System ClassLoader
                // 字节码 InvokeDynamic #0:run:(Ljava/io/PrintStream;)Ljava/lang/Runnable;
//                System.out::println

                // 加载的外部类和当前类在类初始化阶段不能有相互依赖，否则容易相互等待
                Print::print

        );

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new AssertionError(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(initialized);
    }
}
