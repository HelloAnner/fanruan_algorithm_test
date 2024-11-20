package com.fanruan.p16;


import java.util.Scanner;

/**
 * @author Anner
 * @since 12.0
 * Created on 2024/11/20
 * <p>
 * 给定一个输入的字符串，只允许出现 字母、汉字、数字和"."、"_"、”()“、"[]"、"{}"、“-” 其他特殊字符全部用"__"来替换
 * <a href="https://judge.fineres.com/problem/16">#16. 根据要求过滤输入的字符串</a>
 */
public class Main {
    public static void main(String[] args) {
        System.setProperty("file.encoding", "utf-8");
        Scanner scanner = new Scanner(System.in);
        String in = scanner.nextLine();
        String sanitized = sanitizeString(in);
        System.out.println(sanitized);
        scanner.close();
    }

    private static String sanitizeString(String input) {
        return input.replaceAll("[^a-zA-Z0-9\\u4e00-\\u9fa5.\\_()\\[\\]\\{\\}-]", "__");
    }
}
