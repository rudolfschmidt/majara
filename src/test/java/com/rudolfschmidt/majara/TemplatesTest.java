package com.rudolfschmidt.majara;

import org.junit.Test;

import java.io.IOException;

public class TemplatesTest {

	private static final String TEMPLATES = "src/test/resources/templates/";

	@Test
	public void simple() throws IOException {
		Tester.renderTest(TEMPLATES);
	}

	@Test
	public void empty() throws IOException {
		Tester.renderTest(TEMPLATES);
	}

	@Test
	public void pipe() throws IOException {
		Tester.renderTest(TEMPLATES);
	}

	@Test
	public void attributes() throws IOException {
		Tester.renderTest(TEMPLATES);
	}

	@Test
	public void basic() throws IOException {
		Tester.renderTest(TEMPLATES);
	}

	@Test
	public void comments() throws IOException {
		Tester.renderTest(TEMPLATES);
	}
}
