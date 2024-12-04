package com.fanruan.p49;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

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
 * 1≤N≤10，1≤M≤10，1≤X≤Y≤N，数字不超过int范围。
 * <p>
 * 直接写会超时，维护一个稀疏表还是超时 ， 直接换线段树
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/12/4
 */
public class Main {
    static int[] tree; // 线段树数组
    static int[] nums; // 原始数据数组

    public static void main(String[] args) throws IOException {
        // 快速输入输出
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 解析 N 和 M
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // 解析数字数组
        nums = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        // 初始化和构建线段树
        tree = new int[4 * N];
        build(0, 0, N - 1);

        // 处理查询
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int X = Integer.parseInt(st.nextToken()) - 1; // 转换为 0-based 索引
            int Y = Integer.parseInt(st.nextToken()) - 1; // 转换为 0-based 索引
            result.append(query(0, 0, N - 1, X, Y)).append("\n");
        }

        // 一次性写入所有结果
        bw.write(result.toString());
        bw.flush();
        br.close();
        bw.close();
    }

    // 构建线段树
    static void build(int node, int start, int end) {
        if (start == end) {
            // 叶子节点包含数组的值
            tree[node] = nums[start];
        } else {
            int mid = (start + end) / 2;
            int leftChild = 2 * node + 1;
            int rightChild = 2 * node + 2;

            // 递归构建左右子树
            build(leftChild, start, mid);
            build(rightChild, mid + 1, end);

            // 内部节点包含其子节点的最大值
            tree[node] = Math.max(tree[leftChild], tree[rightChild]);
        }
    }

    // 查询区间 [l, r] 内的最大值
    static int query(int node, int start, int end, int l, int r) {
        if (r < start || l > end) {
            // 区间完全不在当前节点的范围内
            return Integer.MIN_VALUE;
        }
        if (l <= start && end <= r) {
            // 区间完全包含当前节点的范围
            return tree[node];
        }

        // 部分重叠：查询左右子节点
        int mid = (start + end) / 2;
        int leftChild = 2 * node + 1;
        int rightChild = 2 * node + 2;

        return Math.max(query(leftChild, start, mid, l, r),
                query(rightChild, mid + 1, end, l, r));
    }
}