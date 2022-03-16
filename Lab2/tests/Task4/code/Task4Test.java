package Task4.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Task4Test {

    private final Set<String> badWords = new HashSet<>(List.of("apple", "grape", "avocado", "cat"));

    @Test
    void case_string_has_no_bad_words() {
        String original = "I have to do it better";
        Assertions.assertEquals(Task4.deleteBadWords(original, badWords), original);
    }

    @Test
    void case_string_has_bad_words_in_different_case() {
        String original = "I have to eat this AppLe and then AVOcaDo";
        String expected = "I have to eat this and then";
        Assertions.assertEquals(Task4.deleteBadWords(original, badWords), expected);
    }

    @Test
    void case_file_with_bad_words_is_empty() throws IOException {
        Set<String> emptyBadWords = new HashSet<>();
        String filePath = "C:\\Users\\yana-\\All_Projects_Intellij_Idea\\OOP_Labs\\Lab2\\src\\Task4\\test\\empty_file\\input.txt";
        Assertions.assertEquals(Task4.readWordsFrom(filePath), emptyBadWords);
    }

    @Test
    void case_set_of_bad_words_is_empty() {
        Set<String> emptyBadWords = new HashSet<>();
        String original = "I have to do it better";
        Assertions.assertEquals(Task4.deleteBadWords(original, emptyBadWords), original);
    }

    @Test
    void case_file_with_bad_words_not_found_or_incorrect() {
        String filePath = "some_file.txt";
        Assertions.assertThrows(FileNotFoundException.class, () -> Task4.readWordsFrom(filePath));
    }

    @Test
    void case_string_is_empty() {
        Assertions.assertEquals(Task4.deleteBadWords("", badWords), "");
    }

    @Test
    void case_check_correct_reading_from_file() throws IOException {
        String filePath = "C:\\Users\\yana-\\All_Projects_Intellij_Idea\\OOP_Labs\\Lab2\\src\\Task4\\test\\correct\\input.txt";
        Set<String> badWords = new HashSet<>(List.of("apple", "pineapple", "grape", "milk"));
        Assertions.assertEquals(Task4.readWordsFrom(filePath), badWords);
    }

}