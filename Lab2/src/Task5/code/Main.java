package Task5.code;

import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Task 5 - Вариант 1 – парсер URL-ов – 80 баллов
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
        try {
            System.out.println(UrlMatcher.getUrlInfo(url));
        } catch (MalformedURLException ex) {
            System.out.println("not url");
        }
    }

}

final class UrlMatcher {

    private static final String protocolRegex = Protocol.getProtocolRegex();
    private static final String hostRegex = "(\\w|[.-])+";
    private static final String portRegex = "(:(" + getPortNumberRegex() + "))";
    private static final String documentRegex = "(/(.)+)";

    private UrlMatcher() {
    }

    static boolean parseUrl(String url) {
        String urlRegex = protocolRegex + "://" + hostRegex +
                "(" + portRegex + "|)" +
                "(" + documentRegex + "|)";
        return url.matches(urlRegex);
    }

    static String getUrlInfo(String url) throws MalformedURLException {
        if (!parseUrl(url)) {
            throw new MalformedURLException();
        }
        StringBuilder urlInfo = new StringBuilder(url).append("\n");
        String protocol = "";
        int startIndex = 0;

        Matcher protocolMatcher = Pattern.compile(protocolRegex).matcher(url);
        if (protocolMatcher.find()) {
            protocol = protocolMatcher.group();
            urlInfo.append("PROTOCOL: ")
                    .append(protocol)
                    .append("\n");
            startIndex = protocolMatcher.end();
        }

        Matcher hostMatcher = Pattern.compile(hostRegex).matcher(url);
        if (hostMatcher.find(startIndex)) {
            urlInfo.append("HOST: ")
                    .append(hostMatcher.group())
                    .append("\n");
            startIndex = hostMatcher.end();
        }

        Matcher portMatcher = Pattern.compile(portRegex).matcher(url);
        String port = portMatcher.find(startIndex) ? portMatcher.group().substring(1) :
                String.valueOf(Protocol.fromString(protocol).port);
        if (portMatcher.find(startIndex)) {
            urlInfo.append("PORT: ")
                    .append(port)
                    .append("\n");
            startIndex = portMatcher.end();
        }

        Matcher docMatcher = Pattern.compile(documentRegex).matcher(url);
        if (docMatcher.find(startIndex)) {
            urlInfo.append("DOC: ")
                    .append(docMatcher.group())
                    .append("\n");
        }

        return urlInfo.toString();
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

enum Protocol {
    HTTP("http", 80),
    HTTPS("https", 443),
    FTP("ftp", 21),
    UNKNOWN("", 0);

    final String value;
    final int port;

    Protocol(String value, int port) {
        this.value = value;
        this.port = port;
    }

    static String getProtocolRegex() {
        StringBuilder regex = new StringBuilder("(");
        String or = "|";
        int len = values().length;

        for (Protocol protocol : values()) {
            boolean isLastElement = protocol == values()[len - 1];
            regex.append(protocol.value).append(or)
                    .append(protocol.value.toUpperCase())
                    .append(isLastElement ? ")" : or);
        }
        return regex.toString();
    }

    static Protocol fromString(String str) {
        for (Protocol protocol : values()) {
            if (protocol.value.equals(str.toLowerCase())) return protocol;
        }
        return Protocol.UNKNOWN;
    }

}
