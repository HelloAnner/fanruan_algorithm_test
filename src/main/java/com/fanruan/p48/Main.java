package com.fanruan.p48;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 你的面前有n个数排成一行，分别为a1, a2, ..., an。你打算在每相邻的两个ai和ai+1间都插入一个加号、减号或者乘号。那么一共有3^(n-1)种可能的表达式。
 * <p>
 * 你对所有可能的表达式的值的和非常感兴趣。但这毕竟太简单了，所以你还打算支持一个修改操作，可以修改某个ai的值。
 * <p>
 * 你能够编写一个程序对每个修改都输出修改完之后所有可能表达式的和吗？注意，修改是永久的，也就是说每次修改都是在上一次修改的基础上进行，而不是在最初的表达式上进行。
 * <p>
 * 输入格式
 * <p>
 * 第一行包含两个正整数n和Q，为数的个数和询问的个数。
 * 第二行包含n个非负整数，依次表示a1, a2, ..., an。
 * 接下来Q行，每行包含两个非负整数t和v，表示要将at修改为v，其中1 ≤ t ≤ n。
 * <p>
 * 保证对于1 ≤ j ≤ n, 1 ≤ i ≤ Q，都有aj, vj ≤ 10^4。
 * <p>
 * 输出格式
 * <p>
 * 输出Q行。对于每个修改输出一行，包含一个整数，表示修改之后所有可能表达式的和，对10^9 + 7取模。
 * 如输入：
 * 5 5
 * 9384 887 2778 6916 7794
 * 2 8336
 * 5 493
 * 3 1422
 * 1 28
 * 4 60
 * 输出:
 * 890543652
 * 252923708
 * 942282590
 * 228728040
 * 608998099
 * <a href="https://judge.fineres.com/problem/48">#48. 随机序列</a>
 * <p>
 * <a href="https://www.luogu.com.cn/problem/P4340">P4340 [SHOI2016] 随机序列</a>
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/12/1
 */
public class Main {
    static final int MOD = 1000000007;
    static int N, Q;
    static int[] A, Pow, Mul, Ans;

    public static void main(String[] args) throws IOException {
        // 使用 BufferedReader 和 StringBuilder 优化 I/O
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        A = new int[N + 1];
        Pow = new int[N + 1];
        Mul = new int[4 * N];
        Ans = new int[4 * N];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        // 预计算 Pow 数组
        Pow[0] = 1;
        for (int i = 1; i <= N; i++) {
            Pow[i] = (int) ((3L * Pow[i - 1]) % MOD);
        }

        // 建立线段树
        build(1, 1, N);

        // 处理每个修改查询
        while (Q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            modify(1, 1, N, t, v);
            sb.append(Ans[1]).append("\n");
        }

        // 输出所有结果
        System.out.print(sb);
    }

    // 建立线段树
    static void build(int i, int l, int r) {
        if (l == r) {
            Mul[i] = A[l];
            if (l == N) {
                Ans[i] = A[l];
            } else {
                Ans[i] = (int) ((A[l] * 2L * Pow[N - l - 1]) % MOD);
            }
            return;
        }

        int m = (l + r) / 2;
        build(i * 2, l, m);
        build(i * 2 + 1, m + 1, r);
        Mul[i] = (int) ((1L * Mul[i * 2] * Mul[i * 2 + 1]) % MOD);
        Ans[i] = (int) ((1L * Mul[i * 2] * Ans[i * 2 + 1] + Ans[i * 2]) % MOD);
    }

    // 修改线段树
    static void modify(int i, int l, int r, int pos, int v) {
        if (l == r) {
            A[l] = v;
            Mul[i] = A[l];
            if (l == N) {
                Ans[i] = A[l];
            } else {
                Ans[i] = (int) ((A[l] * 2L * Pow[N - l - 1]) % MOD);
            }
            return;
        }

        int m = (l + r) / 2;
        if (pos <= m) {
            modify(i * 2, l, m, pos, v);
        } else {
            modify(i * 2 + 1, m + 1, r, pos, v);
        }
        Mul[i] = (int) ((1L * Mul[i * 2] * Mul[i * 2 + 1]) % MOD);
        Ans[i] = (int) ((1L * Mul[i * 2] * Ans[i * 2 + 1] + Ans[i * 2]) % MOD);
    }
}