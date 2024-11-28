package com.fanruan.p38;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Anner
 * @since 12.0
 * Created on 2024/11/28
 */
public class MainTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private final static long LIMIT = 100;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(System.out);
    }


    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test1() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq1.in", "src/test/java/com/fanruan/p38/seq1.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test2() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq2.in", "src/test/java/com/fanruan/p38/seq2.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test3() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq3.in", "src/test/java/com/fanruan/p38/seq3.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test4() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq4.in", "src/test/java/com/fanruan/p38/seq4.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test5() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq5.in", "src/test/java/com/fanruan/p38/seq5.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test6() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq6.in", "src/test/java/com/fanruan/p38/seq6.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test7() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq7.in", "src/test/java/com/fanruan/p38/seq7.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test8() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq8.in", "src/test/java/com/fanruan/p38/seq8.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test9() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq9.in", "src/test/java/com/fanruan/p38/seq9.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test10() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq10.in", "src/test/java/com/fanruan/p38/seq10.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test11() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq11.in", "src/test/java/com/fanruan/p38/seq11.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test12() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq12.in", "src/test/java/com/fanruan/p38/seq12.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test13() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq13.in", "src/test/java/com/fanruan/p38/seq13.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test14() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq14.in", "src/test/java/com/fanruan/p38/seq14.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test15() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq15.in", "src/test/java/com/fanruan/p38/seq15.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test16() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq16.in", "src/test/java/com/fanruan/p38/seq16.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test17() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq17.in", "src/test/java/com/fanruan/p38/seq17.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test18() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq18.in", "src/test/java/com/fanruan/p38/seq18.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test19() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq19.in", "src/test/java/com/fanruan/p38/seq19.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test20() throws Exception {
        testActually("src/test/java/com/fanruan/p38/seq20.in", "src/test/java/com/fanruan/p38/seq20.ans");
    }


    private void testActually(String inPath, String outPath) throws Exception {
        InputStream inputStream = new BufferedInputStream(Files.newInputStream(Paths.get(inPath)));
        ByteArrayInputStream bais = new ByteArrayInputStream(readBytes(inputStream));
        InputStream originalSystemIn = System.in;
        System.setIn(bais);

        Main.main(new String[]{});

        System.setIn(originalSystemIn);
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
    }

    private byte[] readBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }
}
