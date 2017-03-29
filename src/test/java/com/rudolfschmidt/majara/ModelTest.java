package com.rudolfschmidt.majara;

import com.rudolfschmidt.majara.model.ModelNestedValue;
import com.rudolfschmidt.majara.model.ModelValue;
import com.rudolfschmidt.majara.model.NestedObject;
import org.junit.Test;

import java.io.IOException;

public class ModelTest {

	private static final String MODEL = "src/test/resources/model/";

	@Test
	public void text() throws IOException {
		Model model = Model.createModel();
		model.add("a", "foo");
		model.add("b", "bar");
		Tester.renderModelTest(MODEL, model);
	}

	@Test
	public void object() throws IOException {
		Model model = Model.createModel();
		model.add("a", new ModelValue());
		model.add("b", new ModelNestedValue(new NestedObject()));
		Tester.renderModelTest(MODEL, model);
	}

	@Test
	public void attributes() throws IOException {
		Model model = Model.createModel();
		model.add("a", "style");
		model.add("b", "http://www.test.com");
		model.add("foo.bar", "active");
		model.add("c", new ModelValue());
		model.add("d", new ModelNestedValue(new NestedObject()));
		model.add("email", "a@a");
		model.add("e", "foo");
		model.add("f", "bar");
		Tester.renderModelTest(MODEL, model);
	}

}
