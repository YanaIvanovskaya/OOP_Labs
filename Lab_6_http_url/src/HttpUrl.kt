//class HttpUrl {
//
//    private val mDocument: String
//    private val mDomain: String
//    private val mProtocol: Protocol
//    private val mPort: Int
//
//    constructor(url: String) {
//        mDocument = getParsedDocument(url)
//        mDomain = getParsedDomain(url)
//        mPort = 0
//        mProtocol = Protocol.HTTP
//    }
//
//    constructor(domain: String, document: String, protocol: Protocol) : this(domain, document, protocol, 0)
//    constructor(domain: String, document: String, protocol: Protocol, port: Int) {
//        mDocument = document
//        mDomain = domain
//        mPort = port
//        mProtocol = protocol
//    }
//
//    private fun ensureValid(): Boolean {
//        return false
//    }
//
//    private fun getParsedDocument(url: String): String {
//        return ""
//    }
//
//    private fun getParsedDomain(url: String): String {
//        return ""
//    }
//
//    private fun getParsedPort(url: String): Int {
//        return 0
//    }
//
//}
//
