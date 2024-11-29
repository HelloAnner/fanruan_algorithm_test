package com.fanruan.p13;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 给定一个包含n个节点和m条边的图，每条边有一个权值。
 * 你的任务是回答k个询问，每个询问包含两个正整数s和t表示起点和终点，要求寻找从s到t的一条路径，使得路径上权值最大的一条边权值最小。
 * 输入格式
 * 第一行包含三个整数n、m、k，分别表示n个节点，m条路径，k个询问。
 * 接下来m行，每行三个整数u，v，w，表示一个由u到v的长度为w的双向边。
 * 再接下来k行，每行两个整数s，t，表示询问从s连接到t的所有路径中单边长度最大值的最小值。
 * <p>
 * 输出格式
 * 输出包含k行，每一行包含一个整数p。p表示s连接到t的所有路径中单边长度最大值的最小值。另外，如果s到t没有路径相连通，输出-1即可。
 * 数据范围与提示
 * 对于30%的数据，n≤100, m≤1000, k≤100, w≤1000。
 * 对于70%的数据，n≤1000, m≤10000, k≤1000, w≤100000。
 * 对于100%的数据，n≤1000, m≤100000, k≤1000, w≤10000000。
 * 本题可能会有重边。
 * 为了避免SpecialJudge，本题所有的w均不相同。
 * <p>
 * <a href="https://judge.fineres.com/problem/13">#13. 最小瓶颈路</a>
 * <p>
 * <a href="https://www.cnblogs.com/thmyl/p/8835064.html">loj#P136. 最小瓶颈路</a>
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/11/29
 */
class Edge implements Comparable<Edge> {
    int u, v, w;

    public Edge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    @Override
    public int compareTo(Edge other) {
        return this.w - other.w;
    }
}

class Node {
    int to, v;
    Node next;

    public Node(int to, int v) {
        this.to = to;
        this.v = v;
        this.next = null;
    }
}

public class Main {
    public static int maxn = 1010;
    public static int n, m, k;
    public static Node[] head = new Node[maxn];
    public static int num = 0;
    public static int[][] fa = new int[maxn][21];
    public static int[][] dis = new int[maxn][21];
    public static int[] dep = new int[maxn];
    public static int[] f = new int[maxn];
    public static boolean[] vis = new boolean[maxn];
    public static Edge[] E = new Edge[100010];

    static void Insert(int from, int to, int v) {
        Node node = new Node(to, v);
        node.next = head[from];
        head[from] = node;
    }

    static int find(int x) {
        if (x == f[x]) return x;
        return f[x] = find(f[x]);
    }

    static void dfs(int x, int father) {
        fa[x][0] = father;
        dep[x] = dep[father] + 1;
        vis[x] = true;
        for (Node node = head[x]; node != null; node = node.next) {
            int to = node.to;
            if (to == father) continue;
            dis[to][0] = node.v;
            dfs(to, x);
        }
    }

    static int lca(int x, int y) {
        if (dep[x] < dep[y]) {
            int temp = x;
            x = y;
            y = temp;
        }
        int delta = dep[x] - dep[y];
        int res = 0;
        for (int i = 0; i <= 18; i++) {
            if ((delta & (1 << i)) != 0) {
                res = Math.max(res, dis[x][i]);
                x = fa[x][i];
            }
        }
        for (int i = 18; i >= 0; i--) {
            if (fa[x][i] != fa[y][i]) {
                res = Math.max(res, dis[x][i]);
                res = Math.max(res, dis[y][i]);
                x = fa[x][i];
                y = fa[y][i];
            }
        }
        if (x == y) return res;
        res = Math.max(res, dis[x][0]);
        res = Math.max(res, dis[y][0]);
        return res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        k = sc.nextInt();

        for (int i = 1; i <= n; i++) f[i] = i;
        for (int i = 1; i <= m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            E[i] = new Edge(u, v, w);
        }

        Arrays.sort(E, 1, m + 1);

        for (int i = 1; i <= m; i++) {
            int f1 = find(E[i].u), f2 = find(E[i].v);
            if (f1 != f2) {
                f[f1] = f2;
                Insert(E[i].u, E[i].v, E[i].w);
                Insert(E[i].v, E[i].u, E[i].w);
            }
        }

        for (int i = 1; i <= n; i++) {
            if (!vis[i]) dfs(i, 0);
        }

        for (int j = 1; j <= 20; j++) {
            for (int i = 1; i <= n; i++) {
                fa[i][j] = fa[fa[i][j - 1]][j - 1];
                dis[i][j] = Math.max(dis[i][j - 1], dis[fa[i][j - 1]][j - 1]);
            }
        }

        while (k-- > 0) {
            int s = sc.nextInt();
            int t = sc.nextInt();
            if (find(s) != find(t)) {
                System.out.println("-1");
                continue;
            }
            System.out.println(lca(s, t));
        }
    }
}