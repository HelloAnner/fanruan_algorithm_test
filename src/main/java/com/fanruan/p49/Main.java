package com.fanruan.p49;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * 题目描述：
 * 输入一串数字，进行M次询问，每次询问给出两个数字X和Y，要求输出X到Y区间内的最大数。
 * <p>
 * 输入格式：
 * 第一行：两个整数N和M，表示数字个数和询问次数；
 * 第二行：N个数字；
 * 接下来M行：每行两个整数X和Y。
 * <p>
 * 输出格式：
 * 共M行，每行输出一个数。
 * <p>
 * 样例输入：
 * 10 2
 * 3 2 4 5 6 8 1 2 9 7
 * 1 4
 * 3 8
 * <p>
 * 样例输出：
 * 5
 * 8
 * <p>
 * 数据范围与提示：
 * 1≤N≤1000000，1≤M≤100000，1≤X≤Y≤N，数字不超过int范围。
 * <p>
 * <a href="https://www.luogu.com.cn/problem/P3865">RMQ 问题</a>
 * <p>
 * ST表 - 超时
 * 分块ST表 - ch
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/12/4
 */

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        // 读取 N 和 M
        String[] parts = br.readLine().split(" ");
        int N = Integer.parseInt(parts[0]);
        int M = Integer.parseInt(parts[1]);

        // 读取数组 a
        parts = br.readLine().split(" ");
        int[] a = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            a[i] = Integer.parseInt(parts[i - 1]);
        }

        // 预计算 log2 数组
        int[] log = new int[N + 1];
        for (int i = 2; i <= N; i++) {
            log[i] = log[i / 2] + 1;
        }

        // 计算最大的 k 值
        int K = log[N] + 1;

        // 初始化稀疏表 dp
        int[][] dp = new int[K][N + 1];
        for (int i = 1; i <= N; i++) {
            dp[0][i] = a[i];
        }

        // 填充稀疏表
        for (int k = 1; k < K; k++) {
            for (int i = 1; i <= N - (1 << k) + 1; i++) {
                dp[k][i] = Math.max(dp[k - 1][i], dp[k - 1][i + (1 << (k - 1))]);
            }
        }

        // 处理 M 个查询
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            parts = br.readLine().split(" ");
            int L = Integer.parseInt(parts[0]);
            int R = Integer.parseInt(parts[1]);
            int length = R - L + 1;
            int k = log[length];
            int maxVal = Math.max(dp[k][L], dp[k][R - (1 << k) + 1]);
            sb.append(maxVal).append("\n");
        }

        pw.print(sb);
        pw.flush();
    }
}