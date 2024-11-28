package com.fanruan.p46;

import java.util.Scanner;

/**
 * 小王和小马住在一个由 N 个路口和 M 条单向小道构成的森林中。小马喜欢去小王家玩，但小王不喜欢小马的鲁莽。每当小王发现小马走了一条与之前完全相同的路径来到她家时，她就会装作不在家。小马发现了这个秘密，因此她决定每次走不同的路径到小王家。
 * <p>
 * 小马体力有限，她不想走超过 K 条边到达小王家，并且，每当她走 t 条边到达小王家时，她需要付出 t^2 的体力。小马想知道，在小王无论如何都不想接见她之前（即小马无法走一条长度不超过 K 的与之前不同的路径），她会消耗多少体力。
 * <p>
 * 小马拿到了一张森林的地图，但她不知道自己的家和小王的家在哪一个路口，因此她会询问你 Q 次，每次询问假如小马的家的位置在 S 面小王的家的位置在 T 时的答案。一条路径的定义是指一个长度为 P 的边的序列，其中对于任意 1 ≤ i < P，有 A_{m_i-1} 的结束节点是 A_m_i 的起始节点。
 * <p>
 * 两条路径完全相同是指两条路径的长度均为 T 并且对任意 0 ≤ i < P，有 A_{mi} = B_{mi}。
 * <p>
 * 输入格式
 * 输入数据第一行包含四个整数 N, M, K, Q，含义如题所述。
 * 接下来 M 行，每行两个整数 X, Y，表示从第 X 个路口到第 Y 个路口有一条单向小道。路口的标号为 1, 2, 3, ..., N，在输入数据第 i + 1 行的边的标号为 i_0。
 * 接下来 Q 行，每行两个整数 S 和 T，含义如题所述。
 * <p>
 * 输出格式
 * 对于每个询问，输出一行，表示这次询问的答案。由于小马接受不了非常大的数，你只需要输出答案模 1000000007 (10^9 + 7) 的值。
 * <p>
 * 数据范围与提示
 * 对于100%的数据，2≤N≤100，0≤M≤10^6，0≤K≤10^9，0≤Q≤10^5，1≤X,Y,S,T≤N。
 * <p>
 * <a href="https://judge.fineres.com/problem/46">#46. 找朋友</a>
 * <p>
 * <a href="https://www.luogu.com.cn/problem/P4102">P4102 [HEOI2014] 林中路径</a>
 * <p>
 * 矩阵优化优化递推
 * <p>
 * issue: wrong ?
 *
 * @author Anner
 * @since 12.0
 * Created on 2024/11/28
 */
public class Main {
    static int N = 100;
    static int P = (int) (1e9 + 7);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int K = sc.nextInt();
        int q = sc.nextInt();

        Matrix A = new Matrix(n);
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;
            A.g[a][b]++;
        }

        Matrix F0 = new Matrix(n);
        Matrix F1 = new Matrix(n);
        Matrix F2 = new Matrix(n);
        Matrix s = new Matrix(n);

        calc(K, A, F0, F1, F2, s, n);

        for (int i = 0; i < q; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;
            System.out.println(F2.g[a][b]);
        }
    }

    public static void calc(int k, Matrix A, Matrix F0, Matrix F1, Matrix F2, Matrix s, int n) {
        if (k == 1) {
            F0.copy(A);
            F1.copy(A);
            F2.copy(A);
            s.copy(A);
        } else if (k % 2 == 1) {
            calc(k - 1, A, F0, F1, F2, s, n);
            Matrix temp1 = F2.add(F1.multiplyScalar(2).add(F0));
            Matrix temp2 = A.multiply(temp1);
            F2 = A.add(temp2);
            Matrix temp3 = F1.add(F0);
            Matrix temp4 = A.multiply(temp3);
            F1 = A.add(temp4);
            Matrix temp5 = A.multiply(F0);
            F0 = A.add(temp5);
            s = s.multiply(A);
        } else {
            calc(k / 2, A, F0, F1, F2, s, n);
            Matrix temp6 = F2.add(F1.multiplyScalar(k).add(F0.multiplyScalar((int) ((long) k * k / 4 % P))));
            Matrix temp7 = s.multiply(temp6);
            F2 = F2.add(temp7);
            Matrix temp8 = F1.add(F0.multiplyScalar(k / 2));
            Matrix temp9 = s.multiply(temp8);
            F1 = F1.add(temp9);
            Matrix temp10 = s.multiply(F0);
            F0 = F0.add(temp10);
            s = s.multiply(s);
        }
    }
}

class Matrix {
    int[][] g;

    public Matrix(int size) {
        g = new int[size][size];
    }

    public void copy(Matrix other) {
        for (int i = 0; i < g.length; i++) {
            System.arraycopy(other.g[i], 0, g[i], 0, g[i].length);
        }
    }

    public Matrix add(Matrix t) {
        int n = g.length;
        Matrix res = new Matrix(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res.g[i][j] = (g[i][j] + t.g[i][j]) % Main.P;
            }
        }
        return res;
    }

    public Matrix multiply(Matrix t) {
        int n = g.length;
        Matrix res = new Matrix(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                long sum = 0;
                for (int k = 0; k < n; k++) {
                    sum = (sum + (long) g[i][k] * t.g[k][j]) % Main.P;
                }
                res.g[i][j] = (int) sum;
            }
        }
        return res;
    }

    public Matrix multiplyScalar(int t) {
        int n = g.length;
        Matrix res = new Matrix(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res.g[i][j] = (int) (((long) g[i][j] * t) % Main.P);
            }
        }
        return res;
    }
}