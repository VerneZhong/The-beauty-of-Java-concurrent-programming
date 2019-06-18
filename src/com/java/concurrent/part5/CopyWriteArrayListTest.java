package com.java.concurrent.part5;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList是线程安全的ArrayList，使用写时复制的策略来保证list的一致性，而获取-修改-写入三步骤并不是原子性的，所以在增删改的过程中都使用了
 * 独占锁，来保证在某一时刻只有一个线程能对list数组进行修改。另外CopyOnWriteArrayList提供了弱一致性的迭代器，从而保证在获取迭代器后，其他线程对list的修改是不可见的，
 * 迭代器遍历的数组是一个快照。
 * CopyOnWriteArrayList的缺点：
 * 内存占用：如果CopyOnWriteArrayList经常要增删改里面的数据，经常要执行add()、set()、remove()的话，是比较耗费内存的。因为我们知道每次add()、set()、
 * remove()这些增删改操作都要复制一个数组出来。
 * 数据弱一致性：CopyOnWrite容器只能保证数据的最终一致性，不能保证数据的实时一致性。
 *
 * @author Mr.zxb
 * @date 2019-06-18 10:09
 */
public class CopyWriteArrayListTest {

    public static void main(String[] args) {

        List<String> list = new CopyOnWriteArrayList<>();

        list.add("hello");
        list.add("alibaba");

        final Iterator<String> iterator = list.iterator();

        list.remove(1);

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}
