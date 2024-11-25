package com.fanruan.p9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * <a href="https://judge.fineres.com/problem/9">#9. 最小生成树</a>
 * <p>
 * 需要使用快排代替自带的 sort 才可以通过
 *
 * @author Anner
 * @since 11.0
 * Created on 2024/11/21
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

        String line = reader.readLine();
        int V = readInt(line, 0);
        int E = readInt(line, 1);
        ArrayList<Edge> edges = new ArrayList<>();

        for (int e = 0; e < E; ++e) {
            line = reader.readLine();
            int src = readInt(line, 0) - 1;
            int dest = readInt(line, 1) - 1;
            int weight = readInt(line, 2);
            edges.add(new Edge(src, dest, weight));
        }

        System.out.println(kruskalMST(edges, V));
    }

    static int readInt(String line, int index) {
        int start = 0;
        while (index > 0) {
            if (line.charAt(start) == ' ') {
                index--;
            }
            start++;
        }
        int end = start;
        while (end < line.length() && line.charAt(end) != ' ') {
            end++;
        }
        return Integer.parseInt(line.substring(start, end));
    }

    static int find(Subset[] subsets, int i) {
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }

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

    static void quickSort(ArrayList<Edge> edges, int low, int high) {
        if (low < high) {
            int pi = partition(edges, low, high);
            quickSort(edges, low, pi - 1);
            quickSort(edges, pi + 1, high);
        }
    }

    static int partition(ArrayList<Edge> edges, int low, int high) {
        Edge pivot = edges.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (edges.get(j).weight < pivot.weight) {
                i++;
                Edge temp = edges.get(i);
                edges.set(i, edges.get(j));
                edges.set(j, temp);
            }
        }
        Edge temp = edges.get(i + 1);
        edges.set(i + 1, edges.get(high));
        edges.set(high, temp);
        return i + 1;
    }

    static long kruskalMST(ArrayList<Edge> edges, int V) {
        quickSort(edges, 0, edges.size() - 1);

        Subset[] subsets = new Subset[V];
        for (int v = 0; v < V; ++v) {
            subsets[v] = new Subset(v);
        }

        long minimumCost = 0;
        int edgesUsed = 0;

        for (Edge edge : edges) {
            int x = find(subsets, edge.src);
            int y = find(subsets, edge.dest);

            if (x != y) {
                union(subsets, x, y);
                minimumCost += edge.weight;
                edgesUsed++;
                if (edgesUsed == V - 1) break;
            }
        }

        return minimumCost;
    }
}