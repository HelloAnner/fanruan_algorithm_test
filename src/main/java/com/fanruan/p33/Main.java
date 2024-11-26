package com.fanruan.p33;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 给定两个有序整数数组 num1 和 num2，将两个有序数组合并。
 * 输入格式为m+n+2行，第一行值为m，表示数组1的长度，接下来m行为数组1的元素，接下来的一行值为n，表示数组2的长度，再接下来的n行为数组2的元素。
 * 数组输出为字符串后的md5编码：cd571095bb59f5c0c0fbb4532e756ac8 注意：数字转为字符串并生成md5编码时，要转成[123, 345, 789]这样格式的字符串。
 *
 * <a href="https://judge.fineres.com/problem/33">#33. 合并两个有序数组</a>
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/11/26
 */
public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);

        // 读取数组1的长度
        int m = scanner.nextInt();
        int[] num1 = new int[m];
        for (int i = 0; i < m; i++) {
            num1[i] = scanner.nextInt();
        }

        // 读取数组2的长度
        int n = scanner.nextInt();
        int[] num2 = new int[n];
        for (int i = 0; i < n; i++) {
            num2[i] = scanner.nextInt();
        }

        // 合并两个有序数组
        int[] merged = new int[m + n];
        int i = 0, j = 0, k = 0;
        while (i < m && j < n) {
            if (num1[i] < num2[j]) {
                merged[k++] = num1[i++];
            } else {
                merged[k++] = num2[j++];
            }
        }
        // 处理剩余元素
        while (i < m) {
            merged[k++] = num1[i++];
        }
        while (j < n) {
            merged[k++] = num2[j++];
        }

        // 计算MD5值
        String md5 = md5(Arrays.toString(merged));
        System.out.println(md5);
    }

    // 计算MD5哈希值的方法
    public static String md5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : messageDigest) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString().toLowerCase();
    }
}
