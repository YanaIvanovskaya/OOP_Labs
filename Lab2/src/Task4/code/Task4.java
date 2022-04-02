package Task4.code;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

// Task 4 - Вариант 3 – Фильтр нецензурных слов – 60 баллов
public class Task4 {

    public static void main(String[] args) {
        String dictFilePath = args.length == 1 ? args[0] : null;

        if (dictFilePath == null) {
            System.out.println("1 argument expected: <dictionary file path>");
            return;
        }

        try {
            Set<String> words = readWordsFrom(dictFilePath);
            filterInput(words);
        } catch (IOException ex) {
            System.out.println("File not found or incorrect");
        }
    }

    private static void filterInput(Set<String> words) {
        System.out.println("Please, enter string to filter or empty line to cancel:");

        boolean isInterrupted = false;
        try (Scanner userInput = new Scanner(System.in)) {
            while (!isInterrupted) {
                String input = userInput.nextLine();
                if (input.trim().isEmpty()) {
                    isInterrupted = true;
                } else {
                    String filteredStr = deleteWords(input, toLowerCase(words));
                    System.out.println(filteredStr);
                }
            }
        }
    }

    private static Set<String> toLowerCase(Set<String> words) {
        HashSet<String> unifiedWords = new HashSet<>();

        for (String word : words) {
            unifiedWords.add(word.toLowerCase());
        }
        return unifiedWords;
    }

    @NotNull
    static Set<String> readWordsFrom(@NotNull String dictFilePath) throws IOException {
        HashSet<String> words = new HashSet<>();

        try (Scanner dictionaryScanner = new Scanner(new File(dictFilePath))) {
            while (dictionaryScanner.hasNext()) {
                words.add(dictionaryScanner.next());
            }
        }

        return words;
    }

    @NotNull
    static String deleteWords(
            @NotNull String line,
            @NotNull Set<String> words
    ) {
        StringBuilder result = new StringBuilder();

        Scanner scanner = new Scanner(line);

        while (scanner.hasNext()) {
            String word = scanner.next();
            if (!words.contains(word.toLowerCase())) {
                result.append(" ").append(word);
            }
        }
        scanner.close();
        return result.toString().trim();
    }

}
