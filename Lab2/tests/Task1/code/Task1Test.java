package Task1.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Vector;

class Task1Test {

    @Test
    void case_empty_arguments_1() {
        String[] args = {};
        Assertions.assertNull(Task1.getDoubleVectorOrNull(args));
    }

    @Test
    void case_empty_arguments_2() {
        String[] args = {""};
        Assertions.assertNull(Task1.getDoubleVectorOrNull(args));
    }

    @Test
    void case_incorrect_doubles() {
        String[] args = {"1.4", "-2F", "7e"};
        Assertions.assertNull(Task1.getDoubleVectorOrNull(args));
    }

    @Test
    void case_correct_doubles() {
        String[] args = {"1.4", "-34", "88889"};
        Vector<Double> expected = new Vector<>(List.of(1.4, -34.0, 88889.0));
        Assertions.assertEquals(Task1.getDoubleVectorOrNull(args), expected);
    }

    @Test
    void case_transform() {
        Vector<Double> doubles = new Vector<>(List.of(2.5, 7.0, 9.5, -0.3));
        Vector<Double> expected = new Vector<>(List.of(5.0, -12.0, 19.0, -19.3));
        Assertions.assertEquals(Task1.transformAs8Variant(doubles), expected);
    }
}