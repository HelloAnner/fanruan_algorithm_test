package com.fanruan.p9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
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
        ArrayList<Edge> edges = new ArrayList<>();

        for (int e = 0; e < E; ++e) {
            line = reader.readLine();
            tokens = new StringTokenizer(line);
            int src = Integer.parseInt(tokens.nextToken()) - 1;
            int dest = Integer.parseInt(tokens.nextToken()) - 1;
            int weight = Integer.parseInt(tokens.nextToken());
            edges.add(new Edge(src, dest, weight));
        }

        System.out.println(kruskalMST(edges, V));
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

    static long kruskalMST(ArrayList<Edge> edges, int V) {
        Collections.sort(edges);

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
