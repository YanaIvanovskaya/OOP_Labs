package Task2.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void case_only_part_of_entity() {
        String original = "&quot";
        Assertions.assertEquals(Task2.htmlDecode(original), original);
    }

    @Test
    void alexey() {
        String original = "&quot&amp;";
        Assertions.assertEquals("&quot&", Task2.htmlDecode(original));
    }

    @Test
    void case_special_symbols_in_any_positions() {
        String original = "&Cat &lt;says&gt; ;&&quot;Meow&quot;. M&amp;M&;apos;s&";
        String expected = "&Cat <says> ;&\"Meow\". M&M&;apos;s&";
        Assertions.assertEquals(Task2.htmlDecode(original), expected);
    }

    @Test
    void case_string_contains_only_entities() {
        String original = "&lt;&gt;&quot;&quot;&amp;&apos;";
        String expected = "<>\"\"&'";
        Assertions.assertEquals(Task2.htmlDecode(original), expected);
    }

    @Test
    void case_string_not_contains_entities() {
        String original = "Cat says meow-meow";
        Assertions.assertEquals(Task2.htmlDecode(original), original);
    }

    @Test
    void case_string_empty() {
        Assertions.assertEquals(Task2.htmlDecode(""), "");
    }

    @Test
    void case_string_with_one_special_symbol_1() {
        Assertions.assertEquals(Task2.htmlDecode("&"), "&");
    }

    @Test
    void case_string_with_one_special_symbol_2() {
        Assertions.assertEquals(Task2.htmlDecode(";"), ";");
    }

    @Test
    void case_string_is_null() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Task2.htmlDecode(null));
    }

}