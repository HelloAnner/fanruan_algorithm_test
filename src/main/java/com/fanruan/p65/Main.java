package com.fanruan.p65;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 一群人外出春游，准备夜晚时候绕着湖边搭建帐篷过夜，在未休息时，大家都可能去不同的帐篷玩耍，这样每个帐篷的人数就是不同的了。
 * 当晚上开始准备休息的时候，要求每个人只能从当前帐篷直线走到相邻的帐篷（可连续走多个帐篷，但要先到达相邻的帐篷），且最终每个帐篷的人数是一样的。
 * 假设每从一个帐篷到达另外一个帐篷，即为行走1次，请问要达到让每个帐篷人数是一样的目的，最少的行走次数是多少次。
 * <p>
 * 输入:
 * 第一行一个正整数 n，表示有 n 个帐篷。
 * 第二行 n个正整数，表示 n 个帐篷中当前的人数。
 * <p>
 * 输出最少的行走次数。
 * 1≤n≤100。
 * <a href="https://judge.fineres.com/problem/65">#65. 帐篷分配问题</a>
 * <p>
 * 前缀和思想
 * 通过选择中位数作为起点，我们可以最小化每个帐篷的累积差值与某个值的绝对差值之和，从而得到最小的行走次数。
 * 这种方法利用了中位数在最小化绝对差值和中的特性，确保了每个帐篷中的人数相同，并且行走次数最少。
 *
 * @author Anner
 * @since 11.0
 * Created on 2024/12/1
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int total = 0;
        for (int num : a) {
            total += num;
        }
        if (total % n != 0) {
            System.out.println(-1);
            return;
        }
        int avg = total / n;

        // 每个帐篷中的人数与平均人数的差值
        double[] sum = new double[n];
        sum[0] = 0.0;
        for (int i = 1; i < n; i++) {
            // 计算累积差值
            sum[i] = sum[i - 1] + a[i - 1] - avg;
        }

        Arrays.sort(sum);

        // 计算累积差值数组的中位数
        double median;
        if (n % 2 == 1) {
            median = sum[n / 2];
        } else {
            median = (sum[n / 2 - 1] + sum[n / 2]) / 2.0;
        }

        // 计算每个帐篷的累积差值与中位数的绝对差值之和，得到最小行走次数。
        long walking = 0;
        for (int i = 0; i < n; i++) {
            walking += Math.round(Math.abs(median - sum[i]));
        }

        System.out.println(walking);
    }
}
