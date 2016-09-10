package com.rudolfschmidt.majara;

import org.junit.Test;

import java.io.IOException;

public class SnippetsTest {


    private static final String SNIPPETS = "src/test/resources/snippets/";

    @Test
    public void attributes() throws IOException {
        Tester.renderTest(SNIPPETS);
    }

    @Test
    public void classes() throws IOException {
        Tester.renderTest(SNIPPETS);
    }

    @Test
    public void elements() throws IOException {
        Tester.renderTest(SNIPPETS);
    }

    @Test
    public void list() throws IOException {
        Tester.renderTest(SNIPPETS);
    }

    @Test
    public void piped() throws IOException {
        Tester.renderTest(SNIPPETS);
    }

    @Test
    public void voids() throws IOException {
        Tester.renderTest(SNIPPETS);
    }
}
