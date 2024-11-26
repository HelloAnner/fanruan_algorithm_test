package com.fanruan.p20;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.BitSet;

/**
 * 文件内有大约100万个不重复的数字，这些数字的值都在[1, 10000000]之间，除此之外，最后一行为数字-1，表示结束，请对除-1以外的其他数字顺序排序。
 * 输入 大约100万个数字的文件。 从System,in 中模拟
 * 输出 排序后的文件
 * <p>
 * <a href="https://judge.fineres.com/problem/20">#20. 【编程珠玑】文件内数字排序</a>
 * <p>
 * 位图
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/11/26
 */
public class Main {
    public static void main(String[] args) {
        BitSet exists = new BitSet(10000001);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in), 8192)) {
            String line;
            while ((line = reader.readLine()) != null) {
                int num = Integer.parseInt(line);
                if (num == -1) {
                    break;
                }
                exists.set(num);
            }
        } catch (IOException ignored) {
        }

        // 这里使用缓冲区优化一下速度，不然会超时
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out), 8192)) {
            for (int i = exists.nextSetBit(1); i != -1; i = exists.nextSetBit(i + 1)) {
                writer.write(i + "\n"); // 写入缓冲区
            }
        } catch (IOException ignored) {
        }
    }
}
