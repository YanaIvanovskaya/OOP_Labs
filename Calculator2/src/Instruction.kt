sealed interface Instruction

data class Variable(
        val identifier: String,
        val value: Value?,
        val type: Type
) : Instruction {
    sealed interface Value {
        data class Number(val value: Double) : Value
        data class Identifier(val value: String) : Value
    }

    enum class Type {
        DECLARATION,
        ASSIGNMENT
    }
}

data class Function(
        val identifier: String,
        val operation: Operation?,
        val operands: List<String>
) : Instruction {
    enum class Operation {
        DIVISION,
        SUBTRACTION,
        ADDITION,
        MULTIPLICATION
    }
}

data class Print(val identifier: String) : Instruction
object PrintVars : Instruction
object PrintFns : Instruction
