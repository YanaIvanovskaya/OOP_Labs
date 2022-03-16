package Task2.code;

import org.jetbrains.annotations.NotNull;

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
        final String errorArgument = "1 argument expected <html string>";

        String html = args.length == 1 ? args[0] : null;

        if (html == null) {
            System.out.println(errorArgument);
            return;
        }

        System.out.println(htmlEncode(html));
    }

    static String htmlEncode(@NotNull String html) {
        StringBuilder encodedString = new StringBuilder();
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < html.length(); i++) {
            char ch = html.charAt(i);
            boolean isEndOfEntity = ch == ';';
            boolean isNotPartOfEntity = isEndOfEntity && buffer.isEmpty();
            boolean isLastPartOfEntity = isEndOfEntity && !buffer.isEmpty();

            if (ch == '&') {
                encodedString.append(buffer);
                buffer.setLength(0);
                buffer.append(ch);
            } else if (isNotPartOfEntity) {
                encodedString.append(ch);
            } else if (isLastPartOfEntity) {
                buffer.append(ch);
                HtmlEntity entity = HtmlEntity.fromString(buffer.toString());
                switch (entity) {
                    case UNKNOWN -> encodedString.append(buffer);
                    default -> encodedString.append(entity.value);
                }
                buffer.setLength(0);
            } else {
                if (buffer.isEmpty()) encodedString.append(ch);
                else buffer.append(ch);
            }
        }

        encodedString.append(buffer);
        return encodedString.toString();
    }

}
