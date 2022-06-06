package lab_2_task_5

import lab_6.Url
import java.net.MalformedURLException

object UrlMatcher {
    private val protocol = getProtocolRegexStr()
    private const val host = """([\w.-]+)"""
    private const val port = """(:(\d+))?"""
    private const val document = """(/|(/[^/]+?)+)?"""
    private val urlRegex = (protocol + host + port + document).toRegex()

    @Throws(MalformedURLException::class)
    fun getUrl(url: String): Url {
        val matchResult = urlRegex.matchEntire(url)?.groupValues
        val protocol = matchResult?.let {
            Protocol.fromString(matchResult[2])
        } ?: throw MalformedURLException("Error: This is not url")

        val port = matchResult[5]
                .runCatching { toInt() }
                .getOrDefault(protocol.port)

        when {
            !isPortValid(port) ->
                throw MalformedURLException("Error: Port number must be in range [1;65535]")
            else -> return Url(
                    protocol = protocol,
                    domain = matchResult[3],
                    port = port,
                    document = matchResult[6]
            )
        }
    }

    fun validate(domain: String, document: String, port: Int) {
        when {
            !host.toRegex().matches(domain) ->
                throw java.lang.IllegalArgumentException("Domain is incorrect")
            !isPortValid(port) ->
                throw java.lang.IllegalArgumentException("Port number must be in range [1;65535]")
            !this.document.toRegex().matches(document) ->
                throw java.lang.IllegalArgumentException("Document is incorrect")
        }
    }

    private fun isPortValid(port: Int): Boolean {
        return port in 1..65535
    }

    private fun getProtocolRegexStr(): String {
        var regexStr = ""
        Protocol.values()
                .sortedByDescending { it.value.length }
                .forEachIndexed { index, protocol ->
                    val isLastElement = index == Protocol.values().lastIndex
                    regexStr += "${protocol.value}|${protocol.value.uppercase()}${if (isLastElement) "" else "|"}"
                }
        regexStr = "(?i)(($regexStr)://)"
        return regexStr
    }

}