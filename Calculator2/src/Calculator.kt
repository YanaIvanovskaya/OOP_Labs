import java.text.DecimalFormat

class Calculator {

    private val mValidator = InstructionValidator()
    private val mDecimalFormatter = DecimalFormat("#.##")

    private val mInstructions = mutableListOf<Instruction>()
    private val mVariables = mutableMapOf<String, Variable.Value?>()
    private val mFunctions = mutableListOf<Function>()

    @Throws(CalculatorException::class)
    fun calculate(instruction: Instruction): OutputData? {
        mValidator.ensureValidInstruction(instruction, mInstructions)
        mInstructions.add(instruction)

        return when (instruction) {
            is Print -> print(instruction.identifier)
            is PrintFns -> printFns()
            is PrintVars -> printVars()
            is Variable -> calculateVariable(instruction)
            is Function -> {
                mFunctions.add(instruction)
                println(mFunctions)
                null
            }
        }
    }

    private fun calculateVariable(instruction: Variable): OutputData? {
        when (instruction.value) {
            is Variable.Value.Identifier -> {
                var variable = instruction.value.value
                while (mVariables[variable] is Variable.Value.Identifier) {
                    variable = (mVariables[variable] as Variable.Value.Identifier).value
                }
                mVariables[instruction.identifier] = mVariables[variable]
            }
            else -> mVariables[instruction.identifier] = instruction.value
        }
        println(mVariables)
        return null
    }

    private fun print(identifier: String): OutputData {
        return when {
            mVariables.containsKey(identifier) -> OutputData(getVariableValueAsString(identifier))
            else -> {
                val function = mFunctions.find { it.identifier == identifier }
                        ?: throw java.lang.IllegalStateException()
                evaluateFunction(function)
            }
        }
    }

    private fun printFns(): OutputData? {
        return when {
            mFunctions.isEmpty() -> null
            else -> OutputData(mFunctions.joinToString("") {
                "${it.identifier} : ${evaluateFunction(it).value}\n"
            })
        }
    }

    private fun printVars(): OutputData? {
        return when {
            mVariables.isEmpty() -> null
            else -> OutputData(mVariables.map {
                "${it.key} : ${getVariableValueAsString(it.key)}\n"
            }.joinToString(""))
        }
    }

    // добавить случай когда идентификатором является другая ыункция
    private fun evaluateFunction(function: Function): OutputData {
        return OutputData(when (function.operation) {
            null -> getVariableValueAsString(function.operands[0])
            else -> {
                val firstOperand = (mVariables[function.operands[0]] as? Variable.Value.Number)?.value
                val secondOperand = (mVariables[function.operands[1]] as? Variable.Value.Number)?.value
                if (firstOperand == null || secondOperand == null) {
                    return OutputData(NAN)
                }
                val result = when (function.operation) {
                    Function.Operation.ADDITION -> firstOperand + secondOperand
                    Function.Operation.DIVISION -> firstOperand / secondOperand
                    Function.Operation.MULTIPLICATION -> firstOperand * secondOperand
                    Function.Operation.SUBTRACTION -> firstOperand - secondOperand
                }
                mDecimalFormatter.format(result)
            }
        })
    }

    private fun getVariableValueAsString(identifier: String): String {
        return when (val value = mVariables[identifier]) {
            is Variable.Value.Number -> mDecimalFormatter.format(value.value)
            else -> NAN
        }
    }

    fun clearMemory() {
        mInstructions.clear()
    }

    data class OutputData(val value: String)

    companion object {
        private const val NAN = "NaN"
    }

}