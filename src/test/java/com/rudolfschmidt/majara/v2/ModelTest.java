package com.rudolfschmidt.majara.v2;

import com.rudolfschmidt.majara.Model;
import com.rudolfschmidt.majara.Tester;
import com.rudolfschmidt.majara.model.ModelNestedValue;
import com.rudolfschmidt.majara.model.ModelValue;
import com.rudolfschmidt.majara.model.NestedObject;
import org.junit.Test;

import java.io.IOException;

public class ModelTest {

	private static final String MODEL = "src/test/resources/v2/model/";

	@Test
	public void text() throws IOException {
		Model model = Model.get();
		model.put("a", "foo");
		model.put("b", "bar");
		Tester.renderModelTest(MODEL, model);
	}

	@Test
	public void object() throws IOException {
		Model model = Model.get();
		model.put("a", new ModelValue());
		model.put("b", new ModelNestedValue(new NestedObject()));
		Tester.renderModelTest(MODEL, model);
	}

	@Test
	public void attributes() throws IOException {
		Model model = Model.get();
		model.put("a", "style");
		model.put("b", "http://www.test.com");
		model.put("foo.bar", "active");
		model.put("c", new ModelValue());
		model.put("d", new ModelNestedValue(new NestedObject()));
		model.put("email", "a@a");
		model.put("e", "foo");
		model.put("f", "bar");
		Tester.renderModelTest(MODEL, model);
	}

}
