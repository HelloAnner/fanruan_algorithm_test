package com.fanruan.p57;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * <a href="https://judge.fineres.com/problem/57">#57. 【模拟RSA】密钥破解</a>
 * <p>
 * 输入文件内容只有一行，为空格分隔的三个正整数 e N c
 * <p>
 * 输出文件内容只有一行，为空格分隔的两个整数 d, n (where n is the decrypted plaintext)
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/11/21
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 读取输入
        BigInteger e = scanner.nextBigInteger(); // 公钥指数
        BigInteger N = scanner.nextBigInteger(); // 模数
        BigInteger c = scanner.nextBigInteger(); // 密文
        scanner.close();

        // 分解 N，得到 p 和 q
        BigInteger[] factors = factorize(N);
        // 分解失败，其实不会存在这个情况，先留着
        if (factors == null || factors.length != 2) {
            return;
        }
        BigInteger p = factors[0];
        BigInteger q = factors[1];

        // 计算欧拉函数 φ(N) = (p - 1) * (q - 1)
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // 计算 d（私钥指数）
        BigInteger d = e.modInverse(phi);

        // 解密密文，得到明文 n
        BigInteger n = c.modPow(d, N);

        // 输出结果：d 和解密后的明文 n
        System.out.println(d + " " + n);
    }

    /**
     * 分解 N，得到两个素数因子 p 和 q
     * 使用小素数试除和 Pollard's Rho 算法
     *
     * @param N 模数
     * @return 包含两个素数因子的数组 {p, q}
     */
    private static BigInteger[] factorize(BigInteger N) {
        // Step 1: 小素数试除优化
        List<BigInteger> smallPrimes = generateSmallPrimes(100000); // 生成小素数表
        for (BigInteger prime : smallPrimes) {
            if (N.mod(prime).equals(BigInteger.ZERO)) {
                BigInteger p = prime;
                BigInteger q = N.divide(prime);
                return new BigInteger[]{p, q};
            }
        }

        // Step 2: 使用 Pollard's Rho 算法分解
        BigInteger divisor = pollardsRho(N);
        if (divisor != null) {
            BigInteger p = divisor;
            BigInteger q = N.divide(divisor);
            return new BigInteger[]{p, q};
        }

        return null; // 如果无法分解，则返回 null
    }

    /**
     * 使用 Pollard's Rho 算法分解大整数
     *
     * @param N 要分解的整数
     * @return 找到的一个非平凡因子，或 null（如果失败）
     */
    private static BigInteger pollardsRho(BigInteger N) {
        if (N.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            return BigInteger.valueOf(2); // 如果 N 是偶数，直接返回 2
        }

        BigInteger x = new BigInteger(N.bitLength(), new Random()); // 随机选择初始值 x
        BigInteger y = x;
        BigInteger c = BigInteger.valueOf(1); // 常数项 c
        BigInteger d = BigInteger.ONE;

        while (d.equals(BigInteger.ONE)) {
            // f(x) = (x^2 + c) % N
            x = x.multiply(x).mod(N).add(c).mod(N);
            y = y.multiply(y).mod(N).add(c).mod(N);
            y = y.multiply(y).mod(N).add(c).mod(N); // 快速移动 y

            d = x.subtract(y).abs().gcd(N); // 计算 |x-y| 和 N 的最大公约数
        }

        if (d.equals(N)) {
            return null; // 分解失败
        }
        return d; // 返回非平凡因子
    }

    /**
     * 使用埃拉托色尼筛法生成小素数表
     *
     * @param limit 生成小素数的最大值
     * @return 小素数列表
     */
    private static List<BigInteger> generateSmallPrimes(int limit) {
        boolean[] isPrime = new boolean[limit + 1];
        for (int i = 2; i <= limit; i++) {
            isPrime[i] = true;
        }
        for (int i = 2; i * i <= limit; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= limit; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        List<BigInteger> primes = new ArrayList<>();
        for (int i = 2; i <= limit; i++) {
            if (isPrime[i]) {
                primes.add(BigInteger.valueOf(i));
            }
        }
        return primes;
    }
}