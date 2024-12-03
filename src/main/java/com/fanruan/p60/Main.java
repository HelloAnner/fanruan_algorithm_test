package com.fanruan.p60;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 在一个m x n（m行n列） 的矩阵中，在每一行取一个数，并将它们相加，得到一个和，所有的和形成一个数组A。现需要设计一个算法，可以输出正序排序下数组A的前N个元素。
 * 输入:
 * 第一行，三个数m,n,N。
 * 第2~m+1行，每行n个正整数
 * <p>
 * 例如输入:
 * 3 3 2
 * 8657835 6388365 4406730
 * 9596574 1765206 1740440
 * 1054683 1122743 5835610
 * <p>
 * 输出:
 * 7201853 7226619
 * <p>
 * 所有的测试用例数据中m,n以及N均不大于800，且N始终是小于等于n的。
 * <p>
 * <a href="https://judge.fineres.com/problem/60">#60. 矩阵中获取较小的和</a>
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/12/3
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] tokens = br.readLine().split(" ");
        int m = Integer.parseInt(tokens[0]); // 矩阵的行数
        int n = Integer.parseInt(tokens[1]); // 矩阵的列数
        int N = Integer.parseInt(tokens[2]); // 需要的最小和数量

        int[][] matrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            tokens = br.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(tokens[j]);
            }
            Arrays.sort(matrix[i]); // 对每一行进行排序，方便后续取最小值
        }

        // 初始化dp数组为第一行的前N个元素
        int[] dp = Arrays.copyOf(matrix[0], N);

        // 动态更新dp数组，逐行合并最小和
        for (int i = 1; i < m; i++) {
            dp = merge(dp, matrix[i], N);
        }

        // 输出结果
        for (int i = 0; i < N; i++) {
            System.out.print(dp[i] + " ");
        }
    }

    /**
     * 合并两个数组，取前N个最小的和
     */
    public static int[] merge(int[] dp, int[] row, int N) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> (a[0] - b[0])); // 最小堆
        int[] result = new int[N];
        int rowLength = row.length;

        // 将dp[0] + row[0]作为起始点加入堆
        heap.offer(new int[]{dp[0] + row[0], 0, 0});

        // 记录结果数组的索引
        int index = 0;

        // 记录是否访问过某个组合
        boolean[][] visited = new boolean[N][rowLength];
        visited[0][0] = true;

        // 合并操作，取前N个最小的和
        while (index < N && !heap.isEmpty()) {
            int[] top = heap.poll(); // 取出堆顶元素（最小和）
            result[index++] = top[0]; // 将最小和加入结果数组

            int i = top[1]; // dp数组中的索引
            int j = top[2]; // 当前行中的索引

            // 尝试加入dp[i+1] + row[j]
            if (i + 1 < dp.length && !visited[i + 1][j]) {
                heap.offer(new int[]{dp[i + 1] + row[j], i + 1, j});
                visited[i + 1][j] = true;
            }

            // 尝试加入dp[i] + row[j+1]
            if (j + 1 < rowLength && !visited[i][j + 1]) {
                heap.offer(new int[]{dp[i] + row[j + 1], i, j + 1});
                visited[i][j + 1] = true;
            }
        }

        return result;
    }
}