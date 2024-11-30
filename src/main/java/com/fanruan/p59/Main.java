package com.fanruan.p59;


import java.util.Scanner;

/**
 * 设某汉字由N × N的0和1的点阵图案组成。
 * 我们依照以下规则生成压缩码。连续一组数值：从汉字点阵图案的第一行第一个符号开始计算，按书写顺序从左到右，由上至下。
 * 第一个数表示连续有几个0，第二个数表示接下来连续有几个1，第三个数再接下来连续有几个0，第四个数接着连续几个1，以此类推……
 * 例如: 以下汉字点阵图案：
 * 0001000
 * 0001000
 * 0001111
 * 0001000
 * 0001000
 * 0001000
 * 1111111
 * 对应的压缩码是： 7 3 1 6 1 6 4 3 1 6 1 6 1 3 7（第一个数是N ,其余各位表示交替表示0和1 的个数，压缩码保证 N × N=交替的各位数之和）
 * 输入：一行，压缩码。
 * 输出：汉字点阵图（点阵符号之间不留空格）。（3<=N<=200） 注意换行
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/11/30
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        String[] parts = input.split(" ");
        int[] codes = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            codes[i] = Integer.parseInt(parts[i]);
        }
        int N = codes[0];
        int totalChars = N * N;
        StringBuilder builder = new StringBuilder();
        int index = 1;
        char currentChar = '0';
        while (builder.length() < totalChars && index < codes.length) {
            int count = codes[index];
            for (int i = 0; i < count && builder.length() < totalChars; i++) {
                builder.append(currentChar);
            }
            index++;
            currentChar = (currentChar == '0') ? '1' : '0';
        }
        for (int i = 0; i < totalChars; i += N) {
            String row = builder.substring(i, i + N);
            System.out.println(row);
        }
    }
}
