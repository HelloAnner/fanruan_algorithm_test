package com.fanruan.p34;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Scanner;

/**
 * 求两个数组整数数组的交集。
 * <p>
 * 输入格式为总共m+n+2行，第一行值为m，表示数组1的长度，接下来m行为数组1的元素；
 * 接下来的一行值为n，表示数组2的长度，再接下来的n行为数组2的元素。
 * <p>
 * 输出: 数组输出为字符串后的md5编码
 * <p>
 * 空数组，输出为 []
 * <p>
 * <a href="https://judge.fineres.com/problem/34">#34. 两个数组的交集</a>
 * <p>
 * 使用位图 + 排序
 * <p>
 * issue : memory
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/11/25
 */
public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);

        int m = Integer.parseInt(scanner.nextLine());
        int maxValue = 0;
        BitSet bitSet1 = new BitSet();
        for (int i = 0; i < m; i++) {
            int num = Integer.parseInt(scanner.nextLine());
            bitSet1.set(num);
            maxValue = Math.max(maxValue, num);
        }


        int n = Integer.parseInt(scanner.nextLine());
        BitSet bitSet2 = new BitSet(maxValue + 1);
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(scanner.nextLine());
            // 只记录不超过第一个数组最大值的数字
            if (num <= maxValue) {
                bitSet2.set(num);
            }
        }

        bitSet1.and(bitSet2);

        // 提取交集的值
        List<Integer> intersection = new ArrayList<>();
        for (int i = bitSet1.nextSetBit(0); i >= 0; i = bitSet1.nextSetBit(i + 1)) {
            intersection.add(i); // 提取交集值
        }

        String result = intersection.toString();

        String md5Hash = getMD5(result);

        System.out.println(md5Hash);
    }

    /**
     * 计算字符串的 MD5 哈希值
     *
     * @param input 输入字符串
     * @return MD5 哈希值
     */
    private static String getMD5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}