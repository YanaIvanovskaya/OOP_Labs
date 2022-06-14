import java.text.DecimalFormat

typealias FunctionDataMap = MutableMap<String, Function.Operation>

class Calculator {

    private val mValidator = InstructionValidator()
    private val mDecimalFormatter = DecimalFormat("#.##")

    private val mInstructions = mutableListOf<Instruction>()
    private val mVariables = mutableMapOf<String, Variable.Value?>()
    private val mFunctions = mutableListOf<Function>()
    private val mFunctionData: MutableMap<String, FunctionDataMap> = mutableMapOf()

    private val String.isVariable
        get() = mVariables.keys.contains(this)

    private val String.isFunction
        get() = mFunctionData.keys.contains(this)

    @Throws(CalculatorException::class)
    fun calculate(instruction: Instruction): OutputData? {
        mValidator.ensureValidInstruction(instruction, mInstructions)
        mInstructions.add(instruction)

        return when (instruction) {
            is Print -> print(instruction.identifier)
            is PrintFns -> printFns()
            is PrintVars -> printVars()
            is Variable -> calculateVariable(instruction)
            is Function -> calculateFunction(instruction)
        }
    }

    private fun calculateFunction(instruction: Function): OutputData? {
        val data: FunctionDataMap = mutableMapOf()

        mFunctionData[instruction.identifier] = when (instruction.operation) {
            null -> {
                val operand = instruction.operands[0]
                when {
                    operand.isFunction -> mFunctionData[operand] ?: mutableMapOf()
                    operand.isVariable -> data.apply { put(operand, Function.Operation.ADDITION) }
                    else -> data
                }
            }
            else -> {
                val firstOperand = instruction.operands[0]
                val secondOperand = instruction.operands[1]

                when {
                    firstOperand.isFunction && secondOperand.isFunction -> data.apply {
                        put(firstOperand, instruction.operation)
                        put(secondOperand, instruction.operation)
                    }
                    firstOperand.isVariable && secondOperand.isVariable -> data.apply {
                        put(firstOperand, Function.Operation.ADDITION)
                        put(secondOperand, instruction.operation)
                    }
                    firstOperand.isFunction && secondOperand.isVariable -> data.apply {
                        put(firstOperand, instruction.operation)
                        put(secondOperand, instruction.operation)
                    }
                    firstOperand.isVariable && secondOperand.isFunction -> data.apply {
                        put(firstOperand, Function.Operation.ADDITION)
                        put(secondOperand, instruction.operation)
                    }
                    else -> data
                }
            }
        }
        mFunctions.add(instruction)
//        println(mFunctions)
//        println(mFunctionData)
        return null
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
//        println(mVariables)
        return null
    }

    private fun print(identifier: String): OutputData {
        return when {
            identifier.isVariable -> OutputData(getVariableValue(identifier)?.let { toString() } ?: NAN)
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
                "${it.key} : ${getVariableValue(it.key)}\n"
            }.joinToString(""))
        }
    }

    private fun getFunctionValue(identifier: String): Double? {
        val data = mFunctionData[identifier] ?: return null
        var value = 0.0

        fun calc(operand: Double, operation: Function.Operation) {
            when (operation) {
                Function.Operation.ADDITION -> value += operand
                Function.Operation.SUBTRACTION -> value -= operand
                Function.Operation.DIVISION -> value /= operand
                Function.Operation.MULTIPLICATION -> value *= operand
            }
        }

        for (entry in data) {
            val name = entry.key
            val operation = entry.value
            val operand = when {
                name.isVariable -> getVariableValue(name)
                name.isFunction -> getFunctionValue(name)
                else -> null
            }
            operand?.let { calc(it, operation) } ?: return null
        }

        return value
    }

    private fun getValue(identifier: String): Double? {
        return when {
            identifier.isFunction -> getFunctionValue(identifier)
            identifier.isVariable -> getVariableValue(identifier)
            else -> null
        }
    }

    private fun evaluateFunction(function: Function): OutputData {
        val result = when (function.operation) {
            null -> {
                val operand = function.operands[0]
                getValue(operand)
            }
            else -> {
                val firstOperand = getValue(function.operands[0])
                val secondOperand = getValue(function.operands[1])

                if (firstOperand == null || secondOperand == null) {
                    return OutputData(NAN)
                }
                when (function.operation) {
                    Function.Operation.ADDITION -> firstOperand + secondOperand
                    Function.Operation.DIVISION -> firstOperand / secondOperand
                    Function.Operation.MULTIPLICATION -> firstOperand * secondOperand
                    Function.Operation.SUBTRACTION -> firstOperand - secondOperand
                }
            }
        }
        return OutputData(result?.let { mDecimalFormatter.format(it) } ?: NAN)
    }

    private fun getVariableValue(identifier: String): Double? {
        return when (val value = mVariables[identifier]) {
            is Variable.Value.Number -> value.value
            else -> null
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