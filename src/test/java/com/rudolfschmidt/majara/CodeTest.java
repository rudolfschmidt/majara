package com.rudolfschmidt.majara;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class CodeTest {

	private static final String MODEL = "src/test/resources/code/";

	@Test
	public void conditionBoolean() throws IOException {
		Model model = Model.createModel();
		model.add("a", true);
		model.add("b", false);
		model.add("d", false);
		model.add("e", true);
		Tester.renderModelTest(MODEL, model);
	}

	@Test
	public void conditionString() throws IOException {
		Model model = Model.createModel();
		model.add("a", "a");
		model.add("b", "");
		model.add("c", null);
		model.add("e", "a");
		model.add("f", "");
		model.add("g", null);
		Tester.renderModelTest(MODEL, model);
	}

	@Test
	public void list() throws IOException {
		Model model = Model.createModel();
		model.add("list", Arrays.asList("a", "b", "c"));
		Tester.renderModelTest(MODEL, model);
	}

}
