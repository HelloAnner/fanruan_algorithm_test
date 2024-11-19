package com.fanruan.p5;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.util.PriorityQueue;

/**
 * <a href="https://judge.fineres.com/problem/5">#5. 求中位数【一】</a>
 */
public class Main {

    public static void main(String[] args) {
        int[] array = readIntArray();
        System.out.println(getMedian(array));
    }

    private static int[] readIntArray() {
        int[] array = new int[10000000];
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(System.in))) {
            for (int i = 0; i < 10000000; i++) {
                array[i] = in.readInt();
            }
        } catch (Exception ignore) {
        }
        return array;
    }

    private static int getMedian(int[] array) {
        PriorityQueue<Integer> l = new PriorityQueue<>((a, b) -> b - a);
        PriorityQueue<Integer> r = new PriorityQueue<>((a, b) -> a - b);

        for (int num : array) {
            int s1 = l.size(), s2 = r.size();
            if (s1 == s2) {
                if (r.isEmpty() || num <= r.peek()) {
                    l.add(num);
                } else {
                    l.add(r.poll());
                    r.add(num);
                }
            } else {
                if (l.peek() <= num) {
                    r.add(num);
                } else {
                    r.add(l.poll());
                    l.add(num);
                }
            }
        }

        return findMedian(l, r);
    }

    private static int findMedian(PriorityQueue<Integer> l, PriorityQueue<Integer> r) {
        int s1 = l.size(), s2 = r.size();
        if (s1 == s2) {
            return (l.peek() + r.peek()) / 2;
        } else {
            return l.peek();
        }
    }
}
