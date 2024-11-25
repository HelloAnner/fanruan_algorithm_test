package com.fanruan.p56;

import java.util.Scanner;

/**
 * 工具需要检测的号码特征有两个：号码中要出现至少 3 个相邻的相同数字；号码中不能同时出现 8和4
 * 号码必须同时包含两个特征才满足条件。满足条件的号码例如：13000988721、23333333333、14444101000。而不满足条件的号码例如：1015400080、10010012022。
 * 手机号码一定是 11 位数，不含前导的0 。工具接收两个数 L 和 R ，自动统计出 [L,R] 区间内所有满足条件的号码数量。L 和 R 也是 11 位的手机号码。
 * 输入： L R
 * 输出：只有一行，为一个整数，表示满足条件的手机号数量。
 * 如输入 12121284000 12121285550
 * 输出 5
 * 解释:满足条件的号码有：12121285000、 12121285111、 12121285222、 12121285333、 12121285550
 * <p>
 * 对于30%的数据，R-L小于等于1千万；
 * 对于100%的数据，10的10次方小于等于L小于等于R小于10的11次方。
 *
 * <a href="https://judge.fineres.com/problem/56">#56. 手机号码</a>
 * <p>
 * 数位DP
 *
 * @author Anner
 * @since 11.0
 * Created on 2024/11/25
 */
public class Main {
    static long[][][][][][][] dp = new long[11][11][11][2][2][2][2];
    static int[] num = new int[12];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long l = sc.nextLong();
        long r = sc.nextLong();
        sc.close();

        System.out.println(calc(r) - calc(l - 1));
    }

    static long f(int p, int a, int b, boolean c, boolean d, boolean _4, boolean _8) {
        if (_4 && _8) return 0; // 又有4又有8 不合法
        if (p <= 0) return c ? 1 : 0; // 如果当前出现过连续三个相同的数字返回1，否则返回0
        if (dp[p][a][b][c ? 1 : 0][d ? 1 : 0][_4 ? 1 : 0][_8 ? 1 : 0] != -1) {
            return dp[p][a][b][c ? 1 : 0][d ? 1 : 0][_4 ? 1 : 0][_8 ? 1 : 0]; // 记忆化
        }

        long res = 0;
        int lim = d ? 9 : num[p]; // 如果没有严格小于,只能填到num,如果已经严格小于,就可以随便填
        for (int i = 0; i <= lim; i++) {
            res += f(p - 1, i, a, c || (i == b && i == a), d || (i < lim), _4 || (i == 4), _8 || (i == 8));
        }
        dp[p][a][b][c ? 1 : 0][d ? 1 : 0][_4 ? 1 : 0][_8 ? 1 : 0] = res;
        return res;
    }

    static long calc(long x) {
        if (x < 1e10) return 0; // 不是手机号
        for (long[][][][][][] arr : dp) {
            for (long[][][][][] arr2 : arr) {
                for (long[][][][] arr3 : arr2) {
                    for (long[][][] arr4 : arr3) {
                        for (long[][] arr5 : arr4) {
                            for (long[] arr6 : arr5) {
                                java.util.Arrays.fill(arr6, -1);
                            }
                        }
                    }
                }
            }
        }

        int len = 0;
        for (long tmp = x; tmp > 0; tmp /= 10) {
            num[++len] = (int) (tmp % 10);
        }

        long res = 0;
        for (int i = 1; i <= num[len]; i++) {
            res += f(10, i, 0, false, i < num[len], i == 4, i == 8);
        }
        return res;
    }
}