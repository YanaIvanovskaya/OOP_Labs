package Task3.code;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Task 3 - Вариант 1 - Подсчет частоты встречаемости слов – 40 баллов
public class Task3 {

    public static void main(String[] args) {
        System.out.println("Enter string to count the occurrence of words or an empty string to cancel");
        boolean isInterrupted = false;

        try (Scanner userInput = new Scanner(System.in)) {
            while (!isInterrupted) {
                String input = userInput.nextLine();
                if (input.trim().isEmpty()) {
                    isInterrupted = true;
                } else {
                    Map<String, Integer> frequencyMap = getWordFrequencyMap(input);
                    printMap(frequencyMap);
                }
            }
        }
    }

    static Map<String, Integer> getWordFrequencyMap(@NotNull String line) {
        HashMap<String, Integer> frequencyMap = new HashMap<>();
        if (line.isEmpty() || line.isBlank()) {
            return frequencyMap;
        }

        for (String word : line.trim().split("( )+")) {
            putWord(word, frequencyMap);
        }

        return frequencyMap;
    }

    static void putWord(String word, Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.equalsIgnoreCase(word)) {
                map.put(key, entry.getValue() + 1);
                return;
            }
        }
        map.put(word, 1);
    }

    static void printMap(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

}
