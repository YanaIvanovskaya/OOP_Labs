package Task3.code;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Task 3 - Вариант 1 - Подсчет частоты встречаемости слов – 40 баллов
public class Task3 {

    public static void main(String[] args) {
        String userInput = new Scanner(System.in).nextLine();
        Map<String, Integer> frequencyMap = getWordFrequencyMap(userInput);
        printMap(frequencyMap);
    }

    static Map<String, Integer> getWordFrequencyMap(@NotNull String line) {
        HashMap<String, Integer> frequencyMap = new HashMap<>();
        if (line.isEmpty() || line.isBlank()) {
            return frequencyMap;
        }

        for (String word : line.split(" ")) {
            frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        }

        return frequencyMap;
    }

    static void printMap(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

}
