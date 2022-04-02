package Task5.code;

import java.util.Objects;

public class UrlInfo {

    final Protocol protocol;
    final String host;
    final int port;
    final String document;

    UrlInfo(
            Protocol protocol,
            String host,
            int port,
            String document
    ) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.document = document;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlInfo urlInfo = (UrlInfo) o;
        return port == urlInfo.port
                && protocol == urlInfo.protocol
                && Objects.equals(host, urlInfo.host)
                && Objects.equals(document, urlInfo.document);
    }

}
