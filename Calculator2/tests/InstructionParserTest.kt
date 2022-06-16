import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

internal class InstructionParserTest {

    private val parser = InstructionParser()

    @Test
    fun `identifier should not starts with number and must contains only latin letters and underscores`() {
        val instructionValid = "var QWERTY12_qwerty12"
        Assertions.assertDoesNotThrow {
            parser.parse(instructionValid)
        }
        val invalidInstructions = listOf(
                "var 12qwerty",
                "var qwert%",
                "var _qwerty"
        )
        invalidInstructions.forEach {
            assertThrows<Throwable> {
                parser.parse(it)
            }
        }
    }


    @Test
    fun `parsing var identifier`() {
        val instruction = "var x"
        val expected = Variable(
                identifier = "x",
                value = null,
                type = Variable.Type.DECLARATION
        )
        assertEquals(expected, parser.parse(instruction))
    }

    @Test
    fun `parsing let identifier = identifier`() {
        val instruction = "let qwerty_12=some_other_variable"
        val expected = Variable(
                identifier = "qwerty_12",
                value = Variable.Value.Identifier("some_other_variable"),
                type = Variable.Type.ASSIGNMENT
        )
        assertEquals(expected, parser.parse(instruction))
    }

    @Test
    fun `parsing let identifier = number`() {
        val instruction = "let qwerty_12=-128.345"
        val expected = Variable(
                identifier = "qwerty_12",
                value = Variable.Value.Number(-128.345),
                type = Variable.Type.ASSIGNMENT
        )
        assertEquals(expected, parser.parse(instruction))
    }

    @Test
    fun `parsing fn identifier = identifier`() {
        val instruction = "fn FN_13=qwerty"
        val expected = Function(
                identifier = "FN_13",
                operands = listOf("qwerty"),
                operation = null
        )
        assertEquals(expected, parser.parse(instruction))
    }

    @Test
    fun `parsing fn identifier = identifier operation identifier`() {
        val instruction = "fn FN_13=qwerty/some_variable"
        val expected = Function(
                identifier = "FN_13",
                operands = listOf("qwerty", "some_variable"),
                operation = Function.Operation.DIVISION
        )
        assertEquals(expected, parser.parse(instruction))
    }

    @Test
    fun `parsing print identifier`() {
        val instruction = "print qwerty_12"
        val expected = Print(identifier = "qwerty_12")
        assertEquals(expected, parser.parse(instruction))
    }

    @Test
    fun `parsing printvars`() {
        val instruction = "printvars"
        val expected = PrintVars
        assertEquals(expected, parser.parse(instruction))
    }

    @Test
    fun `parsing printfns`() {
        val instruction = "printfns"
        val expected = PrintFns
        assertEquals(expected, parser.parse(instruction))
    }

}