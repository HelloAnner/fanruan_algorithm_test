package com.fanruan.p45;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Anner
 * @since 12.0
 * Created on 2024/11/29
 */
public class MainTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    // 看main中的文件名称
    private final static String FILE_NAME = "in";

    private final static long LIMIT = 500;


    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(System.out);
    }


    @Test
    public void test1() throws Exception {
        testActually("src/test/java/com/fanruan/p45/1.in", "src/test/java/com/fanruan/p45/1.out");
    }

    @Test
    public void test2() throws Exception {
        testActually("src/test/java/com/fanruan/p45/2.in", "src/test/java/com/fanruan/p45/2.out");
    }

    @Test
    public void test3() throws Exception {
        testActually("src/test/java/com/fanruan/p45/3.in", "src/test/java/com/fanruan/p45/3.out");
    }


    private void testActually(String inPath, String outPath) throws Exception {
        Path sourcePath = Paths.get(inPath);
        Path targetPath = Paths.get(FILE_NAME);
        Files.copy(sourcePath, targetPath);
        long startTime = System.nanoTime();
        Main.main(new String[]{});
        Files.delete(targetPath);
        long endTime = System.nanoTime();

        String actualOutput = outContent.toString();
        String expectedOutput;
        try (BufferedInputStream expectedInputStream = new BufferedInputStream(Files.newInputStream(Paths.get(outPath)));
             BufferedReader reader = new BufferedReader(new InputStreamReader(expectedInputStream))) {

            StringBuilder outputBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                outputBuilder.append(line).append("\n"); // 追加每一行并添加换行符
            }
            expectedOutput = outputBuilder.toString().trim();
        }

        assertEquals(expectedOutput, actualOutput.trim());
        assertTrue((endTime - startTime) / 1_000_000 < LIMIT);
    }
}
