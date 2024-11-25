package com.fanruan.p12;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 某实验室正在研发一种能处理大规模数据的新型超级计算机。这台超算的内存包含 2^k 个存储单元，依次编号为 0 至 2^k-1。用内存段 [L,R] 表示编号 ≥L 且 ≤R 的所有存储单元，该内存段的长度为 R-L+1。
 * 定义：如果内存段 [L,R] 的长度是 2 的整数次幣（不妨假设是 2^i），且 L 是 2^i 的整数倍，那么这个内存段是「正确的内存段」。
 * 例如，若 k=3，则正确的内存段为 [0,7],[0,3],[4,7],[0,1],[2,3],[4,5],[6,7],[0,0],[1,1],[2,2],[3,3],[4,4],[5,5],[6,6] 和 [7,7]。
 * 现在，每个存储单元所存储的值均为 0。你需要给每个存储单元赋值。简单起见，我们用游程编码的形式给出每个单元上的值。开头的 c1 个单元中存储的值为 v1，接下来 c2 个单元中存储的值为 v2，以此类推，最后的 cn 个单元中存储的值为 cn，1≤vi≤m。
 * 举个例子，如果 k = 3, n = 3, m = 2, c = {1,2,5}, v = {1,2,1}，那么内存将被赋值为 [1,2,2,1,1,1,1,1]。
 * 你只有一种方法给单元赋值：STORE([l,r],x)。该函数表示将内存段 [l,r] 中所有单元全部赋值为 x。注意，[l,r] 必须是合法的内存段。
 * 试求至少需要多少次操作才能达成要求。
 * 输入格式：
 * 第一行包含两个整数 k 和 n，分别代表内存单元的数量和赋值操作的序列数量。
 * 接下来 n 行，每行包含两个整数 ci 和 vi，代表连续 ci 个存储单元被赋值为 vi。
 * 输出格式：
 * 输出一个整数，表示至少需要多少次操作才能达成要求。
 * 数据范围：
 * 1 ≤ k ≤ 20，表示内存单元的数量是 2^k。
 * 1 ≤ n ≤ 10^5，表示赋值操作的序列数量。
 * 1 ≤ ci ≤ 2^k - 1，表示连续赋值的存储单元数量。
 * 1 ≤ vi ≤ m，其中 m 是一个正整数，表示赋值操作的值。
 * <p>
 * <a href="https://judge.fineres.com/problem/12">#12. 大数据处理</a>
 * <p>
 * 贪心
 * <p>
 * issue : Special Judge returned an unrecoginzed score: .
 *
 * @author Anner
 * @since 11.0
 * Created on 2024/11/25
 */

public class Main {
    static int K, n, m, full;
    static Node rt = null;

    static class Node {
        Node lch, rch;
        int t, ans;
        Set<Integer> s;

        Node() {
            lch = rch = null;
            t = -1;
            ans = 0;
            s = new HashSet<>();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        K = sc.nextInt();
        n = sc.nextInt();
        m = sc.nextInt();
        full = (1 << K) - 1;

        for (int i = 1, l = 0; i <= n; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            rt = update(rt, 0, full, l, l + x - 1, y);
            l += x;
        }

        if (rt != null) {
            solve(rt);
            System.out.println(rt.ans);
        } else {
            System.out.println(0);
        }
    }

    static Node update(Node pos, int l, int r, int ll, int rr, int col) {
        if (pos == null) {
            pos = new Node();
        }
        if (ll <= l && r <= rr) {
            pos.t = col;
            return pos;
        }
        int mid = (l + r) / 2;
        if (ll <= mid) pos.lch = update(pos.lch, l, mid, ll, rr, col);
        if (mid < rr) pos.rch = update(pos.rch, mid + 1, r, ll, rr, col);
        return pos;
    }

    static void solve(Node pos) {
        if (pos.lch == null) {
            pos.s.add(pos.t);
            pos.ans = 1;
            return;
        }
        solve(pos.lch);
        solve(pos.rch);
        Set<Integer> intersection = new HashSet<>(pos.lch.s);
        intersection.retainAll(pos.rch.s);
        if (!intersection.isEmpty()) {
            pos.ans = pos.lch.ans + pos.rch.ans - 1;
            pos.s = intersection;
        } else {
            pos.ans = pos.lch.ans + pos.rch.ans;
            pos.s = new HashSet<>(pos.lch.s);
            pos.s.addAll(pos.rch.s);
        }
    }
}