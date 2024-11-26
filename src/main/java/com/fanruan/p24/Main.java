package com.fanruan.p24;


import java.util.Scanner;

/**
 * 定义：
 * f(1)=1, f(2)=1, f(n>2)=f(n-1)+f(n-2)
 * 我们把符合以上定义的序列称为斐波那契序列，现在给你一个数字n，请你求出f(n)的整数位数。
 * 从System.in 输入 100000
 * 输出 20899
 * 注意n的范围在10万到100万之间。
 * <p>
 * <a href="https://judge.fineres.com/problem/24">#24. 斐波那契序列（二）</a>
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/11/26
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        if (n <= 2) {
            System.out.println(1);
        } else {
            // 不能直接递归计算，直接抄一个近似计算的数学公式
            // 比奈公式（Binet's Formula）
            double phi = (1 + Math.sqrt(5)) / 2;
            double sqrt5 = Math.sqrt(5);
            double log_phi = Math.log10(phi);
            double log_sqrt5 = Math.log10(sqrt5);
            int digits = (int) (n * log_phi - log_sqrt5);
            digits += 1;
            System.out.println(digits);
        }
    }
}
