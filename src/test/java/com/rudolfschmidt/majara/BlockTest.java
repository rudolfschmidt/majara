package com.rudolfschmidt.majara;

import org.junit.Test;

import java.io.IOException;

public class BlockTest {

	private static final String TEMPLATES = "src/test/resources/block/";

	@Test
	public void level1blocks1() throws IOException {
		Tester.renderTest(TEMPLATES);
	}

	@Test
	public void level1blocks2() throws IOException {
		Tester.renderTest(TEMPLATES);
	}

	@Test
	public void level2blocks2() throws IOException {
		Tester.renderTest(TEMPLATES);
	}
}
