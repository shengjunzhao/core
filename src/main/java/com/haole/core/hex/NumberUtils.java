package com.haole.core.hex;

/**
 * Created by shengjunzhao on 2018/9/10.
 */
public class NumberUtils {

    /**
     * The maximum capacity, used if a higher value is implicitly specified
     * by either of the constructors with arguments.
     * MUST be a power of two <= 1<<30.
     */
    public static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 尝试修正一个整数，并转换为离该整数最近的2次幂
     *
     * @param cap
     * @return
     */
    public static final int ceilingPowerOfTwo(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    /**
     * 判断一个数字是否是2次幂
     * (val & -val) == val; 判断val是否是2的次方，(val & -val)=2 是偶数，(val & -val)=1是奇数
     * @param n
     * @return
     */
    public static final boolean powerOf2(int n) {
        return (n & (n - 1)) == 0;
    }

    /**
     * 用位运算计算n个bit能表示的最大数值
     * @param bit
     * @return
     */
    public static long maxOfbit(int bit) {
        return -1L ^ (-1 << bit);
    }
}
