class InstructionParser {

    @Throws(ParserException::class)
    fun parse(instruction: String): Instruction {
        val matchResult = findMatch(instruction)
        val instructionRegExp = matchResult.first
        val groups = matchResult.second?.groupValues
                ?: throw ParserException(ParserErrorType.UNKNOWN_INSTRUCTION)

        return when (instructionRegExp) {
            InstructionRegExp.VARIABLE_ASSIGNMENT -> {
                val value = groups.getOrThrow(2)
                Variable(
                        identifier = groups.getOrThrow(1),
                        type = Variable.Type.ASSIGNMENT,
                        value = value.runCatching {
                            Variable.Value.Number(this.toDouble())
                        }.getOrElse { Variable.Value.Identifier(value) }
                )
            }
            InstructionRegExp.VARIABLE_DECLARATION -> Variable(
                    identifier = groups.getOrThrow(1),
                    type = Variable.Type.DECLARATION,
                    value = null
            )
            InstructionRegExp.FUNCTION_ASSIGNMENT -> Function(
                    identifier = groups.getOrThrow(1),
                    operation = null,
                    operands = listOf(groups.getOrThrow(2))
            )
            InstructionRegExp.FUNCTION_OPERATION -> Function(
                    identifier = groups.getOrThrow(1),
                    operation = operationFrom(groups.getOrThrow(3)),
                    operands = listOf(groups.getOrThrow(2), groups.getOrThrow(4))
            )
            InstructionRegExp.PRINT -> Print(
                    identifier = groups.getOrThrow(1)
            )
            InstructionRegExp.PRINTVARS -> PrintVars
            InstructionRegExp.PRINTFNS -> PrintFns
            else -> throw ParserException(ParserErrorType.UNKNOWN_INSTRUCTION)
        }
    }

    @Throws(ParserException::class)
    private fun operationFrom(str: String): Function.Operation {
        return when (str) {
            "+" -> Function.Operation.ADDITION
            "-" -> Function.Operation.SUBTRACTION
            "/" -> Function.Operation.DIVISION
            "*" -> Function.Operation.MULTIPLICATION
            else -> throw ParserException(ParserErrorType.UNSUPPORTED_OPERATION)
        }
    }

    private fun findMatch(instruction: String): Pair<InstructionRegExp?, MatchResult?> {
        var matchResult: MatchResult? = null
        var instructionRegExp: InstructionRegExp? = null

        InstructionRegExp.values().forEach { regExp ->
            regExp.regex.toRegex().matchEntire(instruction).also { result ->
                if (result != null) {
                    matchResult = result
                    instructionRegExp = regExp
                    return@forEach
                }
            }
        }
        return instructionRegExp to matchResult
    }

    private enum class InstructionRegExp(val regex: String) {
        VARIABLE_ASSIGNMENT("let ${RegExp.IDENTIFIER} = (${RegExp.IDENTIFIER}|${RegExp.NUMBER})"),
        VARIABLE_DECLARATION("var ${RegExp.IDENTIFIER}"),
        FUNCTION_OPERATION("fn ${RegExp.IDENTIFIER} = ${RegExp.IDENTIFIER} ${RegExp.OPERATION} ${RegExp.IDENTIFIER}"),
        FUNCTION_ASSIGNMENT("fn ${RegExp.IDENTIFIER} = ${RegExp.IDENTIFIER}"),
        PRINT("print ${RegExp.IDENTIFIER}"),
        PRINTVARS("printvars"),
        PRINTFNS("printfns");

        private enum class RegExp(val regex: Regex) {
            IDENTIFIER("""([a-zA-Z][\w_]+|[a-zA-Z])""".toRegex()),
            NUMBER("""(-?\d+(\.\d+)?)""".toRegex()),
            OPERATION("""([+-/*])""".toRegex());

            override fun toString() = regex.pattern
        }

    }

    private fun List<String>.getOrThrow(index: Int): String {
        return getOrNull(index) ?: throw ParserException(ParserErrorType.UNKNOWN_INSTRUCTION)
    }

}