package com.fanruan.p58;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 在文章阅读的过程中，我们会选取文章中的部分文字，打上注释评论。如果选取的文字内容有交集，那将会自动合并所选取的文字内容以及评论。 例如：
 * 对于文章内容：“我是中国人”。
 * 小王选取了"我是"，并做出了2条评论。
 * 小李选取了“是中国人”，并做出了3条评论。
 * 则自动合并为，选取了“我是中国人”，并做出了5条评论。
 * <p>
 * 输入:
 * 每一行数据为一组选取范围和评论数量。例如：1 2 10 表示“选取了第1个字符到第2个字符，并给出了10条评论”。
 * 1 2 1
 * 2 3 10
 * 5 10 11
 * 6 11 2
 * 输出:
 * 输出结果按选取的起始字符位置从小到大进行排序。
 * 1 3 11
 * 5 11 13
 * <p>
 * <a href="https://judge.fineres.com/problem/58">区间分组汇总</a>
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/12/7
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if (!line.isEmpty()) {
                lines.add(line);
            }
        }
        List<int[]> intervals = new ArrayList<>(lines.size());
        for (String data : lines) {
            String[] parts = data.split(" ");
            if (parts.length != 3) continue;
            int start = Integer.parseInt(parts[0]);
            int end = Integer.parseInt(parts[1]);
            int comments = Integer.parseInt(parts[2]);
            intervals.add(new int[]{start, end, comments});
        }
        intervals.sort(Comparator.comparingInt(a -> a[0]));
        // 合并区间
        List<int[]> merged = new ArrayList<>();
        int[] current = intervals.get(0);
        merged.add(current);
        for (int i = 1; i < intervals.size(); i++) {
            int[] interval = intervals.get(i);
            if (current[1] >= interval[0]) {
                // 存在重叠
                current[1] = Math.max(interval[1], current[1]);
                current[2] += interval[2];
            } else {
                current = interval;
                merged.add(current);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int[] m : merged) {
            sb.append(m[0]).append(" ").append(m[1]).append(" ").append(m[2]).append("\n");
        }
        System.out.print(sb);
    }
}
