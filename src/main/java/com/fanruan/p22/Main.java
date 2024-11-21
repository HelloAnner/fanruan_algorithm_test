package com.fanruan.p22;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 该程序包含一组测试数据，是一个包含1千万个整数的二进制文件，总计大约有10万个不同的整数。
 * 注意这里就不限制整数范围了，需要使用map 了
 *
 * <a href="https://judge.fineres.com/problem/22">#22. 求中位数【二】</a>
 * <p>
 * issue: wrong answer
 *
 * @author Anner
 * @since 11.0
 * Created on 2024/11/22
 */
public class Main {
    public static void main(String[] args) {
        // 二进制输入，无所谓超出范围，可以解析为任何长度的数字，以题意为准
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
        Map<Integer, Integer> countMap = new HashMap<>();

        for (int num : array) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        int totalNumbers = array.length;
        int medianPos1 = totalNumbers / 2;
        int medianPos2 = (totalNumbers % 2 == 0) ? medianPos1 + 1 : medianPos1;

        int cumulativeCount = 0;
        Integer median1 = null;
        Integer median2 = null;

        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            cumulativeCount += entry.getValue();
            if (cumulativeCount >= medianPos1 && median1 == null) {
                median1 = entry.getKey();
            }
            if (cumulativeCount >= medianPos2) {
                median2 = entry.getKey();
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
