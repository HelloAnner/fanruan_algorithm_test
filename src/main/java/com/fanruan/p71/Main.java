package com.fanruan.p71;


import java.util.Scanner;

/**
 * 给定一叠扑克牌，由甲乙两人轮流从扑克牌的最上面或者最下面取至少一张牌（甲先取，且每个人每次只能选择从最上面取或者从最小面取），
 * 直到取完所有牌为止，两人所取得的牌作为各自的得分，其中2~10分别表示2分~10分，J/Q/K/A分别表示-11/-12/-13/-10分，
 * 大小王表示1分，假设甲和乙都有必要要赢的理由，也就是使得自己得分比对手高，那么甲的最终得分会是多少？
 * <p>
 * 输入:
 * 第一行，一个正整数N，表示有N叠扑克牌。（N<=100）
 * 接着K行，每行第一个数为k，接着k个整数表示分别表示A~K的扑克牌
 * 输出:
 * 输出K行，每行一个整数，表示甲的最终得分
 *
 * <a href="https://judge.fineres.com/problem/71">#71. 取扑克牌定输赢</a>
 * <p>
 * dp[left][right] 表示在 left 到 right 这个范围内，先手能获得的最大得分差（先手得分 - 后手得分）
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/12/7
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        for (int i = 0; i < N; i++) {
            int k = scanner.nextInt();
            int[] value = new int[k];
            for (int j = 0; j < k; j++) {
                value[j] = scanner.nextInt();
            }
            // 计算前缀和
            int[] sum = new int[k + 1];
            for (int j = 0; j < k; j++) {
                sum[j + 1] = sum[j] + value[j];
            }
            // 初始化dp数组
            int[][] dp = new int[k][k];
            for (int length = 1; length <= k; length++) {
                for (int left = 0; left <= k - length; left++) {
                    int right = left + length - 1;
                    if (left == right) {
                        dp[left][right] = value[left];
                    } else {
                        int maxOption1 = Integer.MIN_VALUE;
                        for (int x = 1; x <= length; x++) {
                            // 从左边开始取牌
                            int currentSum = sum[left + x] - sum[left];
                            int nextDp = (left + x <= right) ? dp[left + x][right] : 0;
                            int temp = currentSum - nextDp;
                            if (temp > maxOption1) {
                                maxOption1 = temp;
                            }
                        }
                        int maxOption2 = Integer.MIN_VALUE;
                        for (int y = 1; y <= length; y++) {
                            int currentSum = sum[right + 1] - sum[right - y + 1];
                            int nextDp = (right - y >= left) ? dp[left][right - y] : 0;
                            int temp = currentSum - nextDp;
                            if (temp > maxOption2) {
                                maxOption2 = temp;
                            }
                        }
                        dp[left][right] = Math.max(maxOption1, maxOption2);
                    }
                }
            }
            // 计算得分差
            int scoreDiff = dp[0][k - 1];
            // 计算甲的得分
            int total = sum[k];
            int aScore = (total + scoreDiff) / 2;
            // 输出甲的得分
            System.out.println(aScore);
        }
    }
}
