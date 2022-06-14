import java.util.*

class CalculatorController(
        private val calculator: Calculator,
        private val parser: InstructionParser
) {

    fun calculate(instruction: String) {
        try {
            val result = calculator.calculate(parser.parse(instruction))
            if (result != null) {
                println(result.value)
            }
        } catch (ex: ParserException) {
            println(when (ex.errorType) {
                ParserErrorType.UNSUPPORTED_OPERATION -> "Unsupported operation"
                ParserErrorType.UNKNOWN_INSTRUCTION -> "Unknown instruction"
            })
        } catch (ex: CalculatorException) {
            println(when (ex.errorType) {
                CalculatorErrorType.IDENTIFIER_ALREADY_EXIST -> "Identifier already exist"
                CalculatorErrorType.IDENTIFIER_DOES_NOT_EXIST -> "Identifier does not exist"
                CalculatorErrorType.KEYWORD_AS_IDENTIFIER -> "Keyword cannot use as identifier"
            })
        }
    }

    fun start() {
        Scanner(System.`in`).use {
            println("Enter instructions to calculate:")
            while (it.hasNextLine()) {
                calculate(it.nextLine())
            }
        }
    }

}