package lab_6

import lab_2_task_5.Protocol
import lab_2_task_5.UrlMatcher

class Url {

    val document: String
    val domain: String
    val protocol: Protocol
    val port: Int

    val url: String
        get() {
            val port = if (port != protocol.port) ":$port" else ""
            return "${protocol.value}://$domain$port$document"
        }

    constructor(domain: String, document: String, protocol: Protocol) {
        val documentWithSlash = document.setSlashInFront()

        UrlMatcher.validate(domain = domain, document = documentWithSlash, port = protocol.port)

        this.document = documentWithSlash
        this.domain = domain
        this.port = protocol.port
        this.protocol = protocol
    }

    constructor(domain: String, document: String, protocol: Protocol, port: Int) {
        val documentWithSlash = document.setSlashInFront()

        UrlMatcher.validate(domain = domain, document = documentWithSlash, port = port)

        this.document = documentWithSlash
        this.domain = domain
        this.port = port
        this.protocol = protocol
    }

    override fun toString(): String {
        return "URL(domain=$domain, protocol=$protocol, port=$port, document=$document)"
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Url) {
            this.document == other.document
                    && this.domain == other.domain
                    && this.protocol == other.protocol
                    && this.port == other.port
        } else false
    }

    override fun hashCode(): Int {
        var result = document.hashCode()
        result = 31 * result + domain.hashCode()
        result = 31 * result + protocol.hashCode()
        result = 31 * result + port
        return result
    }
// убрать проверку на пустоту
    private fun String.setSlashInFront() = if (startsWith("/") || isBlank()) this else "/$this"

    companion object {
        fun parse(url: String): Url = UrlMatcher.getUrl(url)
    }

}

