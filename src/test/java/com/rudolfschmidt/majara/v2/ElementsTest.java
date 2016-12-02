package com.rudolfschmidt.majara.v2;

import com.rudolfschmidt.majara.Tester;
import org.junit.Test;

import java.io.IOException;

public class ElementsTest {

	private static final String TEMPLATES = "src/test/resources/v2/elements/";

	@Test
	public void names() throws IOException {
		Tester.renderTest(TEMPLATES);
	}

	@Test
	public void values() throws IOException {
		Tester.renderTest(TEMPLATES);
	}

	@Test
	public void attributes() throws IOException {
		Tester.renderTest(TEMPLATES);
	}

	@Test
	public void doctype() throws IOException {
		Tester.renderTest(TEMPLATES);
	}

	@Test
	public void voids() throws IOException {
		Tester.renderTest(TEMPLATES);
	}
}
