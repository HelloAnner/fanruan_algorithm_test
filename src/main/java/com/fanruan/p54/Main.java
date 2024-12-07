package com.fanruan.p54;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 输入两个字符串A和B，合并成一个串C，属于A和B的字符在C中顺序保持不变。如"abc"和"xyz"可以被组合成"axbycz"或"abxcyz"等。
 * 我们定义字符串的价值为其最长回文子串的长度（回文串表示从正反两边看完全一致的字符串，如"aba"和"xyyx"）。
 * 需要求出所有可能的C中价值最大的字符串，输出这个最大价值即可。
 * <p>
 * 输入格式：
 * 第一行一个整数T。
 * 接下来2T行，每两行两个字符串分别代表a，b，a，b的字符集为全体小写字母。
 * <p>
 * 输出格式：
 * 对于每组数据，输出一行一个整数表示价值最大的c的价值。
 * <p>
 * <a href="https://judge.fineres.com/problem/54">#54. 合并回文子串</a>
 */
public class Main {
    public static void main(String[] args) throws IOException {
        // 使用 BufferedReader 提高输入效率
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append(" ");
        }
        String[] tokens = sb.toString().split("\\s+");
        int index = 0;
        int t = Integer.parseInt(tokens[index++]);

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        while (t-- > 0) {
            String a = tokens[index++];
            String b = tokens[index++];
            int na = a.length();
            int nb = b.length();
            int ans = 1;
            a = "#" + a; // 前缀添加 '#' 以便从索引1开始
            b = "#" + b;
            // 四维布尔数组，表示从 a[la..ra] 和 b[lb..rb] 能否形成回文
            boolean[][][][] dp = new boolean[na + 2][na + 2][nb + 2][nb + 2];

            for (int lena = 0; lena <= na; ++lena) {
                for (int lenb = 0; lenb <= nb; ++lenb) {
                    for (int la = 1, ra = la + lena - 1; ra <= na; ++la, ++ra) {
                        for (int lb = 1, rb = lb + lenb - 1; rb <= nb; ++lb, ++rb) {
                            if (lena + lenb <= 1) {
                                dp[la][ra][lb][rb] = true;
                            } else {
                                dp[la][ra][lb][rb] = false;
                                // 检查 a 的两端字符是否相等，并且内部子串也是回文
                                if (lena > 1 && a.charAt(la) == a.charAt(ra)) {
                                    dp[la][ra][lb][rb] |= dp[la + 1][ra - 1][lb][rb];
                                }
                                // 检查 b 的两端字符是否相等，并且内部子串也是回文
                                if (lenb > 1 && b.charAt(lb) == b.charAt(rb)) {
                                    dp[la][ra][lb][rb] |= dp[la][ra][lb + 1][rb - 1];
                                }
                                // 检查 a 的左端和 b 的右端字符是否相等，并且剩余部分也是回文
                                if (lena != 0 && lenb != 0 && a.charAt(la) == b.charAt(rb)) {
                                    dp[la][ra][lb][rb] |= dp[la + 1][ra][lb][rb - 1];
                                }
                                // 检查 b 的左端和 a 的右端字符是否相等，并且剩余部分也是回文
                                if (lena != 0 && lenb != 0 && b.charAt(lb) == a.charAt(ra)) {
                                    dp[la][ra][lb][rb] |= dp[la][ra - 1][lb + 1][rb];
                                }
                            }
                            if (dp[la][ra][lb][rb]) {
                                ans = Math.max(ans, lena + lenb);
                            }
                        }
                    }
                }
            }
            bufferedWriter.write(ans + "\n");
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}