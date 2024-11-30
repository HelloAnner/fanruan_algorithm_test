package com.fanruan.p45;

import java.io.IOException;
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
    public static void main(String[] args) throws IOException {
        // 打开文件并映射到内存
        RandomAccessFile file = new RandomAccessFile("in", "r");
        FileChannel channel = file.getChannel();
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        IntBuffer intBuffer = buffer.asIntBuffer();

        // 读取前四个参数
        int totalColumns = intBuffer.get(); // 总列数
        int groupColumns = intBuffer.get(); // 汇总列数
        int rows = intBuffer.get();         // 行数
        int topN = intBuffer.get();         // 结果取前多少条记录

        // 读取二维表数据
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

        // 分组去重
        Map<List<Integer>, Set<List<Integer>>> groupMap = new HashMap<>();
        for (List<Integer> row : table) {
            // 获取分组键
            List<Integer> groupKey = row.subList(0, groupColumns);
            // 获取需要去重的值
            List<Integer> remainingValues = row.subList(groupColumns, row.size());
            // 将去重值添加到对应分组的集合中
            groupMap.computeIfAbsent(groupKey, k -> new HashSet<>()).add(remainingValues);
        }

        // 将分组结果转为列表
        List<Map.Entry<List<Integer>, Set<List<Integer>>>> entries = new ArrayList<>(groupMap.entrySet());
        // 对分组键排序（字典序）
        entries.sort(Comparator.comparing(Map.Entry::getKey, (a, b) -> {
            for (int i = 0; i < Math.min(a.size(), b.size()); i++) {
                int cmp = a.get(i).compareTo(b.get(i));
                if (cmp != 0) return cmp;
            }
            return Integer.compare(a.size(), b.size());
        }));

        // 如果需要取前 topN 条记录
        if (topN != -1 && topN < entries.size()) {
            entries = entries.subList(0, topN);
        }

        // 构建结果列表
        List<List<Integer>> resultTable = new ArrayList<>();
        for (Map.Entry<List<Integer>, Set<List<Integer>>> entry : entries) {
            List<Integer> newRow = new ArrayList<>(entry.getKey());
            newRow.add(entry.getValue().size()); // 添加去重记录数
            resultTable.add(newRow);
        }

        // 计算结果总和
        int sum = 0;
        for (List<Integer> row : resultTable) {
            for (int num : row) {
                sum += num;
            }
        }

        // 输出结果总和
        // 按照测试用例 ，这里还需要加上一下无关的列 , 测试用例存在问题，逻辑是ok 的， 比如第二个例子，这个 +4 是什么鬼?????
        // 到现在没有一个人做出来，逻辑不复杂
        System.out.println(sum);
    }
}
