package com.fanruan.p22;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 该程序包含一组测试数据，是一个包含1千万个整数的二进制文件，总计大约有10万个不同的整数。
 * 注意这里就不限制整数范围了，需要使用map 了
 *
 * <a href="https://judge.fineres.com/problem/22">#22. 求中位数【二】</a>
 * <p>
 * issue: timeout
 *
 * @author Anner
 * @since 11.0
 * Created on 2024/11/22
 */
public class Main {
    public static void main(String[] args) {
        int[] array = readIntArray();
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        // 统计频率
        for (int num : array) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        // 排序不同的数
        List<Integer> uniqueNumbers = new ArrayList<>(frequencyMap.keySet());
        Collections.sort(uniqueNumbers);

        int n = array.length;
        int pos1 = n / 2;       // 第5,000,000个数的位置
        int pos2 = pos1 + 1;    // 第5,000,001个数的位置

        int count = 0;
        int median1 = 0;        // 第5,000,000个数
        int median2 = 0;        // 第5,000,001个数

        // 遍历排序后的不同数，找到中位数位置
        for (int num : uniqueNumbers) {
            int freq = frequencyMap.get(num);
            if (count + freq >= pos1 && median1 == 0) {
                median1 = num;
            }
            if (count + freq >= pos2 && median2 == 0) {
                median2 = num;
                break;
            }
            count += freq;
        }

        // 计算中位数并向下取整
        int median = (median1 + median2) / 2;
        System.out.println(median);
    }

    // 读取二进制文件中的整数数组
    private static int[] readIntArray() {
        int[] array = new int[10000000];
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(System.in))) {
            for (int i = 0; i < 10000000; i++) {
                array[i] = in.readInt();
            }
        } catch (Exception ignore) {
            // 忽略异常
        }
        return array;
    }
}
