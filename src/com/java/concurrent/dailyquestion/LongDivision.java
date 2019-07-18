package com.java.concurrent.dailyquestion;

/**
 * @author Mr.zxb
 * @date 2019-07-16 10:15
 */
public class LongDivision {

    private static final long MILLIS_PER_DAY
            = 24 * 60 * 60 * 1000;

    private static final long MICROS_PER_DAY
            = 24 * 60 * 60 * 1000 * 1000;

    public static void main(String[] args) {
        System.out.println(MICROS_PER_DAY);
        System.out.println(MILLIS_PER_DAY);
        // 5
        System.out.println(MICROS_PER_DAY / MILLIS_PER_DAY);
    }
}
