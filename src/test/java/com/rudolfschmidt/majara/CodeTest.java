package com.rudolfschmidt.majara;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class CodeTest {

    private static final String MODEL = "src/test/resources/code/";

    @Test
    public void simple() throws IOException {
        Model model = Model.newInstance();
        model.put("a", true);
        model.put("b", false);
        model.put("d", false);
        model.put("e", true);
        Tester.renderModelTest(MODEL, model);
    }

    @Test
    public void list() throws IOException {
        Model model = Model.newInstance();
        model.put("list", Arrays.asList("a", "b", "c"));
        Tester.renderModelTest(MODEL, model);
    }

}
