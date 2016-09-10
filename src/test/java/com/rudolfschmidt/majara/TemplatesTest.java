package com.rudolfschmidt.majara;

import org.junit.Test;

import java.io.IOException;

public class TemplatesTest {

    private static final String TEMPLATES = "src/test/resources/templates/";

    @Test
    public void basic() throws IOException {
        Tester.renderTest(TEMPLATES);
    }

    @Test
    public void oneBlockOneLevel() throws IOException {
        Tester.renderTest(TEMPLATES);
    }

    @Test
    public void twoBlocksOneLevel() throws IOException {
        Tester.renderTest(TEMPLATES);
    }

    @Test
    public void twoBlocksTwoLevels() throws IOException {
        Tester.renderTest(TEMPLATES);
    }

    @Test
    public void firstLevelComment() throws IOException {
        Tester.renderTest(TEMPLATES);
    }

    @Test
    public void twoLevelComment() throws IOException {
        Tester.renderTest(TEMPLATES);
    }

    @Test
    public void include() throws IOException {
        Tester.renderTest(TEMPLATES);
    }

    @Test
    public void includeBlock() throws IOException {
        Tester.renderTest(TEMPLATES);
    }

    @Test
    public void append() throws IOException {
        Tester.renderTest(TEMPLATES);
    }

    @Test
    public void closing() throws IOException {
        Tester.renderTest(TEMPLATES);
    }
}
