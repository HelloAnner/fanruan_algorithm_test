package com.fanruan.p38;


import java.util.Scanner;

/**
 * 定义一个由0和1组成的序列为“交错序列”，如果序列中没有相邻的1（可以有相邻的0）。例如，000, 001, 101是交错序列，而110不是。
 * <p>
 * 对于长度为n的交错序列，统计0和1出现的次数，分别记为x和y。给定参数a, b，定义交错序列的特征值为x^a * y^b。这里规定任何整数的0次幂都等于1。
 * <p>
 * 我们要求所有长度为n的交错序列的特征值的和，然后除以给定的质数m的余数。
 * <p>
 * 输入格式
 * 一行包含三个整数n, a, b和m。
 * <p>
 * 输出格式
 * 一行，为计算结果。
 * <p>
 * 示例
 * 输入：3 1 2 1009
 *
 * <a href="https://judge.fineres.com/problem/38">#38. 交错序列</a>
 * <p>
 * <a href="https://www.luogu.com.cn/problem/solution/P4456">转移矩阵后快速幂</a>
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/11/28
 */
public class Main {
    static final int size = 185;
    static final long maxv = 1L << 62;
    static int mod;

    static class Mat {
        int n, m;
        int[][] v;

        Mat(int n, int m) {
            this.n = n;
            this.m = m;
            v = new int[n][m];
        }

        int[] get(int i) {
            return v[i];
        }

        Mat multiply(Mat rhs) {
            Mat res = new Mat(n, rhs.m);
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < rhs.m; ++j) {
                    long sum = 0;
                    for (int k = 0; k < m; ++k) {
                        sum += (long) v[i][k] * rhs.v[k][j];
                        if (sum >= maxv) sum %= mod;
                    }
                    res.v[i][j] = (int) (sum % mod);
                }
            }
            return res;
        }
    }

    static int[][] C = new int[size][size];
    static long[] powv = new long[size];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        mod = scanner.nextInt();

        int c = a + b + 1;
        int d = 2 * c;

        // 计算组合数C[i][j]，即C(i, j)
        C[0][0] = 1;
        for (int i = 1; i < c; ++i) {
            C[i][0] = 1;
            for (int j = 1; j <= i; ++j) {
                C[i][j] = (C[i - 1][j - 1] + C[i - 1][j]) % mod;
            }
        }

        // 计算n的幂次，即powv[i] = n^i % mod
        powv[0] = 1;
        for (int i = 1; i <= a; ++i) {
            powv[i] = powv[i - 1] * n % mod;
        }

        // 初始化矩阵mul
        Mat mul = new Mat(d, d);
        for (int i = 0; i < c; ++i) {
            // 0->0
            mul.v[i][i] = 1;
            // 1->0
            mul.v[i + c][i] = 1;
            // 0->1
            for (int j = i; j < c; ++j) {
                mul.v[i][j + c] = C[j][i];
            }
        }

        // 初始化结果矩阵ans
        Mat ans = new Mat(1, d);
        ans.v[0][0] = 1;

        // 使用快速幂算法计算矩阵mul的n次幂
        while (n > 0) {
            if ((n & 1) == 1) ans = ans.multiply(mul);
            n >>= 1;
            mul = mul.multiply(mul);
        }

        // 计算最终结果
        int res = 0;
        for (int i = 0; i <= a; ++i) {
            int p = a + b - i;
            // 计算特征值的系数
            long fac = C[a][i] * (((a - i) & 1) != 0 ? -1 : 1) * powv[i] % mod;
            // 计算特征值的计数
            long cnt = (ans.v[0][p] + ans.v[0][p + c]) % mod;
            res = (int) ((res + cnt * fac) % mod);
        }
        // 输出结果
        System.out.println((res + mod) % mod);
    }
}
