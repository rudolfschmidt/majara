package com.rudolfschmidt.majara.v2;

import com.rudolfschmidt.majara.Tester;
import org.junit.Test;

import java.io.IOException;

public class IncluderTest {

	private static final String TEMPLATES = "src/test/resources/v2/include/";

	@Test
	public void simple() throws IOException {
		Tester.renderTest(TEMPLATES);
	}

	@Test
	public void deep() throws IOException {
		Tester.renderTest(TEMPLATES);
	}
}
