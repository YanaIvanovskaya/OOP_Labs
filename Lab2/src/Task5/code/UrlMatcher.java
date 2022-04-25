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
    private static final String portRegex = ":[0-9]+";
    private static final String documentRegex = "(/(.)+)";

    private UrlMatcher() {
    }

    static boolean canParseUrl(@NotNull String url) {
        String urlRegex = protocolRegex + "://" + hostRegex +
                "(" + portRegex + "|)" +
                "(" + documentRegex + "|)";
        try {
            boolean isMatchesPattern = url.matches(urlRegex);
            Integer port = getPortOrNull(url);
            if (port == null) {
                return isMatchesPattern;
            } else {
                return isMatchesPattern && port >= 0 && port <= 65535;
            }
        } catch (MalformedURLException ex) {
            return false;
        }
    }

    static UrlInfo getUrlInfo(@NotNull String url) throws MalformedURLException {
        if (!canParseUrl(url)) {
            throw new MalformedURLException();
        }

        //
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

    private static Integer getPortOrNull(@NotNull String url) throws MalformedURLException {
        int indexOfProtocolColon = url.indexOf(":");
        int indexOfPortColon = url.indexOf(":", indexOfProtocolColon + 1);
        if (indexOfPortColon == -1) return null;
        try {
            int indexOfPortEnd = url.indexOf("/", indexOfPortColon);
            if (indexOfPortEnd == -1) {
                indexOfPortEnd = url.length();
            }
            String portStr = url.substring(indexOfPortColon + 1, indexOfPortEnd);
            return Integer.parseInt(portStr);
        } catch (Exception ex) {
            throw new MalformedURLException();
        }
    }

}
