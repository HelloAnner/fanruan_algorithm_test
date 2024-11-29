package com.fanruan.p17;


import java.util.Scanner;

/**
 * 求出一个具有n个浮点数的向量x中具有和最大的子向量，并输出该子向量的和（该题目为练习题，可以自行实现后，再去查找最优实现算法）。
 * <p>
 * 具有n个浮点数的向量x，其中第一行是一个整数n，表示该向量的浮点数数量。
 * <p>
 * 向量x的任何连续子向量中的最大和。
 * <p>
 * <a href="https://judge.fineres.com/problem/17">#17. 【编程珠玑】子向量最大和</a>
 * <p>
 * Kadane算法
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/11/29
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = sc.nextDouble();
        }
        // 初始化两个变量current_sum和max_sum，分别表示当前最大和和全局最大和，初始值设为向量的第一个元素
        double current_sum = x[0];
        double max_sum = x[0];
        for (int i = 1; i < n; i++) {
            current_sum = Math.max(x[i], current_sum + x[i]);
            max_sum = Math.max(max_sum, current_sum);
        }
        System.out.println(max_sum);
    }
}
