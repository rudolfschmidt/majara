package com.rudolfschmidt.majara.v1;

import com.rudolfschmidt.majara.Model;
import com.rudolfschmidt.majara.Tester;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class CodeTest {

    private static final String MODEL = "src/test/resources/code/";

    @Test
    public void simple() throws IOException {
        Model model = Model.get();
        model.put("a", true);
        model.put("b", false);
        model.put("d", false);
        model.put("e", true);
        Tester.renderModelTest(MODEL, model);
    }

    @Test
    public void list() throws IOException {
        Model model = Model.get();
        model.put("list", Arrays.asList("a", "b", "c"));
        Tester.renderModelTest(MODEL, model);
    }

}
