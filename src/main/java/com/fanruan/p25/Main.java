package com.fanruan.p25;

import java.io.File;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * issue: timeout
 */
public class Main {
    public static void main(String[] args) throws Exception {
        // 读取文件
        RandomAccessFile fs = new RandomAccessFile(new File("in"), "r");
        FileChannel fc = fs.getChannel();
        MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        IntBuffer intBuffer = buffer.asIntBuffer();
        PrintStream stream = new PrintStream(new File("out"));

        // 读取数据
        int size = intBuffer.get(); // 总记录数
        int n = intBuffer.get();    // 前 n 个车主

        // 使用 TreeMap 统计违章次数（按车牌 ID 升序）
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < size; i++) {
            int id = intBuffer.get();
            map.put(id, map.getOrDefault(id, 0) + 1);
        }

        // Step 1: 计算中位数对应的 ID
        int index = map.size() / 2;
        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            list.add(entry.getValue());
            if (--index == 0) {
                stream.println(entry.getKey());
            }
        }

        // Step 2: 计算前 n 个车辆的违章次数的平均值
        // 将违章次数转为列表并按照降序排序
        List<Integer> violationCounts = new ArrayList<>(map.values());
        violationCounts.sort(Comparator.reverseOrder()); // 按降序排序

        // 计算前 n 个的平均值
        int sum = 0;
        for (int i = 0; i < n && i < violationCounts.size(); i++) {
            sum += violationCounts.get(i);
        }
        int average = sum / n;
        stream.println(average); // 输出平均值

        // 关闭资源
        fc.close();
        stream.close();
    }
}