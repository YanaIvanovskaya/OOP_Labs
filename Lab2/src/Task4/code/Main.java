package Task4.code;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    private static final String ENTER_STRING = "Please, enter string:";
    private final static String ERROR_ARGUMENT = "1 argument expected: <dictionary file path>";
    private final static String ERROR_FILE = "File not found or incorrect";

    public static void main(String[] args) {

        String dictFilePath = getArgumentNull(args);

        if (dictFilePath == null) {
            System.out.println(ERROR_ARGUMENT);
            return;
        }

        Scanner dictionaryScanner = createScannerOrNull(dictFilePath);
        if (dictionaryScanner == null) {
            System.out.println(ERROR_FILE);
            return;
        }

        System.out.println(ENTER_STRING);
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();
        userInput.close();

        HashSet<String> badWords = readWordsFrom(dictionaryScanner);
        if (badWords.size() > 0) {
            String goodString = deleteBadWords(input, badWords);
            System.out.println(goodString);
        } else System.out.println(input);
    }

    private static String getArgumentNull(String[] args) {
        if (args.length != 1) {
            return null;
        } else {
            return args[0];
        }
    }

    private static Scanner createScannerOrNull(String filePath) {
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
