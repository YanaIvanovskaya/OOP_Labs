package Task3.code;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Task 3 - Вариант 1 - Подсчет частоты встречаемости слов – 40 баллов
public class Main {

    public static void main(String[] args) {
        String userInput = new Scanner(System.in).nextLine();
        HashMap<String, Integer> frequencyMap = getWordFrequencyMap(userInput);
        printMap(frequencyMap);
    }

    static HashMap<String, Integer> getWordFrequencyMap(String line) {
        HashMap<String, Integer> frequencyMap = new HashMap<>();

        for (String word : line.split(" ")) {
            frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        }

        return frequencyMap;
    }

    static void printMap(HashMap<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

}
