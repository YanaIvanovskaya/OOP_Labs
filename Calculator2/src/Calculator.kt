import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

data class FunctionData(
        val identifier: String,
        val operation: Function.Operation,
        val repeatCount: Int = 1
)

typealias FunctionDataMap = MutableMap<String, MutableList<FunctionData>>

private fun MutableList<FunctionData>.addIfNotSame(data: FunctionData) {
    when {
        isEmpty() -> add(data)
        else -> {
            val lastElement = last()
            val isLastElementTheSame = lastElement.let {
                it.identifier == data.identifier && it.operation == data.operation
            }
            if (isLastElementTheSame) {
                this[lastIndex] = lastElement.copy(repeatCount = lastElement.repeatCount + 1)
            } else {
                add(data)
            }
        }
    }
}

class Calculator {

    private val mValidator = InstructionValidator()
    private val mDecimalFormatter = DecimalFormat("#.##", DecimalFormatSymbols(Locale.ENGLISH))

    private val mInstructions = mutableListOf<Instruction>()
    private val mVariables = mutableMapOf<String, Variable.Value?>()
    private val mFunctions = mutableListOf<Function>()
    private val mFunctionData: FunctionDataMap = mutableMapOf()

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
        val data = mutableListOf<FunctionData>()

        mFunctionData[instruction.identifier] = when (instruction.operation) {
            null -> {
                val operand = instruction.operands[0]
                when {
                    operand.isFunction -> mFunctionData[operand] ?: data
                    operand.isVariable -> data.apply { add(FunctionData(operand, Function.Operation.ADDITION)) }
                    else -> data
                }
            }
            else -> {
                val firstOperand = instruction.operands[0]
                val secondOperand = instruction.operands[1]

                when {
                    firstOperand.isFunction && secondOperand.isFunction -> data.apply {
                        mFunctionData[firstOperand]?.forEach(::add)
                        addIfNotSame(FunctionData(secondOperand, instruction.operation))
                    }
                    firstOperand.isVariable && secondOperand.isVariable -> data.apply {
                        add(FunctionData(firstOperand, Function.Operation.ADDITION))
                        addIfNotSame(FunctionData(secondOperand, instruction.operation))
                    }
                    firstOperand.isFunction && secondOperand.isVariable -> data.apply {
                        mFunctionData[firstOperand]?.forEach(::add)
                        addIfNotSame(FunctionData(secondOperand, instruction.operation))
                    }
                    firstOperand.isVariable && secondOperand.isFunction -> data.apply {
                        add(FunctionData(firstOperand, Function.Operation.ADDITION))
                        addIfNotSame(FunctionData(secondOperand, instruction.operation))
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
                var identifier = instruction.value.value
                mVariables[instruction.identifier] = when {
                    identifier.isVariable -> {
                        while (mVariables[identifier] is Variable.Value.Identifier) {
                            identifier = (mVariables[identifier] as Variable.Value.Identifier).value
                        }
                        mVariables[identifier]
                    }
                    identifier.isFunction -> getFunctionValue(identifier)?.let {
                        Variable.Value.Number(it)
                    }
                    else -> null
                }
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
            else -> OutputData(mFunctions.sortedByDescending { it.identifier }
                    .reversed()
                    .joinToString("") {
                        "${it.identifier} : ${evaluateFunction(it).value}\n"
                    })
        }
    }

    private fun printVars(): OutputData? {
        return when {
            mVariables.isEmpty() -> null
            else -> OutputData(mVariables.keys.sortedDescending()
                    .reversed()
                    .joinToString("") {
                        "$it : ${mDecimalFormatter.format(getVariableValue(it))}\n"
                    })
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

        for (elem in data) {
            val name = elem.identifier
            val operation = elem.operation
            val operand = when {
                name.isVariable -> getVariableValue(name)
                name.isFunction -> getFunctionValue(name)
                else -> null
            }
            operand ?: return null

            repeat(elem.repeatCount) {
                calc(operand, operation)
            }
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
        mFunctions.clear()
        mVariables.clear()
        mFunctionData.clear()
    }

    data class OutputData(val value: String)

    companion object {
        private const val NAN = "NaN"
    }

}