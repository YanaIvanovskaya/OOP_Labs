class InstructionValidator {

    @Throws(CalculatorException::class)
    fun ensureValidInstruction(instruction: Instruction, instructions: List<Instruction>) {
        ensureSemantic(instruction)
        when (instruction) {
            is Variable -> ensureValidVariable(instruction, instructions)
            is Function -> ensureValidFunction(instruction, instructions)
            is Print -> ensureIdentifierExist(instruction.identifier, instructions)
            else -> Unit
        }
    }

    private fun ensureValidVariable(instruction: Variable, instructions: List<Instruction>) {
        when (instruction.type) {
            Variable.Type.DECLARATION -> {
                ensureIdentifierDoesNotExist(instruction.identifier, instructions)
            }
            Variable.Type.ASSIGNMENT -> {
                ensureIdentifierDoesNotDeclaredAsFunction(instruction.identifier, instructions)
                if (instruction.value is Variable.Value.Identifier) {
                    ensureIdentifierExist(instruction.value.value, instructions)
                }
            }
        }
    }

    private fun ensureValidFunction(instruction: Function, instructions: List<Instruction>) {
        ensureIdentifierDoesNotExist(instruction.identifier, instructions)
        instruction.operands.forEach {
            ensureIdentifierExist(it, instructions)
        }
    }

    private fun ensureSemantic(instruction: Instruction) {
        val invalidIdentifiers = """(val|let|fn)""".toRegex()
        val hasInvalidIdentifier = when (instruction) {
            is Variable -> instruction.identifier.matches(invalidIdentifiers)
            is Function -> instruction.identifier.matches(invalidIdentifiers)
            else -> false
        }
        if (hasInvalidIdentifier) {
            throw CalculatorException(CalculatorErrorType.KEYWORD_AS_IDENTIFIER)
        }
    }

    private fun ensureIdentifierExist(identifier: String, instructions: List<Instruction>) {
        val existedIdentifiers = instructions.mapNotNull {
            when (it) {
                is Variable -> it.identifier
                is Function -> it.identifier
                else -> null
            }
        }.filter { it == identifier }
        if (existedIdentifiers.isEmpty()) {
            throw CalculatorException(CalculatorErrorType.IDENTIFIER_DOES_NOT_EXIST)
        }
    }

    private fun ensureIdentifierDoesNotExist(identifier: String, instructions: List<Instruction>) {
        val existedIdentifiers = instructions.mapNotNull {
            when (it) {
                is Variable -> it.identifier
                is Function -> it.identifier
                else -> null
            }
        }.filter { it == identifier }
        if (existedIdentifiers.isNotEmpty()) {
            throw CalculatorException(CalculatorErrorType.IDENTIFIER_ALREADY_EXIST)
        }
    }

    private fun ensureIdentifierDoesNotDeclaredAsFunction(identifier: String, instructions: List<Instruction>) {
        val existedIdentifiers = instructions.mapNotNull {
            when (it) {
                is Variable -> null
                is Function -> it.identifier
                else -> null
            }
        }.filter { it == identifier }
        if (existedIdentifiers.isNotEmpty()) {
            throw CalculatorException(CalculatorErrorType.IDENTIFIER_ALREADY_EXIST)
        }
    }

}