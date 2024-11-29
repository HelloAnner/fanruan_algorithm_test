package com.fanruan.p41;


import java.util.Scanner;

/**
 * 将一个n元一维数组a[n]左移i个位置。例如，当n=8，i=3时，数组abcdefgh旋转为defghabc。请设计一个算法完成这个任务。
 * <p>
 * 从System.in 输入两行，第一行为 i ， 第二行为一个字符串，代表即将翻转的数组
 * <a href="https://judge.fineres.com/problem/41">#41. 【编程珠玑】数组旋转</a>
 * <p>
 * 反转数组的前i个元素：这样前i个元素的顺序会反转。
 * <p>
 * 反转数组的后n-i个元素：这样后n-i个元素的顺序会反转。
 * <p>
 * 反转整个数组：这样整个数组的顺序会再次反转，最终达到左移i个位置的效果。
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/11/29
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        scanner.nextLine(); // 消耗换行符
        String s = scanner.nextLine();
        int n = s.length();
        i = i % n;
        char[] arr = s.toCharArray();
        reverse(arr, 0, i - 1);
        reverse(arr, i, n - 1);
        reverse(arr, 0, n - 1);
        System.out.println(new String(arr));
    }

    public static void reverse(char[] arr, int start, int end) {
        while (start < end) {
            char temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
}
