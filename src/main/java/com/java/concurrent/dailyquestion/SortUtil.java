package com.java.concurrent.dailyquestion;

import java.util.Arrays;

/**
 * 几种排序算法：
 * 冒泡排序
 * 快速排序
 * 插入排序
 * 堆排序
 * 归并排序
 *
 * @author Mr.zxb
 * @date 2019-07-19 09:05
 */
public class SortUtil {

    /**
     * 冒泡排序算法的运作如下：
     * 1.比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     * 2.对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。这步做完后，最后的元素会是最大的数。
     * 3.针对所有的元素重复以上的步骤，除了最后一个。
     * 4.持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
     *
     * @param array
     * @return
     */
    public static void bubbleSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    int temp = array[j];
                    array[j] = array[i];
                    array[i] = temp;
                }
            }
        }
    }

    /**
     * 快速排序
     *
     * @param arr
     */
    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * 快速排序算法的运作如下：
     * 1.挑选基准值：从数列中挑出一个元素，称为“基准”（pivot）.
     * 2.分割：重新排序数列，所有比基准值小的元素摆放在基准前面，所有比基准值大的元素摆在基准后面（与基准值相等的数可以到任何一边）。
     * 在这个分割结束之后，对基准值的排序就已经完成.
     * 3.递归排序子序列：递归地将小于基准值元素的子序列和大于基准值元素的子序列排序。
     *
     * @param arr
     * @param head
     * @param tail
     */
    private static void quickSort(int[] arr, int head, int tail) {
        // 不满足排序条件即返回
        if (head >= tail || arr == null || arr.length <= 1) {
            return;
        }

        // 记录头下标和尾下标，以及基准值
        int i = head, j = tail, pivot = arr[(head + tail) / 2];
        while (i <= j) {
            while (arr[i] < pivot) {
                ++i;
            }
            while (arr[j] > pivot) {
                --j;
            }
            if (i < j) {
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
                ++i;
                --j;
            } else if (i == j) {
                ++i;
            }
        }
        quickSort(arr, i, tail);
        quickSort(arr, head, j);
    }

    /**
     * 插入排序都采用in-place在数组上实现。具体算法描述如下：
     * 1.从第一个元素开始，该元素可以认为已经被排序
     * 2.取出下一个元素，在已经排序的元素序列中从后向前扫描
     * 3.如果该元素（已排序）大于新元素，将该元素移到下一位置
     * 4.重复步骤3，直到找到已排序的元素小于或者等于新元素的位置
     * 5.将新元素插入到该位置后
     * 6.重复步骤2~5
     *
     * @param array
     */
    public static void insertSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    /**
     * 选择排序算法：是一种简单直观的排序算法。它的工作原理如下。首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，
     * 然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕。
     *
     * @param arr
     */
    public static void selectionSort(int[] arr) {
        int min;
        int max;
        int count = arr.length;
        for (int i = 0; i < count; i++) {
            // 初始化未排序序列中最小数据数组下标
            min = i;
            // 初始化未排序序列中最大数据数组下标
            max = count - 1;
            for (int j = i; j < count; j++) {
                // 在未排序元素中继续寻找最小元素，并保存其下标
                if (arr[min] >= arr[j]) {
                    min = j;
                }
                // 在未排序元素中继续寻找最大元素，并保存其下标
                if (arr[max] <= arr[j]) {
                    max = j;
                }
            }
            // 将未排序列中最小元素放到已排序列头
            if (min != i) {
                int temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
            }
            // 将未排序列中最大元素放到已排序列尾
            if (max != count - 1) {
                int temp = arr[count - 1];
                arr[count - 1] = arr[max];
                arr[max] = temp;
            }
            count--;
        }
    }

    public static void main(String[] args) {

        int[] array = new int[]{1, 4, 8, 2, 55, 3, 4, 8, 6, 4, 0, 11, 34, 90, 23, 54, 77, 9, 2, 9, 4, 10};
        // 快速排序
//        quickSort(array);

        // 冒泡排序
//        bubbleSort(array);

        // 插入排序
//        insertSort(array);

        // 选择排序
//        selectionSort(array);

        Arrays.parallelSort(array);

        System.out.println(Arrays.toString(array));
    }
}
