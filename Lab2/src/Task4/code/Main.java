package Task4.code;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final String errorArgument = "1 argument expected: <dictionary file path>";
        final String errorFile = "File not found or incorrect";

        String dictFilePath = args.length == 1 ? args[0] : null;

        if (dictFilePath == null) {
            System.out.println(errorArgument);
            return;
        }

        Scanner dictionaryScanner = getScannerOrNull(dictFilePath);
        if (dictionaryScanner == null) {
            System.out.println(errorFile);
            return;
        }

        String input = getUserInput();

        HashSet<String> badWords = readWordsFrom(dictionaryScanner);
        if (badWords.size() > 0) {
            String goodString = deleteBadWords(input, badWords);
            System.out.println(goodString);
        } else System.out.println(input);
    }

    private static String getUserInput() {
        final String enterString = "Please, enter string:";
        System.out.println(enterString);
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();
        userInput.close();
        return input;
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

    private static HashSet<String> readWordsFrom(Scanner scanner) {
        HashSet<String> words = new HashSet<>();

        while (scanner.hasNext()) {
            words.add(scanner.next());
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
