package com.fanruan.p57;


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
 * Created on 2024/11/21
 */
public class MainTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    // 电脑如果性能比较好，建议直接 / 2
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
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test1() throws Exception {
        testActually("src/test/java/com/fanruan/p57/crack1.in", "src/test/java/com/fanruan/p57/crack1.out");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test2() throws Exception {
        testActually("src/test/java/com/fanruan/p57/crack2.in", "src/test/java/com/fanruan/p57/crack2.out");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test3() throws Exception {
        testActually("src/test/java/com/fanruan/p57/crack3.in", "src/test/java/com/fanruan/p57/crack3.out");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test4() throws Exception {
        testActually("src/test/java/com/fanruan/p57/crack4.in", "src/test/java/com/fanruan/p57/crack4.out");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test5() throws Exception {
        testActually("src/test/java/com/fanruan/p57/crack5.in", "src/test/java/com/fanruan/p57/crack5.out");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test6() throws Exception {
        testActually("src/test/java/com/fanruan/p57/crack6.in", "src/test/java/com/fanruan/p57/crack6.out");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test7() throws Exception {
        testActually("src/test/java/com/fanruan/p57/crack7.in", "src/test/java/com/fanruan/p57/crack7.out");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test8() throws Exception {
        testActually("src/test/java/com/fanruan/p57/crack8.in", "src/test/java/com/fanruan/p57/crack8.out");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test9() throws Exception {
        testActually("src/test/java/com/fanruan/p57/crack9.in", "src/test/java/com/fanruan/p57/crack9.out");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test10() throws Exception {
        testActually("src/test/java/com/fanruan/p57/crack10.in", "src/test/java/com/fanruan/p57/crack10.out");
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