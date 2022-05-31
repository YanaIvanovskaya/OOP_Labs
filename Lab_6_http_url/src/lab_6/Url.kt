package lab_6

import lab_2_task_5.Protocol
import lab_2_task_5.UrlInfo
import lab_2_task_5.UrlMatcher

class Url {

    private val mDocument: String
    private val mDomain: String
    private val mProtocol: Protocol
    private val mPort: Int

    constructor(url: String) {
        val urlInfo = parseUrl(url)

        mDocument = urlInfo.document
        mDomain = urlInfo.host
        mPort = urlInfo.port
        mProtocol = urlInfo.protocol
    }

    constructor(domain: String, document: String) {
        val documentWithFlash = document.setSlashInFront()

        UrlMatcher.validate(domain = domain, document = documentWithFlash, port = 80)

        mDocument = documentWithFlash
        mDomain = domain
        mPort = 80
        mProtocol = Protocol.HTTP
    }

    constructor(domain: String, document: String, protocol: Protocol, port: Int) {
        val documentWithFlash = document.setSlashInFront()

        UrlMatcher.validate(domain = domain, document = documentWithFlash, port = port)

        mDocument = documentWithFlash
        mDomain = domain
        mPort = port
        mProtocol = protocol
    }

    private fun String.setSlashInFront() = if (startsWith("/") || isBlank()) this else "/$this"

    val url: String
        get() {
            val protocol = "${mProtocol.value}://"
            val port = if (mPort != mProtocol.port) ":$mPort" else ""
            return "$protocol$mDomain$port$mDocument"
        }

    val document: String
        get() = mDocument

    val domain: String
        get() = mDomain

    val port: Int
        get() = mPort

    val protocol: Protocol
        get() = mProtocol

    private fun parseUrl(url: String): UrlInfo {
        return UrlMatcher.getUrlInfo(url)
    }

}

