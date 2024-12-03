package com.fanruan.p54;


import java.util.Scanner;

/**
 * 输入两个字符串A和B，合并成一个串C，属于A和B的字符在C中顺序保持不变。如"abc"和"xyz"可以被组合成"axbycz"或"abxcyz"等。
 * 我们定义字符串的价值为其最长回文子串的长度（回文串表示从正反两边看完全一致的字符串，如"aba"和"xyyx"）。
 * 需要求出所有可能的C中价值最大的字符串，输出这个最大价值即可
 * <p>
 * 输入格式
 * 第一行一个整数T。
 * 接下来2T行，每两行两个字符串分别代表a，b，a，b的字符集为全体小写字母。
 * <p>
 * 输出格式
 * 对于每组数据，输出一行一个整数表示价值最大的c的价值。
 * <p>
 * <a href="https://judge.fineres.com/problem/54">#54. 合并回文子串</a>
 * <p>
 * 首先考虑如何生成字符串 c 的所有可能组合。 使用 DP
 * 然后对每个组合计算其最长回文子串的长度，取最大的值。
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/12/1
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 读取方阵大小 n
        int n = scanner.nextInt();
        scanner.nextLine(); // 消耗换行符

        // 定义输入矩阵 a 和辅助数组 d 和 num
        char[][] a = new char[151][151]; // 矩阵表示白格和黑格
        int[][][] d = new int[151][151][151]; // d[i][j][k] 表示从点 (i, j) 开始向右延伸 k 行的连续白格长度
        int[][] num = new int[151][151]; // num[i][j] 表示从点 (i, j) 开始向下连续的白色方格数
        int sum = 0; // 统计最终结果：白色矩形的数量

        // 读取输入矩阵
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < n; j++) {
                a[i][j] = line.charAt(j); // 读取每一格的颜色
            }
        }

        // 从矩阵的最后一行开始处理
        for (int j = 0; j < n; j++) {
            if (a[n - 1][j] == '白') {
                num[n - 1][j] = 1; // 最后一行的白格数量初始化为 1

                // 计算从 (n-1, j) 开始连续的白格数量
                for (int k = j; k < n; k++) {
                    if (a[n - 1][k] != '白') {
                        break;
                    }
                    d[n - 1][j][0] = k - j; // 记录连续白格的长度
                }
            }
        }

        // 逐行向上处理矩阵
        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                if (a[i][j] == '白') {
                    // 更新 num[i][j]：从 (i, j) 开始向下连续白格的数量
                    num[i][j] = num[i + 1][j] + 1;

                    // 继承下一行的 d 值
                    for (int k = 0; k < num[i + 1][j]; k++) {
                        d[i][j][k + 1] = d[i + 1][j][k];
                    }

                    // 计算当前行从 (i, j) 开始向右的连续白格数量
                    for (int k = j; k < n; k++) {
                        if (a[i][k] != '白') {
                            break;
                        }
                        d[i][j][0] = k - j;
                    }
                }
            }
        }

        // 统计白色矩形的数量
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (a[i][j] == '白') {
                    int min = d[i][j][0]; // 初始化最小值为当前点的第一行白格数量

                    // 遍历从 (i, j) 开始的每一行，累计白色矩形数量
                    for (int k = 0; k < num[i][j]; k++) {
                        if (d[i][j][k] < min) {
                            min = d[i][j][k]; // 更新最小值
                        }
                        sum += min; // 累加矩形数量
                    }
                }
            }
        }

        // 输出最终结果
        System.out.println(sum);

        scanner.close();
    }
}