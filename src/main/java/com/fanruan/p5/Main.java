package com.fanruan.p5;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;

/**
 * <a href="https://judge.fineres.com/problem/5">#5. 求中位数【一】</a>
 */
public class Main {

    public static void main(String[] args) {
        int[] array = new int[10000000];
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(System.in))) {
            for (int i = 0; i < 10000000; i++) {
                array[i] = in.readInt();
            }
        } catch (Exception ignore) {
        }
        System.out.println(getMedian(array));
    }

    /**
     * 计数排序
     *
     * @param array target
     * @return ans
     */
    public static int getMedian(int[] array) {
        int[] count = new int[100001];

        for (int num : array) {
            count[num]++;
        }

        int totalNumbers = array.length;
        int medianPos1 = totalNumbers / 2;
        int medianPos2 = (totalNumbers % 2 == 0) ? medianPos1 + 1 : medianPos1;

        int cumulativeCount = 0;
        Integer median1 = null;
        Integer median2 = null;

        for (int num = 0; num < count.length; num++) {
            cumulativeCount += count[num];
            if (cumulativeCount >= medianPos1 && median1 == null) {
                median1 = num;
            }
            if (cumulativeCount >= medianPos2) {
                median2 = num;
                break;
            }
        }

        if (totalNumbers % 2 == 0) {
            return (median1 + median2) / 2;
        } else {
            return median1;
        }
    }
}
