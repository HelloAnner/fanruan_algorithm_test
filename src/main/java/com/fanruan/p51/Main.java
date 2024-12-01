package com.fanruan.p51;

import java.util.Scanner;

/**
 * 黄王在学等差数列和等比数列，当已知前三项时，就可以知道是等差数列还是等比数列。
 * 现在给你序列的前三项，这个序列要么是等差序列，要么是等比序列，你能求出第k项的值吗。如果第k项的值太大，对200907取模。
 * <p>
 * 输入格式
 * 第一行一个整数T，表示有T组测试数据；
 * 对于每组测试数据，输入前三项a，b，c，然后输入k。
 * <p>
 * 输出格式
 * 对于每组数据输出第k项的值，对200907取模。
 * 对于全部数据，1≤T≤100，1<a≤b≤c≤10，1≤k≤10。
 *
 * <a href="https://judge.fineres.com/problem/51">#51. 序列的第 k 个数</a>
 * <p>
 * 数学
 *
 * @author Anner
 * @since 11.0
 * Created on 2024/12/1
 */

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        while (T-- > 0) {
            long a = scanner.nextLong();
            long b = scanner.nextLong();
            long c = scanner.nextLong();
            long k = scanner.nextLong();

            if (a + c == 2 * b) { // 判断是否为等差数列
                // 等差数列的第 k 项公式：a + (k - 1) * (b - a)
                long result = (a + (k - 1) * (b - a)) % 200907;
                System.out.println(result);
            } else { // 否则为等比数列
                // 等比数列的第 k 项公式：a * (b / a)^(k - 1)
                long ratio = b / a; // 计算公比
                long result = (power(ratio, k - 1, 200907) * a) % 200907;
                System.out.println(result);
            }
        }
        scanner.close();
    }

    // 快速幂函数，计算 (base^exponent) % mod
    public static long power(long base, long exponent, long mod) {
        long result = 1;
        while (exponent > 0) {
            if ((exponent & 1) == 1) { // 如果 exponent 是奇数
                result = (result * base) % mod;
            }
            base = (base * base) % mod; // base 自乘
            exponent >>= 1; // exponent 右移一位，相当于 exponent /= 2
        }
        return result;
    }
}