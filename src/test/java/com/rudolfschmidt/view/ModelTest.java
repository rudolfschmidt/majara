package com.rudolfschmidt.view;

import com.mappointer.view.model.ModelNestedValue;
import com.mappointer.view.model.ModelValue;
import com.mappointer.view.model.NestedObject;
import org.junit.Test;

import java.io.IOException;

public class ModelTest {

    private static final String MODEL = "src/test/resources/model/";

    @Test
    public void value() throws IOException {
        Model model = Model.newInstance();
        model.put("a", "foo");
        model.put("b", "bar");
        Tester.renderModelTest(MODEL, model);
    }

    @Test
    public void equalsValue() throws IOException {
        Model model = Model.newInstance();
        model.put("a", "b");
        Tester.renderModelTest(MODEL, model);
    }

    @Test
    public void attributes() throws IOException {
        Model model = Model.newInstance();
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
