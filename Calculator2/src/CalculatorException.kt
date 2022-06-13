class CalculatorException(val errorType: CalculatorErrorType) : Throwable()

enum class CalculatorErrorType {
    IDENTIFIER_ALREADY_EXIST,
    IDENTIFIER_DOES_NOT_EXIST,
    KEYWORD_AS_IDENTIFIER
}