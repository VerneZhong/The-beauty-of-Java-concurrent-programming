package com.java.concurrent.dailyquestion;

import java.util.Calendar;

/**
 * @author Mr.zxb
 * @date 2019-07-15 11:25
 */
public class Elvis {

    public static final Elvis INSTANCE = new Elvis();

    private final int beltSize;

    private static final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);

    private Elvis() {
        beltSize = CURRENT_YEAR - 1930;
    }

    public int beltSize() {
        return beltSize;
    }

    public static void main(String[] args) {
        // -1930
        System.out.println("Elvis wears size " + INSTANCE.beltSize() + " belt.");
    }
}
