package com.fanruan.p55;


import java.util.Scanner;

/**
 * 给定两个字符串A和B，要求计算B在A中的出现次数。字符串A和B中的字符都是英语字母，可以是大写或小写。在字符串A中，不同位置出现的字符串B可以重叠。
 * 输入格式
 * 输入包含两行，分别是字符串A和字符串B。
 * <p>
 * 输出格式
 * 输出一个整数，表示字符串B在字符串A中的出现次数。
 * <p>
 * 数据范围与提示
 * 字符串A和B的长度范围是1到10^6。
 * 字符串A和B仅包含大写或小写字母。
 *
 * <a href="https://judge.fineres.com/problem/55">#55. 子串查找</a>
 * <p>
 * 手写KMP?
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/11/28
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String A = sc.nextLine();
        String B = sc.nextLine();
        sc.close();

        int lenA = A.length();
        int lenB = B.length();

        if (lenB > lenA) {
            System.out.println(0);
            return;
        }

        // 计算前缀函数pi
        int[] pi = new int[lenB];
        int j = 0;
        for (int i = 1; i < lenB; i++) {
            while (j > 0 && B.charAt(i) != B.charAt(j)) {
                j = pi[j - 1];
            }
            if (B.charAt(i) == B.charAt(j)) {
                j++;
                pi[i] = j;
            }
        }

        // KMP匹配
        j = 0;
        int count = 0;
        for (int i = 0; i < lenA; i++) {
            while (j > 0 && A.charAt(i) != B.charAt(j)) {
                j = pi[j - 1];
            }
            if (A.charAt(i) == B.charAt(j)) {
                j++;
                if (j == lenB) {
                    count++;
                    j = pi[j - 1];
                }
            }
        }

        System.out.println(count);
    }
}
