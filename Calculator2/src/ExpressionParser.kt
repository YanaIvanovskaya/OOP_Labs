class InstructionParser {

    private enum class InstructionRegExp(val regex: Regex) {
        VARIABLE_ASSIGNMENT("let ${RegExp.IDENTIFIER} = (${RegExp.IDENTIFIER}|${RegExp.NUMBER})".toRegex()),
        VARIABLE_DECLARATION("var ${RegExp.IDENTIFIER}".toRegex()),
        FUNCTION_OPERATION("fn ${RegExp.IDENTIFIER} = ${RegExp.IDENTIFIER} ${RegExp.OPERATION} ${RegExp.IDENTIFIER}".toRegex()),
        FUNCTION_ASSIGNMENT("fn ${RegExp.IDENTIFIER} = ${RegExp.IDENTIFIER}".toRegex()),
        PRINT("print ${RegExp.IDENTIFIER}".toRegex()),
        PRINTVARS("printvars".toRegex()),
        PRINTFNS("printfns".toRegex());

        private enum class RegExp(val regex: Regex) {
            IDENTIFIER("""([a-zA-Z][_\w]+)?""".toRegex()),
            NUMBER("""(-?\d+(\.\d+)?)""".toRegex()),
            OPERATION("""(\+|-|//|\*)""".toRegex());

            override fun toString() = regex.pattern
        }
    }

    fun parse(instruction: String): Instruction {
        var matchResult: MatchResult? = null
        var instructionRegExp: InstructionRegExp? = null
        InstructionRegExp.values().forEach { regExp ->
            regExp.regex.matchEntire(instruction).also { result ->
                if (result != null) {
                    matchResult = result
                    instructionRegExp = regExp
                    return@forEach
                }
            }
        }

        println(matchResult?.groupValues)

        val groups = matchResult?.groupValues ?: throw java.lang.IllegalStateException("")

        val res = when (instructionRegExp) {
            InstructionRegExp.VARIABLE_ASSIGNMENT -> Instruction.Variable(
                    identifier = groups.getOrNull(1) ?: "ERROR",
                    type = Instruction.Variable.Type.ASSIGNMENT,
                    value = null
            )
            InstructionRegExp.VARIABLE_DECLARATION -> Instruction.Variable(
                    identifier = groups.getOrNull(1) ?: "ERROR",
                    type = Instruction.Variable.Type.DECLARATION,
                    value = groups.getOrNull(2)?.runCatching { toDouble() }?.getOrNull()
            )
            InstructionRegExp.FUNCTION_ASSIGNMENT -> Instruction.Function(
                    identifier = "",
                    operation = null,
                    operands = listOf(groups.getOrNull(2) ?: "")
            )
            InstructionRegExp.FUNCTION_OPERATION -> Instruction.Function(
                    identifier = groups.getOrNull(1) ?: "ERROR",
                    operation = operationFrom(groups.getOrNull(3) ?: ""),
                    operands = listOf(groups.getOrNull(2) ?: "", groups.getOrNull(4) ?: "")
            )
            else -> throw java.lang.UnsupportedOperationException("Not done yet")
        }

        println(res)
        return Instruction.PrintVars
    }

    private fun operationFrom(str: String): Instruction.Function.Operation {
        return when (str) {
            "+" -> Instruction.Function.Operation.ADDITION
            "-" -> Instruction.Function.Operation.SUBTRACTION
            "/" -> Instruction.Function.Operation.DIVISION
            "*" -> Instruction.Function.Operation.MULTIPLICATION
            else -> throw java.lang.IllegalStateException()
        }
    }

}

fun main() {
    val parser = InstructionParser()
    parser.parse("var qwer_t3y")
    parser.parse("let qwerty = -99")
    parser.parse("let q2_werty = asd")
    parser.parse("fn qwerty = fggg")
    parser.parse("fn qwerty = fggg + sweet")
}

sealed interface Instruction {

    data class Variable(
            val identifier: String,
            val value: Double?,
            val type: Type
    ) : Instruction {
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

    class Print(val identifier: String) : Instruction
    object PrintVars : Instruction
    object PrintFns : Instruction
}