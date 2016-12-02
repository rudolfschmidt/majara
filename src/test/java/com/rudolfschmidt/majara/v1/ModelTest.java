package com.rudolfschmidt.majara.v1;

import com.rudolfschmidt.majara.Model;
import com.rudolfschmidt.majara.Tester;
import com.rudolfschmidt.majara.model.ModelNestedValue;
import com.rudolfschmidt.majara.model.ModelValue;
import com.rudolfschmidt.majara.model.NestedObject;
import org.junit.Test;

import java.io.IOException;

public class ModelTest {

	private static final String MODEL = "src/test/resources/model/";

	@Test
	public void value() throws IOException {
		Model model = Model.get();
		model.put("a", "foo");
		model.put("b", "bar");
		model.put("c", new ModelValue());
		model.put("d", new ModelNestedValue(new NestedObject()));
		Tester.renderModelTest(MODEL, model);
	}

	@Test
	public void equalsValue() throws IOException {
		Model model = Model.get();
		model.put("a", "b");
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
