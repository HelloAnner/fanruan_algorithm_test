package com.fanruan.p35;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 对于两个给定的序列，请求出它们的最长公共子序列长度。
 * 一个序列的子序列定义为能通过删除一部分元素，保留剩下的元素相对顺序不变而得到的序列
 * 输入格式
 * 第一行两个整数 n, m，表示两个序列的长度。
 * 第二行 n 个整数 a1, a2, ..., an，表示第一个序列。
 * 第二行 m 个整数 b1, b2, ..., bm，表示第二个序列。
 * <p>
 * 输出格式
 * 输出两个序列的最长公共子序列长度。
 * <p>
 * 对于所有数据，1 ≤ n, m, ai, bi ≤ 70000。
 * <p>
 * <a href="https://judge.fineres.com/problem/35">#35. 最长公共子序列</a>
 *
 * @author Anner
 * @since 11.0
 * Created on 2024/12/1
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(reader.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] a = new int[n];
        st = new StringTokenizer(reader.readLine());
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }

        int[] b = new int[m];
        st = new StringTokenizer(reader.readLine());
        for (int i = 0; i < m; i++) {
            b[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(lcs(a, b));
    }

    private static int lcs(int[] a, int[] b) {
        Map<Integer, List<Integer>> posMap = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            posMap.computeIfAbsent(a[i], k -> new ArrayList<>()).add(i);
        }

        List<Integer> positions = new ArrayList<>();
        for (int num : b) {
            if (posMap.containsKey(num)) {
                for (int p : posMap.get(num)) {
                    positions.add(p);
                }
            }
        }

        return lengthOfLIS(positions);
    }

    private static int lengthOfLIS(List<Integer> sequence) {
        List<Integer> tails = new ArrayList<>();
        for (int num : sequence) {
            int idx = Collections.binarySearch(tails, num);
            if (idx < 0) idx = -(idx + 1);
            if (idx == tails.size()) {
                tails.add(num);
            } else {
                tails.set(idx, num);
            }
        }
        return tails.size();
    }
}
