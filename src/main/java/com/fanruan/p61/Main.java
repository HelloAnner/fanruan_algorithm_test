package com.fanruan.p61;


import java.util.Scanner;

/**
 * 给出一个n阶方阵，在方阵中有些格子是白色，有些格子是黑色，现要设计一个算法，计算出方阵中白色矩形的数量。
 * 输入:
 * 第一行，一个整数n，表示矩形的大小。
 * 接下来n行，每行n个字符，这些字符为"白"或"黑"。其中"白"表示白格，"黑"表示黑格。
 * 输出:
 * 一个正整数，为白色矩形数量。
 * <p>
 * 例如输入:
 * 4
 * 黑黑白黑
 * 黑白黑黑
 * 黑白白白
 * 白黑黑白
 * 输出：12
 * <p>
 * <a href="https://judge.fineres.com/problem/61">#61. 染色矩阵问题</a>
 * <p>
 * DP
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/12/3
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 读取矩阵的大小n
        int n = sc.nextInt();

        // 读取n行的矩阵数据
        String[] lines = new String[n];
        for (int i = 0; i < n; i++) {
            lines[i] = sc.next();
        }

        // 初始化矩阵a
        char[][] a = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = lines[i].charAt(j);
            }
        }

        // 初始化辅助数组d和num
        int[][][] d = new int[n][n][n];
        int[][] num = new int[n][n];
        int sum = 0;

        // 处理最后一行
        for (int j = 0; j < n; j++) {
            if (a[n - 1][j] == '白') {
                num[n - 1][j] = 1;
                int k;
                for (k = j; k < n; k++) {
                    if (a[n - 1][k] != '白') {
                        break;
                    }
                }
                d[n - 1][j][0] = k - j;
            }
        }

        // 从倒数第二行开始向上处理
        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                if (a[i][j] == '白') {
                    num[i][j] = num[i + 1][j] + 1;
                    for (int k = 0; k < num[i + 1][j]; k++) {
                        d[i][j][k + 1] = d[i + 1][j][k];
                    }
                    int k;
                    for (k = j; k < n; k++) {
                        if (a[i][k] != '白') {
                            break;
                        }
                    }
                    d[i][j][0] = k - j;
                }
            }
        }

        // 计算白色矩形的数量
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (a[i][j] == '白') {
                    int min = d[i][j][0];
                    for (int k = 0; k < num[i][j]; k++) {
                        if (d[i][j][k] < min) {
                            min = d[i][j][k];
                        }
                        sum += min;
                    }
                }
            }
        }

        // 输出结果
        System.out.println(sum);
    }
}
