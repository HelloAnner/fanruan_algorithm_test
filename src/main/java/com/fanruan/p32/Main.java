package com.fanruan.p32;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 * 设有n个正整数（n≤30000），将它们联接成一排，组成一个最大的多位整数。
 * 例如：n=3时，3个整数13，312，343联接成的最大整数为：34331213。
 * 又如：n=4时，4个整数7，13，4，246联接成的最大整数为：7424613。
 * 整数的值都在1到100000之间。
 * <p>
 * <a href="https://judge.fineres.com/practice/classify/25/problem/32">#32. 最大整数</a>
 *
 * @author Anner
 * @since 11.0
 * Created on 2024/11/22
 */
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine()); // 读取整数的数量
        String[] numbers = new String[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = reader.readLine(); // 读取每个整数
        }

        // 按照自定义的比较规则对整数进行排序
        Arrays.sort(numbers, (a, b) -> {
            String apb = a + b;
            String bpa = b + a;
            return bpa.compareTo(apb); // 如果bpa > apb，则返回负数，表示a应该排在b前面
        });

        // 将排序后的整数连接成一排，组成最大的多位整数
        StringJoiner joiner = new StringJoiner("");
        for (String number : numbers) {
            joiner.add(number);
        }
        System.out.println(joiner);
    }
}
