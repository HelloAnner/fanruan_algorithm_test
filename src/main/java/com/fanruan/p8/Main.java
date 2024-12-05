package com.fanruan.p8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 分别给定n×p和p×m的两个矩阵A和B，求A×B。
 * 输入格式
 * 第一行三个正整数n、p、m，表示矩阵的长宽。
 * 之后的n行，每行p个整数，表示矩阵A。
 * 之后的p行，每行m个整数，表示矩阵B。
 * <p>
 * 输出格式
 * 输出n行，每行m个整数，表示矩阵A×B，每个数模10^9+7输出。
 * <p>
 * 例如输入:
 * 3 4 5
 * -2 -8 -9 8
 * -10 0 6 -8
 * -10 -6 6 9
 * 4 -7 5 -5 9
 * 10 -2 -10 5 5
 * -3 -7 -3 8 -2
 * -6 7 7 3 -2
 * 输出：
 * 999999898 149 153 999999929 999999951
 * 999999997 999999979 999999883 74 999999921
 * 999999835 103 55 95 999999857
 * <p>
 * <a href="https://judge.fineres.com/problem/8">#8. 矩阵乘法</a>
 * <p>
 * 暴力写法其实也可以过，没想到吧
 * <p>
 * 矩阵快速幂可以更快
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/12/5
 */
public class Main {
    private static final int MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] A = readMatrix(br, n, p);
        int[][] B = readMatrix(br, p, m);
        int[][] result = multiply(A, B, n, p, m);
        printMatrix(result, n, m);
    }

    private static int[][] readMatrix(BufferedReader br, int rows, int cols) throws IOException {
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        return matrix;
    }

    private static int[][] multiply(int[][] A, int[][] B, int n, int p, int m) {
        int[][] result = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                long sum = 0;
                for (int k = 0; k < p; k++) {
                    sum += (long) A[i][k] * B[k][j];
                    sum %= MOD;
                }
                result[i][j] = (int) ((sum % MOD + MOD) % MOD);
            }
        }
        return result;
    }

    private static void printMatrix(int[][] matrix, int n, int m) {
        for (int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < m; j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            if (sb.length() > 0) {
                sb.setLength(sb.length() - 1);
            }
            System.out.println(sb);
        }
    }
}
