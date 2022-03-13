package Task5.code;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum Protocol {
    HTTP("http"),
    HTTPS("https"),
    FTP("ftp");

    final String value;

    Protocol(String value) {
        this.value = value;
    }

    static String getProtocolRegex() {
        StringBuilder regex = new StringBuilder("(");
        int len = values().length;
        for (Protocol protocol : values()) {
            boolean isLastElement = protocol == values()[len - 1];
            regex.append(protocol.value)
                    .append(isLastElement ? ")" : "|");
        }
        return regex.toString();
    }
}

public class Main {
    public static void main(String[] args) {

        String[] testUrls = {
                "ftp://qwerty-123.ru", // ok
                "ftp://", // error
                "https:///qwerty.ru",// error
                "http://qwerty.ru:67767/document" // error
        };

//        for (String s : testUrls) {
//            System.out.println(parseUrl(s));
//        }

        String url = "http://qwerty-123.ru:62222/document/123/234";
        System.out.println(parseUrl(url));
    }

    static boolean parseUrl(
            String url
    ) {
        String protocolRegex = Protocol.getProtocolRegex();
        String hostRegex = "(\\w|[.-])+";
        String portRegex = "((:(" + getPortNumberRegex() + "))|)";
        String documentRegex = "((/(.)+)|)";
        String urlRegex = protocolRegex + "://" + hostRegex + portRegex + documentRegex;

        int startIndex = 0;
        Matcher protocolMatcher = Pattern.compile(protocolRegex).matcher(url);
        if (protocolMatcher.find()) {
            System.out.println(" protocolRegex: " + protocolMatcher.group());
            startIndex = protocolMatcher.end();
        }

        Matcher hostMatcher = Pattern.compile(hostRegex).matcher(url);
        if (hostMatcher.find(startIndex)) {
            System.out.println(" hostRegex: " + hostMatcher.group());
            startIndex = hostMatcher.end();
        }

        Matcher portMatcher = Pattern.compile(portRegex).matcher(url);
        while (portMatcher.find(startIndex)) {
            if (!portMatcher.group().isEmpty()) {
                System.out.println(" portRegex: " + portMatcher.group());
                startIndex = portMatcher.end();
                break;
            }
        }

        Matcher docMatcher = Pattern.compile(documentRegex).matcher(url);
        while (docMatcher.find(startIndex)) {
            if (!docMatcher.group().isEmpty()) {
                System.out.println(" documentRegex: " + docMatcher.group());
                break;
            }
        }

        return url.matches(urlRegex);
    }

    private static String getPortNumberRegex() {
        final String or = "|";
        final String num0_9 = "[0-9]";
        final String numberValue = "/N";

        final String num1_9 = "[1-9]";
        final String num10_99 = "([1-9]/N)".replaceAll(numberValue, num0_9);
        final String num100_999 = "([1-9]/N/N)".replaceAll(numberValue, num0_9);
        final String num1000_9999 = "([1-9]/N/N/N)".replaceAll(numberValue, num0_9);
        final String num10000_59999 = "([1-5]/N/N/N/N)".replaceAll(numberValue, num0_9);
        final String num60000_64999 = "(6[0-4]/N/N/N)".replaceAll(numberValue, num0_9);
        final String num65000_65499 = "(65[0-4]/N/N)".replaceAll(numberValue, num0_9);
        final String num65500_65529 = "(655[0-2]/N)".replaceAll(numberValue, num0_9);
        final String num65530_65535 = "(6553[0-5])".replaceAll(numberValue, num0_9);

        return num65530_65535 +
                or +
                num65500_65529 +
                or +
                num65000_65499 +
                or +
                num60000_64999 +
                or +
                num10000_59999 +
                or +
                num1000_9999 +
                or +
                num100_999 +
                or +
                num10_99 +
                or +
                num1_9;
    }

}
