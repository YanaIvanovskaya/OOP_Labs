package Task5.code;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class UrlMatcher {

    private static final String protocolRegex = getProtocolRegex();
    private static final String hostRegex = "(\\w|[.-])+";
    private static final String portRegex = "(:(" + getPortNumberRegex() + "))";
    private static final String documentRegex = "(/(.)+)";

    private UrlMatcher() {
    }

    static boolean canParseUrl(@NotNull String url) {
        String urlRegex = protocolRegex + "://" + hostRegex +
                "(" + portRegex + "|)" +
                "(" + documentRegex + "|)";
        return url.matches(urlRegex);
    }

    static UrlInfo getUrlInfo(@NotNull String url) throws MalformedURLException {
        if (!canParseUrl(url)) {
            throw new MalformedURLException();
        }

        Matcher protocolMatcher = Pattern.compile(protocolRegex).matcher(url);
        Protocol protocol = protocolMatcher.find() ?
                Protocol.fromString(protocolMatcher.group()) : Protocol.UNKNOWN;
        int startIndex = protocol.value.length();

        String host = "";
        Matcher hostMatcher = Pattern.compile(hostRegex).matcher(url);
        if (hostMatcher.find(startIndex)) {
            host = hostMatcher.group();
            startIndex = hostMatcher.end();
        }

        int port = protocol.port;
        Matcher portMatcher = Pattern.compile(portRegex).matcher(url);
        if (portMatcher.find(startIndex)) {
            port = Integer.parseInt(portMatcher.group().substring(1));
            startIndex = portMatcher.end();
        }

        Matcher docMatcher = Pattern.compile(documentRegex).matcher(url);
        String document = docMatcher.find(startIndex) ? docMatcher.group() : "";

        return new UrlInfo(
                protocol,
                host,
                port,
                document
        );
    }

    private static String getPortNumberRegex() {
        final String or = "|";

        final String num1_9 = "[1-9]";
        final String num10_99 = "([1-9][0-9])";
        final String num100_999 = "([1-9][0-9][0-9])";
        final String num1000_9999 = "([1-9][0-9][0-9][0-9])";
        final String num10000_59999 = "([1-5][0-9][0-9][0-9][0-9])";
        final String num60000_64999 = "(6[0-4][0-9][0-9][0-9])";
        final String num65000_65499 = "(65[0-4][0-9][0-9])";
        final String num65500_65529 = "(655[0-2][0-9])";
        final String num65530_65535 = "(6553[0-5])";

        return num65530_65535 +
                or + num65500_65529 +
                or + num65000_65499 +
                or + num60000_64999 +
                or + num10000_59999 +
                or + num1000_9999 +
                or + num100_999 +
                or + num10_99 +
                or + num1_9;
    }

    private static String getProtocolRegex() {
        Iterator<Protocol> protocols = Arrays.stream(
                Protocol.values())
                .filter((Protocol p) -> p != Protocol.UNKNOWN)
                .sorted((Protocol p1, Protocol p2) ->
                        p1.value.length() <= p2.value.length() ? 1 : -1
                ).iterator();
        StringBuilder regex = new StringBuilder("(");
        String or = "|";

        while (protocols.hasNext()) {
            Protocol protocol = protocols.next();
            boolean isLastElement = !protocols.hasNext();
            regex.append(protocol.value).append(or)
                    .append(protocol.value.toUpperCase())
                    .append(isLastElement ? ")" : or);
        }

        return regex.toString();
    }

}
