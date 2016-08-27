package com.rudolfschmidt.view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class Tester {

    private static final String SUFFIX = "jade";

    public static void renderTest(String base) throws IOException {
        String templateName = getTemplateName();
        String expected = getExpected(base, templateName);
        TemplateEngine templateTemplateEngine = new TemplateEngine(base, SUFFIX, false);
        String actual = templateTemplateEngine.render(templateName, Model.newInstance());

        assertEquals(expected, actual);
    }

    public static void renderModelTest(String base, Model model) throws IOException {
        String templateName = getTemplateName();
        String expected = getExpected(base, templateName);
        TemplateEngine templateTemplateEngine = new TemplateEngine(base, SUFFIX, false);
        String actual = templateTemplateEngine.render(templateName, model);

        assertEquals(expected, actual);
    }

    private static String getExpected(String base, String fileName) throws IOException {
        return Files.readAllLines(Paths.get(base, fileName + ".html"))
                .stream()
                .map(line -> line.trim())
                .collect(Collectors.joining());
    }

    private static String getTemplateName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return stackTrace[3].getMethodName();
    }
}
