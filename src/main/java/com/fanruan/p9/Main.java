package com.fanruan.p9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Anner
 * @since 11.0
 * Created on 2024/11/21
 *
 * <a href="https://judge.fineres.com/problem/9">#9. 最小生成树</a>
 */
public class Main {
    static class Edge implements Comparable<Edge> {
        int src, dest, weight;

        public Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    static class Subset {
        int parent, rank;

        public Subset(int parent) {
            this.parent = parent;
            this.rank = 0;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokens;

        String line = reader.readLine();
        tokens = new StringTokenizer(line);
        int V = Integer.parseInt(tokens.nextToken());
        int E = Integer.parseInt(tokens.nextToken());
        Edge[] edges = new Edge[E];

        for (int e = 0; e < E; ++e) {
            line = reader.readLine();
            tokens = new StringTokenizer(line);
            int src = Integer.parseInt(tokens.nextToken()) - 1; // 适应从 0 开始的索引
            int dest = Integer.parseInt(tokens.nextToken()) - 1; // 适应从 0 开始的索引
            int weight = Integer.parseInt(tokens.nextToken());
            edges[e] = new Edge(src, dest, weight);
        }

        System.out.println(kruskalMST(edges, V, E));
    }

    // 使用路径压缩和按秩合并的并查集查找
    static int find(Subset[] subsets, int i) {
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }

    // 按秩合并
    static void union(Subset[] subsets, int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank) {
            subsets[xroot].parent = yroot;
        } else if (subsets[xroot].rank > subsets[yroot].rank) {
            subsets[yroot].parent = xroot;
        } else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    // Kruskal 最小生成树算法
    static long kruskalMST(Edge[] edges, int V, int E) {
        // 首先对所有边按权重进行排序
        Arrays.sort(edges);

        // 创建 V 个子集，初始时每个点都是自己的父节点
        Subset[] subsets = new Subset[V];
        for (int v = 0; v < V; ++v) {
            subsets[v] = new Subset(v);
        }

        long minimumCost = 0; // 最终的最小生成树的总权重
        int edgesUsed = 0;    // 已经加入生成树的边数

        // 处理每一条边
        for (int i = 0; i < E && edgesUsed < V - 1; ++i) {
            Edge edge = edges[i];

            // 查找当前边的两个端点是否属于不同的集合
            int x = find(subsets, edge.src);
            int y = find(subsets, edge.dest);

            // 如果端点不属于一个集合，则将该边加入最小生成树
            if (x != y) {
                union(subsets, x, y);
                minimumCost += edge.weight;
                edgesUsed++;
            }
        }

        return minimumCost;
    }
}
