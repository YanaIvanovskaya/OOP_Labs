package Task5.code;

import java.net.MalformedURLException;
import java.util.Scanner;

// Task 5 - Вариант 1 – парсер URL-ов – 80 баллов
public class Task5 {

    public static void main(String[] args) {
        System.out.println("Please, enter url to validate or empty line to cancel:");
        boolean isInterrupted = false;

        try (Scanner userInput = new Scanner(System.in)) {
            while (!isInterrupted) {
                String url = userInput.nextLine();
                if (url.trim().isEmpty()) {
                    isInterrupted = true;
                } else try {
                    printUrlInfo(url,UrlMatcher.getUrlInfo(url));
                } catch (MalformedURLException ex) {
                    System.out.println("This is not url");
                }
            }
        }
    }

    private static void printUrlInfo(String url,UrlInfo info) {
        System.out.println(url);
        System.out.println("PROTOCOL: " + info.protocol);
        System.out.println("HOST: " + info.host);
        System.out.println("PORT: " + info.port);
        System.out.println("DOC: " + info.document);
    }

}
