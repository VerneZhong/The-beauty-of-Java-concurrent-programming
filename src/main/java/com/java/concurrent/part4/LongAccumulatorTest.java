package com.java.concurrent.part4;

import java.util.List;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * LongAdder是LongAccumulator的一个特例，LongAccumulator比LongAdder的功能更强大，支持一个双目运算器接口，可以自定义累加规则
 * 其根据输入的两个参数返回一个计算值，identity则是LongAccumulator累加器的初始值。
 * @author Mr.zxb
 * @date 2019-06-17 15:32
 */
public class LongAccumulatorTest {

    public static void main(String[] args) throws InterruptedException {

        final LongAccumulator longAccumulator = new LongAccumulator((i, j) -> i + j, 0);

        // 10个线程每次加1
        List<Thread> threads = IntStream.rangeClosed(0, 10).mapToObj(i -> new Thread(() -> longAccumulator.accumulate(i)))
                .collect(Collectors.toList());

        threads.forEach(Thread::start);

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(longAccumulator.get());
    }

}
