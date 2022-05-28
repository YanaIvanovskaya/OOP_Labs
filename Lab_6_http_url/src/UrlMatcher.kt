import java.net.MalformedURLException

object UrlMatcher {
    private val protocolRegex = """((${getProtocolRegex()})://)"""
    private val hostRegex = """([\w.-]+)""".toRegex()
    private val portRegex = """(:(\d+))?""".toRegex()
    private val documentRegex = """((/[\w.]+)+)?""".toRegex()
    private val urlRegex = (protocolRegex + hostRegex + portRegex + documentRegex).toRegex()

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

    private fun getProtocolRegex(): String {
        var regex = ""
        Protocol.values()
                .sortedByDescending { it.value.length }
                .forEach { protocol ->
                    val isLastElement = protocol == Protocol.UNKNOWN
                    regex += "${protocol.value}|${protocol.value.uppercase()}${if (isLastElement) "" else "|"}"
                }
        return regex
    }

}

private fun MatchResult?.toUrlInfo(): UrlInfo? {
    this ?: return null
    println(groupValues)
//    val isPortInvalid = groupValues[4] == ":"
//    if (isPortInvalid) return null
    val protocol = Protocol.fromString(groupValues[2])
    return UrlInfo(
            protocol = protocol,
            host = groupValues[3],
            port = groupValues[5].runCatching { toInt() }.getOrDefault(protocol.port),
            document = groupValues[6]
    )
}