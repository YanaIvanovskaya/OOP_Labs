package Task3.code;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        final String errorArgument = "1 argument expected: <dictionary file path>";
        final String errorFile = "File not found or incorrect";
        final String noTranslation = "No such translations. Enter translation or empty string for continue";

        String dictFilePath = "C:\\Users\\yana-\\All_Projects_Intellij_Idea\\OOP_Labs\\Lab2\\src\\Task3\\test\\correct\\input.txt";

//        if (dictFilePath == null) {
//            System.out.println(errorArgument);
//            return;
//        }

//        Scanner dictionaryScanner = getScannerOrNull(dictFilePath);
//        if (dictionaryScanner == null) {
//            System.out.println(errorFile);
//            return;
//        }
//        HashMap<String, String[]> dictionary = getDictionary(dictionaryScanner);
//
//        System.out.println(dictionary);
//
//        Scanner userInput = new Scanner(System.in);
//
//        while (userInput.hasNextLine()) {
//            String input = userInput.next();
//            if (input.equals("...")) {
//                System.out.println("Goodbye");
//                userInput.close();
//                break;
//            } else {
//                boolean hasTranslation = dictionary.containsKey(input);
//
//                if (!hasTranslation) {
//                    System.out.println(noTranslation);
//                } else {
//                    String[] translations = dictionary.get(input);
//                    System.out.println(Arrays.toString(translations));
//                }
//            }
//        }
    }
//
//    private static Scanner getScannerOrNull(String filePath) {
//        File file = new File(filePath);
//        if (!(file.exists() && file.isFile() && file.canRead())) {
//            return null;
//        }
//        try {
//            return new Scanner(file);
//        } catch (IOException ex) {
//            return null;
//        }
//    }
//
//    private static HashMap<String, String[]> getDictionary(Scanner scanner) {
//        HashMap<String, String[]> dictionary = new HashMap<>();
//
//        while (scanner.hasNextLine()) {
//            String line = scanner.nextLine();
//            String[] translate = line.split(" ");
//            if (translate.length >= 2) {
//                dictionary.put(translate[0], Arrays.copyOfRange(translate, 1, translate.length));
//            }
//        }
//
//        return dictionary;
//    }

}
