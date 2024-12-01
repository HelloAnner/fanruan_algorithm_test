package com.fanruan.p64;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * 斐波那契数是指可以用斐波那契列中的数，例如：1、2、3、5、8、13、21均为斐波那契数。现任意给定一个正整数，要求设计一个算法，将这个正整数拆分为两个斐波那契数的和。
 * 输入: 10
 * 输出:10=2+8
 * 输入的正整数的值为1~10^9之间。
 * <a href="https://judge.fineres.com/problem/64">#64. 整数分解为斐波那契数</a>
 * <p>
 * 贪心
 *
 * @author Anner
 * @since 11.0
 * Created on 2024/12/1
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        sc.close();

        List<Integer> fib = new ArrayList<>();

        int a = 1, b = 2;
        fib.add(a);
        fib.add(b);

        while (b <= N) {
            int c = a + b;
            if (c > N) break;
            fib.add(c);
            a = b;
            b = c;
        }

        List<Integer> result = new ArrayList<>();
        int remaining = N;

        for (int i = fib.size() - 1; i >= 0; i--) {
            int f = fib.get(i);
            while (remaining >= f) {
                remaining -= f;
                result.add(f);
            }
        }

        if (remaining == 0) {
            System.out.print(N + "=");
            for (int i = result.size() - 1; i >= 0; i--) {
                if (i < result.size() - 1) System.out.print("+");
                System.out.print(result.get(i));
            }
            System.out.println();
        }
    }
}