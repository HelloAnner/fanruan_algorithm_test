package com.fanruan.p13;

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
 * Created on 2024/11/29
 */
public class MainTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private final static long LIMIT = 500;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        Main.maxn = 1010;
        Main.n = 0;
        Main.m = 0;
        Main.k = 0;
        Main.head = new Node[Main.maxn];
        Main.num = 0;
        Main.fa = new int[Main.maxn][21];
        Main.dis = new int[Main.maxn][21];
        Main.dep = new int[Main.maxn];
        Main.f = new int[Main.maxn];
        Main.vis = new boolean[Main.maxn];
        Main.E = new Edge[100010];
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(System.out);
    }


    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test1() throws Exception {
        testActually("src/test/java/com/fanruan/p13/package1.in", "src/test/java/com/fanruan/p13/package1.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test2() throws Exception {
        testActually("src/test/java/com/fanruan/p13/package2.in", "src/test/java/com/fanruan/p13/package2.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test3() throws Exception {
        testActually("src/test/java/com/fanruan/p13/package3.in", "src/test/java/com/fanruan/p13/package3.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test4() throws Exception {
        testActually("src/test/java/com/fanruan/p13/package4.in", "src/test/java/com/fanruan/p13/package4.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test5() throws Exception {
        testActually("src/test/java/com/fanruan/p13/package5.in", "src/test/java/com/fanruan/p13/package5.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test6() throws Exception {
        testActually("src/test/java/com/fanruan/p13/package6.in", "src/test/java/com/fanruan/p13/package6.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test7() throws Exception {
        testActually("src/test/java/com/fanruan/p13/package7.in", "src/test/java/com/fanruan/p13/package7.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test8() throws Exception {
        testActually("src/test/java/com/fanruan/p13/package8.in", "src/test/java/com/fanruan/p13/package8.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test9() throws Exception {
        testActually("src/test/java/com/fanruan/p13/package9.in", "src/test/java/com/fanruan/p13/package9.ans");
    }

    @Test
    @Timeout(value = LIMIT, unit = TimeUnit.MILLISECONDS)
    public void test10() throws Exception {
        testActually("src/test/java/com/fanruan/p13/package10.in", "src/test/java/com/fanruan/p13/package10.ans");
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
                outputBuilder.append(line).append("\n");
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
