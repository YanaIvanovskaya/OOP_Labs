class ParserException(val errorType: ParserErrorType) : Throwable()

enum class ParserErrorType {
    UNSUPPORTED_OPERATION,
    UNKNOWN_INSTRUCTION
}