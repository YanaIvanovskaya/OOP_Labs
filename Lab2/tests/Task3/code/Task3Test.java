package Task3.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class Task3Test {

    @Test
    void case_string_with_one_word() {
        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("qwerty", 1);
        Assertions.assertEquals(Task3.getWordFrequencyMap("qwerty"), expected);
    }

    @Test
    void case_string_with_one_symbol() {
        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("a", 1);
        Assertions.assertEquals(Task3.getWordFrequencyMap("a"), expected);
    }

    @Test
    void case_string_empty() {
        HashMap<String, Integer> expected = new HashMap<>();
        Assertions.assertEquals(Task3.getWordFrequencyMap(""), expected);
    }

    @Test
    void case_string_contains_only_whitespaces() {
        HashMap<String, Integer> expected = new HashMap<>();
        Assertions.assertEquals(Task3.getWordFrequencyMap("    "), expected);
    }

    @Test
    void test_correct_count() {
        String original = "cat ice-cream cat bubble pink ice-cream bubble";
        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("cat", 2);
        expected.put("ice-cream", 2);
        expected.put("bubble", 2);
        expected.put("pink", 1);
        Assertions.assertEquals(Task3.getWordFrequencyMap(original), expected);
    }

}