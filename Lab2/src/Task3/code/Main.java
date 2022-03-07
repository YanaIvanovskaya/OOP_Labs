package Task3.code;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    private final static String ERROR_ARGUMENT = "1 argument expected: <dictionary file path>";
    private final static String ERROR_FILE = "File not found or incorrect";
    private final static String NO_TRANSLATION = "No such translations. Enter translation or empty string for continue";

    public static void main(String[] args) {
        String dictFilePath = "C:\\Users\\yana-\\All_Projects_Intellij_Idea\\OOP_Labs\\Lab2\\src\\Task3\\test\\correct\\input.txt";
        //getArgumentNull(args);

        if (dictFilePath == null) {
            System.out.println(ERROR_ARGUMENT);
            return;
        }

        Scanner dictionaryScanner = createScannerOrNull(dictFilePath);
        if (dictionaryScanner == null) {
            System.out.println(ERROR_FILE);
            return;
        }
        HashMap<String, String[]> dictionary = readDictionary(dictionaryScanner);

        System.out.println(dictionary);

        Scanner userInput = new Scanner(System.in);

        while (userInput.hasNextLine()) {
            String input = userInput.next();
            if (input.equals("...")) {
                System.out.println("Goodbye");
                userInput.close();
                break;
            } else {
                boolean hasTranslation = dictionary.containsKey(input);

                if (!hasTranslation) {
                    System.out.println(NO_TRANSLATION);
                } else {
                    String[] translations = dictionary.get(input);
                    System.out.println(Arrays.toString(translations));
                }
            }
        }
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

    private static HashMap<String, String[]> readDictionary(Scanner scanner) {
        HashMap<String, String[]> dictionary = new HashMap<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] translate = line.split(" ");
            if (translate.length >= 2) {
                dictionary.put(translate[0], Arrays.copyOfRange(translate, 1, translate.length));
            }
        }

        return dictionary;
    }

}
