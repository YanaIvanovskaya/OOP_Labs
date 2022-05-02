sealed interface Expression {

    data class Variable(
        val identifier: String,
        val value: Value
    ) : Expression

    data class Function(
        val identifier: String,
        val variable1: Variable,
        val variable2: Variable? = null,
        val operation: Operation = Operation.NONE
    ) : Expression {
        enum class Operation {
            ADDITION,
            MULTIPLICATION,
            DIVISION,
            SUBTRACTION,
            NONE
        }
    }

}

