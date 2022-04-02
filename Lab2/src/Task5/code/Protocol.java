package Task5.code;

import org.jetbrains.annotations.NotNull;

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

    static Protocol fromString(@NotNull String str) {
        for (Protocol protocol : values()) {
            if (protocol.value.equals(str.toLowerCase())) return protocol;
        }
        return Protocol.UNKNOWN;
    }

}
