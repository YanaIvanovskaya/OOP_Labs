sealed interface Expression {

    data class Variable(val type: Type, val identifier: String, val value: Value) {
        enum class Type {
            DECLARATION,
            INITIALIZATION
        }
    }

}