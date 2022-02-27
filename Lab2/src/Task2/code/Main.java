package Task2.code;

enum HtmlEntity {

    QUOT("\"", "&quot;"),
    APOS("'", "&apos;"),
    LT("<", "&lt;"),
    GT(">", "&gt;"),
    AMP("&", "&amp;"),
    UNKNOWN("", "");

    final String value;
    final String representation;

    HtmlEntity(String value, String representation) {
        this.value = value;
        this.representation = representation;
    }

    static HtmlEntity convertToHtmlEntity(String value) {
        for (HtmlEntity entity : values()) {
            if (value.equals(entity.representation)) {
                return entity;
            }
        }
        return HtmlEntity.UNKNOWN;
    }

}

public class Main {

    private static final String ERROR_ARGUMENT = "1 argument expected <html string>";

    public static void main(String[] args) {
        String html = "&Cat &lt;says&gt; &quot;;Meow&quot;. M&amp;M&apos;s&";//getArgumentOrNull(args);

//        if (html == null) {
//            System.out.println();
//            return;
//        }

        System.out.println(htmlEncode(html));


    }

    static String getArgumentOrNull(String[] args) {
        if (args.length != 1) {
            return null;
        }
        return args[0];
    }

    static String htmlEncode(String html) {
        StringBuilder encodedString = new StringBuilder();
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < html.length(); i++) {
            char ch = html.charAt(i);
            if (ch == '&') {
                if (!buffer.isEmpty()) {
                    encodedString.append(buffer);
                    buffer.setLength(0);
                }
                buffer.append(ch);
            } else if (ch == ';') {
                if (buffer.isEmpty()) {
                    encodedString.append(ch);
                    continue;
                }
                buffer.append(ch);
                HtmlEntity entity =
                        HtmlEntity.convertToHtmlEntity(buffer.toString());
                encodedString.append(entity.value);
                buffer.setLength(0);
            } else if (buffer.isEmpty()) {
                encodedString.append(ch);
            } else {
                buffer.append(ch);
            }
        }
        encodedString.append(buffer);

        return encodedString.toString();
    }

}
