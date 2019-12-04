package com.java.concurrent.dailyquestion;

/**
 * @author Mr.zxb
 * @date 2019-07-17 14:14
 */
public class AnimalFarm {

    public static void main(String[] args) {
        final String pig = "length: 10";
        final String dog = "length: " + pig.length();
        // false
        System.out.println("Animals are equal: "
                + pig == dog);
    }
}
