package com.fanruan.p45;

import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 求分组之后的去重记录数. 类似sql中的 select col,discount(col2) from table group by col.
 * <p>
 * 比如有下列数据(名字，年份)
 * <p>
 * 如：
 * 张三，2018
 * 张三，2019
 * 李四，2018
 * 结果为:
 * 张三，2
 * 李四，1
 * 题目会有两种要求，一种是计算所有汇总后数据，一种只需要计算输出部分数据
 * 输入格式：
 * 输入数据为一连串数字，前四个数字为参数信息，后面数字表示一个二维表。
 * 第一个数字表示有多少列，第二个数字表示有多少汇总列，二维表前面的列为汇总列，剩下的列全部为计算去重的列。
 * 第三个数字表示有多少行 第四个数字表示结果取前多少条记录，如果为-1表示取全部记录。
 * <p>
 * 输出格式
 * 把得到的二维表数字全部加起来，输出为结果。
 *
 * <a href="https://judge.fineres.com/problem/45">#45. 去重记录数</a>
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/11/29
 */
public class Main {
    private static final Comparator<List<Integer>> listComparator = new Comparator<List<Integer>>() {
        @Override
        public int compare(List<Integer> a, List<Integer> b) {
            int size = Math.min(a.size(), b.size());
            for (int i = 0; i < size; i++) {
                int cmp = a.get(i).compareTo(b.get(i));
                if (cmp != 0) {
                    return cmp;
                }
            }
            return Integer.compare(a.size(), b.size());
        }
    };

    public static void main(String[] args) {
        try {
            RandomAccessFile fs = new RandomAccessFile("in", "r");
            FileChannel fc = fs.getChannel();
            MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            IntBuffer intBuffer = buffer.asIntBuffer();

            // 读取前四个参数
            int totalColumns = intBuffer.get();
            int groupColumns = intBuffer.get();
            int rows = intBuffer.get();
            int topN = intBuffer.get();

            // 计算数据的总长度
            int dataLength = totalColumns * rows;
            int[] data = new int[dataLength];
            for (int i = 0; i < dataLength; i++) {
                data[i] = intBuffer.get();
            }

            // 组织成二维表
            List<List<Integer>> table = new ArrayList<>();
            for (int i = 0; i < rows; i++) {
                List<Integer> row = new ArrayList<>();
                for (int j = 0; j < totalColumns; j++) {
                    row.add(data[i * totalColumns + j]);
                }
                table.add(row);
            }

            // 分组
            Map<List<Integer>, Set<List<Integer>>> groupMap = new HashMap<>();
            for (List<Integer> row : table) {
                List<Integer> groupKey = row.subList(0, groupColumns);
                List<Integer> remainingValues = row.subList(groupColumns, row.size());
                groupMap.computeIfAbsent(groupKey, k -> new HashSet<>()).add(remainingValues);
            }

            // 统计去重记录数
            List<Map.Entry<List<Integer>, Set<List<Integer>>>> entries = new ArrayList<>(groupMap.entrySet());
            // 按groupKey排序
            entries.sort(Comparator.comparing(Map.Entry::getKey, listComparator));

            // 取前topN条
            if (topN != -1 && topN < entries.size()) {
                entries = entries.subList(0, topN);
            }

            // 生成结果二维表
            List<List<Integer>> resultTable = new ArrayList<>();
            for (Map.Entry<List<Integer>, Set<List<Integer>>> entry : entries) {
                List<Integer> newRow = new ArrayList<>(entry.getKey());
                newRow.add(entry.getValue().size());
                resultTable.add(newRow);
            }

            // 把所有数字加起来
            int sum = 0;
            for (List<Integer> row : resultTable) {
                for (int num : row) {
                    sum += num;
                }
            }

            System.out.println(sum);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
