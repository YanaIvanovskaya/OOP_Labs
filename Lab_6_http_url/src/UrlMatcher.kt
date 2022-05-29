import java.net.MalformedURLException

object UrlMatcher {
    private val protocol = getProtocolRegexStr()
    private const val host = """([\w.-]+)"""
    private const val port = """(:(\d+))?"""
    private const val document = """((/[\w.]+)+)?"""
    private val urlRegex = (protocol + host + port + document).toRegex()

    @Throws(MalformedURLException::class)
    fun getUrlInfo(url: String): UrlInfo {
        val urlInfo = urlRegex.matchEntire(url)?.toUrlInfo()
        when {
            urlInfo == null ->
                throw MalformedURLException("Error: This is not url")
            urlInfo.port !in 1..65535 ->
                throw MalformedURLException("Error: Port number must be in range [1;65535]")
            else -> return urlInfo
        }
    }

    private fun getProtocolRegexStr(): String {
        var regexStr = ""
        Protocol.values()
                .sortedByDescending { it.value.length }
                .forEach { protocol ->
                    val isLastElement = protocol == Protocol.UNKNOWN
                    regexStr += "${protocol.value}|${protocol.value.uppercase()}${if (isLastElement) "" else "|"}"
                }
        regexStr = "(?i)(($regexStr)://)"
        return regexStr
    }

}

private fun MatchResult?.toUrlInfo(): UrlInfo? {
    this ?: return null
    val protocol = Protocol.fromString(groupValues[2])
    return UrlInfo(
            protocol = protocol,
            host = groupValues[3],
            port = groupValues[5].runCatching { toInt() }.getOrDefault(protocol.port),
            document = groupValues[6]
    )
}