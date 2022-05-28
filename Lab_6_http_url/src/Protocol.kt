enum class Protocol(val value: String, val port: Int) {
    HTTP("http", 80),
    HTTPS("https", 443),
    FTP("ftp", 21),
    UNKNOWN("", 0);

    companion object {
        fun fromString(str: String): Protocol {
            return values().firstOrNull { it.value == str.lowercase() } ?: UNKNOWN
        }
    }
}