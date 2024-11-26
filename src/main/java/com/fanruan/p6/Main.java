package com.fanruan.p6;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * @author Anner
 * @since 11.0
 * Created on 2024/11/21
 * <p>
 * 定义：
 * <p>
 * f(1)=1, f(2)=1, f(n>2)=f(n-1)+f(n-2)
 * <p>
 * 我们把符合以上定义的序列称为斐波那契序列，现在给你一个数字n，请你求出f(n)。
 * n不会超过1000。
 *
 * <a href="https://judge.fineres.com/problem/6">#6. 斐波那契数列</a>
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            long n = scanner.nextLong();
            if (n == -1) {
                break; // 结束输入
            }
            System.out.println(fibonacci(n));
        }
        scanner.close();
    }

    private static BigInteger fibonacci(long n) {
        if (n == 0) {
            return BigInteger.ZERO;
        }
        if (n == 1) {
            return BigInteger.ONE;
        }

        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;
        BigInteger c;

        for (long i = 2; i <= n; i++) {
            c = a.add(b);
            a = b;
            b = c;
        }
        return b;
    }
}
