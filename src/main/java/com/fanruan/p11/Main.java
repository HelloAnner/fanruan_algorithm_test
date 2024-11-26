package com.fanruan.p11;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 题目描述：
 * 已知一个多项式方程，需要求这个方程在[1, m]区间内的整数解，其中n和m均为正整数。
 * <p>
 * 输入格式：
 * <p>
 * 输入共有n+2行。
 * 第一行包含两个整数n和m，两个整数之间用一个空格隔开。
 * 接下来的n+1行，每行包含一个整数，依次为a0, a1, a2, ..., an。
 * 输出格式：
 * <p>
 * 第一行输出方程在[1, m]内的整数解的个数。
 * 接下来每行输出一个整数，按照从小到大的顺序依次输出方程在[1, m]内的一个整数解。
 * <p>
 * 数据范围与提示：
 * <p>
 * 对于30%的数据：
 * <p>
 * 0 < n ≤ 2
 * |a_i| ≤ 100
 * a_n ≠ 0
 * m < 100
 * 对于50%的数据：
 * <p>
 * 0 < n ≤ 100
 * |a_i| ≤ 10^100
 * a_n ≠ 0
 * m < 100
 * 对于70%的数据：
 * <p>
 * 0 < n ≤ 100
 * |a_i| ≤ 10^10000
 * a_n ≠ 0
 * m < 10^4
 * 对于100%的数据：
 * <p>
 * 0 < n ≤ 100
 * |a_i| ≤ 10^10000
 * a_n ≠ 0
 * m < 10^6
 * <a href="https://judge.fineres.com/problem/11">#11. 解方程</a>
 * <p>
 * <a href="https://www.luogu.com.cn/problem/P2312">P2312 [NOIP2014 提高组] 解方程</a>
 * <p>
 * 秦九韶算法
 * <p>
 * issue: timeout
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/11/26
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] nm = br.readLine().split(" ");
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);

        BigInteger p = new BigInteger("1000000007");
        BigInteger[] A = new BigInteger[n + 1];
        for (int i = 0; i <= n; i++) {
            A[i] = new BigInteger(br.readLine()).mod(p);
        }

        List<Integer> solutions = new ArrayList<>();
        for (int x = 1; x <= m; x++) {
            BigInteger value = A[n];
            for (int i = n - 1; i >= 0; i--) {
                value = value.multiply(BigInteger.valueOf(x)).add(A[i]);
                value = value.mod(p);
            }
            if (value.equals(BigInteger.ZERO)) {
                solutions.add(x);
            }
        }

        System.out.println(solutions.size());
        for (int x : solutions) {
            System.out.println(x);
        }
    }
}