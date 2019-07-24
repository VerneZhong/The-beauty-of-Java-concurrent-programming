package com.java.concurrent.bit;

/**
 * 位运算
 *
 * @author Mr.zxb
 * @date 2019-07-24 10:48
 */
public class BitOperation {

    public static void main(String[] args) {
//        int a = 35;
//        int b = 47;
//        int c = a + b;

//        35:  0 0 1 0 0 0 1 1
//        47:  0 0 1 0 1 1 1 1
//        ————————————————————
//        82:  0 1 0 1 0 0 1 0

        // 十进制转二进制
        System.out.println(Integer.toBinaryString(35));
        System.out.println(Integer.toBinaryString(47));
        System.out.println(Integer.toBinaryString(82));

        // 当用a乘b，且如果b满足2^N的时候 就相当于把a的二进制数据向左移动N位，放到代码中
        // 我们可以这样来写 a << N,所以上面3 * 2、3 * 4、3 * 8其实是可以写成3<<1、3<<2、3<<3，运算结果都是一样的
        // 那假如相乘的两个数都不满足2^N怎么办呢？其实这个时候编译器会将其中一个数拆分成多个满足2^N的数相加的情况

//        int a = 3;
//        int b = 2; // b = 4, b = 8
//        int c = a * b;
        System.out.println(3 << 1);
        System.out.println(3 << 2);
        System.out.println(3 << 3);

        int a = 15;        //		int a = 15
        int b = 13;     //=>    	int b = (4 + 8 + 1)
        int c = a * b;    //		int c = a * b
        System.out.println(c);
        // 最后其实执行相乘运算就会变成这样 15 * 4 + 15 * 8 + 15 * 1，
        // 按照上文说的移位来转换为位运算就会变成15 << 2 + 15 << 3 + 15 << 0
        System.out.println((15 << 2) + (15 << 3) + (15 << 0));

        System.out.println("-----------");
        // 获取int和long最大最小值
//        System.out.println(getMaxInt());
//        System.out.println(getMaxInt2());
//        System.out.println(getMaxInt3());
//        System.out.println(getMinInt());
//        System.out.println(getMinInt2());
//        System.out.println(getLongMax());
//        System.out.println(getLongMin());

        // 位运算
        System.out.println(mulTwoPower(5, 2));
        System.out.println(isOddNumber(2));

        // 交换位置
        int m = 3, n = 2;
//        swap(m, n);
        m ^= n;
        n ^= m;
        m ^= n;
        System.out.printf("m = %s, n = %s\n", m, n);

        System.out.printf("[abs] = %s\n", abs(-2));
        System.out.printf("[abs] = %s\n", abs2(-2));

        System.out.println(max(2, 5));
        System.out.println(min(2, 5));

        System.out.println(isSameSign(-1, -4));

        System.out.println(getAverage(4, 20));
    }

    /**
     * 获得int型最大值
     *
     * @return
     */
    public static int getMaxInt() {
        return (1 << 31) - 1;
    }

    public static int getMaxInt2() {
        return ~(1 << 31);
    }

    public static int getMaxInt3() {
        // 有些编译器不适用
        return (1 << -1) - 1;
    }

    /**
     * 获得int型最小值
     *
     * @return
     */
    public static int getMinInt() {
        return 1 << 31;
    }

    public static int getMinInt2() {
        return 1 << -1;
    }

    /**
     * 获取long最大值
     *
     * @return
     */
    public static long getLongMax() {
        return ((long) 1 << 127) - 1;
    }

    public static long getLongMin() {
        return ((long) 1 << 127);
    }

    /**
     * 乘以2运算
     *
     * @param n
     * @return
     */
    static int mulTwo(int n) {
        return n << 1;
    }

    /**
     * 除以2运算
     *
     * @param n
     * @return
     */
    static int divTwo(int n) {
        return n >> 1;
    }

    /**
     * 乘以2的m次幂
     *
     * @param n
     * @param m
     * @return
     */
    static int mulTwoPower(int n, int m) {
        return n << m;
    }

    /**
     * 除以2的m次幂
     *
     * @param n
     * @param m
     * @return
     */
    static int divTwoPower(int n, int m) {
        return n >> m;
    }

    /**
     * 判断一个数的奇偶性
     *
     * @param n
     * @return
     */
    static boolean isOddNumber(int n) {
        return (n & 1) == 0;
    }

    /**
     * 不用临时变量交换两个数（面试常考）
     *
     * @param a
     * @param b
     */
    static void swap(int a, int b) {
        a ^= b; // 第一步 a ^= b 即 a = (a ^ b)；
        b ^= a; // 第二步 b ^= a 即 b= b ^ ( a ^ b)，由于异或运算满足交换律，b ^ ( a ^ b) = b ^ b ^ a。
        // 由于一个数和自己异或的结果为 0 并且任何数与 0 异或都会不变的，所以此时 b 被赋上了 a 的值；
        a ^= b; // 第三步 a ^= b 就是 a = a ^ b，由于前面二步可知 a = ( a ^ b)，b=a，所以 a = a ^ b 即 a = ( a ^ b ) ^ a。故 a 会被赋上 b 的值
    }

    /**
     * 取绝对值（某些机器上，效率比n>0  ?  n:-n 高）
     * n>>31 取得n的符号，若n为正数，n>>31等于0，若n为负数，n>>31等于-1
     * 若n为正数 n^0=0,数不变，若n为负数有n^-1 需要计算n和-1的补码，然后进行异或运算，
     * 结果n变号并且为n的绝对值减1，再减去-1就是绝对值
     *
     * @param n
     * @return
     */
    static int abs(int n) {
        return (n ^ (n >> 31)) - (n >> 31);
    }

    static int abs2(int n) {
        return (n > 0) ? n : -n;
    }

    /**
     * 取两个数的最大值（某些机器上，效率比a>b ? a:b高）
     *
     * @param a
     * @param b
     * @return
     */
    static int max(int a, int b) {
        /* 如果a>=b,(a-b)>>31为0，否则为-1 */
        return b & ((a - b) >> 31) | a & (~(a - b) >> 31);
    }

    /**
     * 取两个数的最小值（某些机器上，效率比a>b ? b:a高）
     *
     * @param a
     * @param b
     * @return
     */
    static int min(int a, int b) {
        /* 如果a>=b,(a-b)>>31为0，否则为-1 */
        return a & ((a - b) >> 31) | b & (~(a - b) >> 31);
    }

    /**
     * 判断符号是否相同
     * 同是正整数或负整数为true，一正一负为false
     *
     * @param x
     * @param y
     * @return
     */
    static boolean isSameSign(int x, int y) {
        // 有0的情况例外
        // true 表示 x和y有相同的符号， false表示x，y有相反的符号。
        return (x ^ y) >= 0;
    }

    /**
     * 计算2的n次幂
     *
     * @param n
     * @return
     */
    static int getFactorialofTwo(int n) {
        return 2 << (n - 1);
    }

    /**
     * 判断一个数是不是2的幂
     *
     * @param n
     * @return
     */
    static boolean isFactorialofTwo(int n) {
        return n > 0 ? (n & (n - 1)) == 0 : false;
    }

    /**
     * 对2的n次方取余
     *
     * @param m
     * @param n
     * @return
     */
    static int residual(int m, int n) {
        // n为2的次方
        /* 如果是2的幂，n一定是100... n-1就是1111....
	        所以做与运算结果保留m在n范围的非0的位*/
        return m & (n - 1);
    }

    /**
     * 求两个整数的平均值
     *
     * @param m
     * @param n
     * @return
     */
    static int getAverage(int m, int n) {
        return (m + n) >> 1;
    }

    /**
     * 从低位到高位,取n的第m位
     *
     * @param m
     * @param n
     * @return
     */
    static int getBit(int m, int n) {
        return (n >> (m - 1)) & 1;
    }

    /**
     * 从低位到高位.将n的第m位置1
     *
     * @param n
     * @param m
     * @return
     */
    static int setBitToOne(int n, int m) {
        return n | (1 << (m - 1));
	  /* 将1左移m-1位找到第m位，得到000...1...000 n在和这个数做或运算*/
    }

    /**
     * 从低位到高位,将n的第m位置0
     *
     * @param n
     * @param m
     * @return
     */
    int setBitToZero(int n, int m) {
        return n & ~(1 << (m - 1));
	/* 将1左移m-1位找到第m位，取反后变成111...0...1111 n再和这个数做与运算*/
    }


}
