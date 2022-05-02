class CalculatorController(
    private val calculator: Calculator,
    private val parse: Parser
) {

    fun evaluate(expression: String) {
        val parsedExpression = parse(expression)
        calculator.calculate(parsedExpression)
    }


}

class Parser {

    enum class Type(val keyWord: String) {
        VARIABLE_DECLARATION("var"),
        VARIABLE_INITIALIZATION("let"),
        FUNCTION("fn"),
        UNKNOWN("")
    }

    operator fun invoke(expression: String): Expression {
//        val variableRegex =
//            Regex("(var $IDENTIFIER_REGEX)|(let $IDENTIFIER_REGEX = ($IDENTIFIER_REGEX|$NUMBER_REGEX))")
//        Regex(IDENTIFIER_REGEX).runCatching {
//            findAll("var var1").filter { it.value.matches(Regex(VARIABLE_KEYWORD_REGEX)) }
//        }
//        if

        // var qwert
        // let qwert = 546
        // let q = qwert

        var type = Type.UNKNOWN
        Type.values().forEach {
            if (expression.startsWith(it.keyWord)) {
                type = it
                return@forEach
            }
        }
        if (type == Type.UNKNOWN) {
            throw IllegalStateException()
        }




        return Expression.Function(
            identifier = "qwert",
            variable1 = Expression.Variable(identifier = "qwe", value = Value.Number(45.5))
        )
    }

    companion object {
        private const val VARIABLE_KEYWORD_REGEX = "(var|let)"
        private const val IDENTIFIER_REGEX = "([a-zA-Z\\w_]+)"
        private const val NUMBER_REGEX = "^(-)?([0-9]+)(([.]{1})[0-9]+)?\$"
        private const val OPERATION_REGEX = "[(+-/*]"
    }

}