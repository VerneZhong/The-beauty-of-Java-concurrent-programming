package com.java.concurrent.part4;

import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * JDK8新增的原子操作类LongAdder
 * AtomicLong通过CAS提供了非阻塞的原子性操作，相比使用阻塞算法的同步器来说它的性能已经很好了，但是使用AtomicLong时，在高并发下大量线程会同时去竞争
 * 更新同一个原子变量，但是由于同时只有一个线程的CAS操作会成功，这就会造成了大量线程竞争失败后，会通过无线循环不断进行自旋尝试CAS的操作，而造成白白浪费CPU资源。
 * 因此JDK8新增了一个原子性递增或递减类LongAdder用来克服在高并发下使用AtomicLong的缺点。
 *
 * 为了解决高并发下多线程对一个变量CAS争夺失败后进行自旋而造成的降低并发性能问题，LongAdder在内部维护了多个cell元素（cells数组）来分担对单个变量进行争夺的开销。
 * 让多个线程可以同时对cells数组里面的元素进行并行的更新操作。另外，数组元素Cell使用@sun.misc.Contended注解进行修饰，这避免了cells数组内多个原子变量被放入同一个
 * 缓存行，也就是避免了伪共享，这对性能也是一个提升。
 * @author Mr.zxb
 * @date 2019-06-17 15:01
 */
public class LongAdderTest {

    public static void main(String[] args) throws InterruptedException {

        final LongAdder longAdder = new LongAdder();

        // 10个线程10次自增
        List<Thread> threads = IntStream.range(0, 10).mapToObj(i -> new Thread(() -> {
            for (int i1 = 0; i1 < 10; i1++) {
                longAdder.increment();
            }
        })).collect(Collectors.toList());

        threads.forEach(Thread::start);

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(longAdder.sum());
    }
}
