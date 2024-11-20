package com.fanruan.p16;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

import static org.junit.Assert.assertEquals;

/**
 * @author Anner
 * @since 12.0
 * Created on 2024/11/20
 */
public class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
    }

    @Test
    public void test1() throws Exception {
        testActually("src/test/java/com/fanruan/p16/1.in", "src/test/java/com/fanruan/p16/1.out");
    }

    @Test
    public void test2() throws Exception {
        testActually("src/test/java/com/fanruan/p16/2.in", "src/test/java/com/fanruan/p16/2.out");
    }

    @Test
    public void test3() throws Exception {
        testActually("src/test/java/com/fanruan/p16/3.in", "src/test/java/com/fanruan/p16/3.out");
    }

    @Test
    public void test4() throws Exception {
        testActually("src/test/java/com/fanruan/p16/4.in", "src/test/java/com/fanruan/p16/4.out");
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
        try (InputStream expectedInputStream = new BufferedInputStream(Files.newInputStream(Paths.get(outPath)));
             BufferedReader reader = new BufferedReader(new InputStreamReader(expectedInputStream))) {
            expectedOutput = reader.readLine();
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
