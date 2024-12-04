package com.fanruan.p49;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
 * <a href="https://judge.fineres.com/problem/49">#49. 数列区间最大值</a>
 * <p>
 * <a href="https://www.luogu.com.cn/problem/P3865">RMQ 问题</a>
 * <p>
 * ST表 - 超时
 * 分块ST表 - 超时 ，就差10ms ， tu le
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/12/4
 */

public class Main {
    static final int MAX_K = 20;
    static int[] log2_table;

    static int log2(int x) {
        return 31 - Integer.numberOfLeadingZeros(x);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        String[] input = br.readLine().split(" ");
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);

        // 预计算对数表
        log2_table = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            log2_table[i] = log2(i);
        }

        // 读取数字序列
        input = br.readLine().split(" ");
        int[] a0 = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a0[i] = Integer.parseInt(input[i - 1]);
        }

        // 构建ST表
        int max_k = log2(n);
        int[][] st = new int[MAX_K][n + 1];
        for (int i = 1; i <= n; i++) {
            st[0][i] = a0[i];
        }
        for (int k = 1; k < MAX_K; k++) {
            for (int j = 1; j <= n - (1 << k) + 1; j++) {
                st[k][j] = Math.max(st[k - 1][j], st[k - 1][j + (1 << (k - 1))]);
            }
        }

        // 处理查询
        for (int i = 0; i < m; i++) {
            input = br.readLine().split(" ");
            int l = Integer.parseInt(input[0]);
            int r = Integer.parseInt(input[1]);
            int length = r - l + 1;
            int k = log2_table[length];
            int max = Math.max(st[k][l], st[k][r - (1 << k) + 1]);
            pw.println(max);
        }

        pw.flush();
        pw.close();
        br.close();
    }
}