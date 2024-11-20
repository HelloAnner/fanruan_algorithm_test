package com.fanruan.p6;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * @author Anner
 * @since 11.0
 * Created on 2024/11/21
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
