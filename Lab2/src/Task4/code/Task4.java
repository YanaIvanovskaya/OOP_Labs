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

        Set<String> badWords = new HashSet<>();
        try {
            badWords = readWordsFrom(dictFilePath);
        } catch (IOException ex) {
            System.out.println("File not found or incorrect");
        }

        System.out.println("Please, enter string to filter or empty line to cancel:");
        boolean isInterrupted = false;
        try (Scanner userInput = new Scanner(System.in)) {
            while (!isInterrupted) {
                String input = userInput.nextLine();
                if (input.trim().isEmpty()) {
                    isInterrupted = true;
                } else {
                    String goodString = deleteBadWords(input, badWords);
                    System.out.println(goodString);
                }
            }
        }
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
    static String deleteBadWords(
            @NotNull String original,
            @NotNull Set<String> words
    ) {
        StringBuilder result = new StringBuilder();
        HashSet<String> unifiedWords = new HashSet<>();

        for (String word : words) {
            unifiedWords.add(word.toLowerCase());
        }

        for (String word : original.trim().split("( )+")) {
            if (!unifiedWords.contains(word.toLowerCase())) {
                result.append(" ").append(word);
            }
        }

        return result.toString().trim();
    }

}
