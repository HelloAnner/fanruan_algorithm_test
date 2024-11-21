package com.fanruan.p4;

import java.util.Scanner;

/**
 * 给你一个数组，输出里面出现超过1/2的元素。保证有且只有一个解。
 * 第一行是一个整数n(n <=1e7)，表示测试数据的个数 ，之后每一行都是一个整数。
 * 输出出现超过1/2的那个数字。
 * <a href="https://judge.fineres.com/practice/classify/25/problem/4">#4. 数组中出现最多的元素</a>
 *
 * @author Anner
 * @since 11.0
 * Created on 2024/11/22
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        scanner.close();

        System.out.println(findMajorityElement(nums));
    }

    /**
     * 摩尔投票
     *
     * @param nums nums
     * @return ans
     */
    private static int findMajorityElement(int[] nums) {
        int count = 0;
        Integer candidate = null;

        // 计算占比例最大的元素的值
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }

        int finalCount = 0;
        for (int num : nums) {
            if (num == candidate) finalCount++;
        }
        return (finalCount > nums.length / 2) ? candidate : -1; // 如果候选元素出现次数超过一半则返回，否则返回-1
    }
}
