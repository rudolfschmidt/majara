package com.rudolfschmidt.majara.v2;

import com.rudolfschmidt.majara.Model;
import com.rudolfschmidt.majara.Tester;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class CodeTest {

	private static final String MODEL = "src/test/resources/v2/code/";

	@Test
	public void conditionBoolean() throws IOException {
		Model model = Model.get();
		model.put("a", true);
		model.put("b", false);
		model.put("d", false);
		model.put("e", true);
		Tester.renderModelTest(MODEL, model);
	}

	@Test
	public void conditionString() throws IOException {
		Model model = Model.get();
		model.put("a", "a");
		model.put("b", "");
		model.put("c", null);
		model.put("e", "a");
		model.put("f", "");
		model.put("g", null);
		Tester.renderModelTest(MODEL, model);
	}

	@Test
	public void list() throws IOException {
		Model model = Model.get();
		model.put("list", Arrays.asList("a", "b", "c"));
		Tester.renderModelTest(MODEL, model);
	}

}
