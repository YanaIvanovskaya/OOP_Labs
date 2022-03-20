package Task2.code;

import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

enum HtmlEntity {

    QUOT("\"", "&quot;"),
    APOS("'", "&apos;"),
    LT("<", "&lt;"),
    GT(">", "&gt;"),
    AMP("&", "&amp;"),
    UNKNOWN("", "");

    final String value;
    private final String representation;

    HtmlEntity(String value, String representation) {
        this.value = value;
        this.representation = representation;
    }

    static HtmlEntity fromString(String value) {
        for (HtmlEntity entity : values()) {
            if (value.equals(entity.representation)) return entity;
        }
        return HtmlEntity.UNKNOWN;
    }

}

// Task 2 - Вариант 5 – HTML Decode – 60 баллов
public class Task2 {

    public static void main(String[] args) {
        System.out.println("Enter html to decoding or an empty string to cancel");
        boolean isInterrupted = false;

        try (Scanner userInput = new Scanner(System.in)) {
            while (!isInterrupted) {
                String html = userInput.nextLine();
                if (html.trim().isEmpty()) {
                    isInterrupted = true;
                } else {
                    String decodedHtml = htmlDecode(html);
                    System.out.println(decodedHtml);
                }
            }
        }
    }

    static String htmlDecode(@NotNull String html) {
        StringBuilder decodedString = new StringBuilder();
        StringBuilder entityBuffer = new StringBuilder();

        for (int i = 0; i < html.length(); i++) {
            char ch = html.charAt(i);
            boolean isJustSemicolon = ch == ';' && entityBuffer.isEmpty();

            if (isJustSemicolon) {
                decodedString.append(ch);
                continue;
            }

            switch (ch) {
                case '&' -> {
                    decodedString.append(entityBuffer);
                    entityBuffer.setLength(0);
                    entityBuffer.append(ch);
                }
                case ';' -> {
                    entityBuffer.append(ch);
                    HtmlEntity entity = HtmlEntity.fromString(entityBuffer.toString());
                    switch (entity) {
                        case UNKNOWN -> decodedString.append(entityBuffer);
                        default -> decodedString.append(entity.value);
                    }
                    entityBuffer.setLength(0);
                }
                default -> {
                    if (entityBuffer.isEmpty()) decodedString.append(ch);
                    else entityBuffer.append(ch);
                }
            }
        }

        decodedString.append(entityBuffer);
        return decodedString.toString();
    }

}
