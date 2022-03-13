package Task4.code;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

// Task 4 - Вариант 3 – Фильтр нецензурных слов – 60 баллов
public class Main {

    public static void main(String[] args) {
        final String errorArgument = "1 argument expected: <dictionary file path>";
        final String enterString = "Please, enter not empty string:";

        String dictFilePath = args.length == 1 ? args[0] : null;

        if (dictFilePath == null) {
            System.out.println(errorArgument);
            return;
        }

        HashSet<String> badWords = new HashSet<>();
        try {
            badWords = readWordsFrom(dictFilePath);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(enterString);
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

    private static Scanner getScannerOrNull(String filePath) {
        File file = new File(filePath);
        if (!(file.exists() && file.isFile() && file.canRead())) {
            return null;
        }
        try {
            return new Scanner(file);
        } catch (IOException ex) {
            return null;
        }
    }

    private static HashSet<String> readWordsFrom(String dictFilePath) throws FileNotFoundException {
        final String errorFile = "File not found or incorrect";
        Scanner dictionaryScanner = getScannerOrNull(dictFilePath);

        if (dictionaryScanner == null) {
            throw new FileNotFoundException(errorFile);
        }

        HashSet<String> words = new HashSet<>();

        try (dictionaryScanner) {
            while (dictionaryScanner.hasNext()) {
                words.add(dictionaryScanner.next());
            }
        }

        return words;
    }

    private static String deleteBadWords(String original, HashSet<String> words) {
        StringBuilder result = new StringBuilder();
        HashSet<String> unifiedWords = new HashSet<>();

        for (String word : words) {
            unifiedWords.add(word.toLowerCase());
        }

        for (String word : original.split(" ")) {
            if (!unifiedWords.contains(word.toLowerCase())) {
                result.append(" ").append(word);
            }
        }

        return result.toString().trim();
    }

}
