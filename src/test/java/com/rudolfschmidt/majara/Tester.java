package com.rudolfschmidt.majara;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class Tester {

	private static final String SUFFIX = "jade";

	public static void renderTest(String base) throws IOException {
		final String templateName = getTemplateName();
		final String expected = getExpected(base, templateName);
		final Majara templateEngine = new Majara(base, SUFFIX, false);
		final String actual = templateEngine.render(templateName, Model.createModel());
		assertEquals(expected, actual);
	}

	public static void renderModelTest(String base, Model model) throws IOException {
		final String templateName = getTemplateName();
		final String expected = getExpected(base, templateName);
		final Majara templateEngine = new Majara(base, SUFFIX, false);
		final String actual = templateEngine.render(templateName, model);
		assertEquals(expected, actual);
	}

	private static String getExpected(String base, String fileName) throws IOException {
		return Files.readAllLines(Paths.get(base, fileName + ".html"))
				.stream()
				.map(String::trim)
				.collect(Collectors.joining());
	}

	private static String getTemplateName() {
		final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return stackTrace[3].getMethodName();
	}
}
