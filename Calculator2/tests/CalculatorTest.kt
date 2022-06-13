import org.junit.jupiter.api.*
import kotlin.test.assertEquals

internal class CalculatorTest {

    private val calculator = Calculator()

    @BeforeEach
    fun setUp() {
        calculator.clearMemory()
    }

    @Test
    fun `must throw exception when declare variable with existing name`() {
        val instruction1 = Variable(
                identifier = "qwerty",
                type = Variable.Type.ASSIGNMENT,
                value = Variable.Value.Number(0.5)
        )
        val instruction2 = Function(
                identifier = "qwerty3",
                operation = null,
                operands = listOf("qwerty")
        )
        Assertions.assertDoesNotThrow {
            calculator.calculate(instruction1)
            calculator.calculate(instruction2)
        }
        assertThrows<CalculatorException> {
            calculator.calculate(Variable(
                    identifier = "qwerty3",
                    value = Variable.Value.Number(-93.0),
                    type = Variable.Type.ASSIGNMENT
            ))
        }
    }

    @Test
    fun `must throw exception when assign variable value from undeclared variable`() {
        val instruction = Variable(
                identifier = "qwerty",
                type = Variable.Type.ASSIGNMENT,
                value = Variable.Value.Identifier("qwe")
        )

        assertThrows<CalculatorException> {
            calculator.calculate(instruction)
        }
    }

    @Test
    fun `must throw exception when declare function with existing name`() {
        val instruction1 = Variable(
                identifier = "asd",
                type = Variable.Type.DECLARATION,
                value = null
        )
        val instruction2 = Function(
                identifier = "qwerty",
                operands = listOf("asd"),
                operation = null
        )

        Assertions.assertDoesNotThrow {
            calculator.calculate(instruction1)
            calculator.calculate(instruction2)
        }
        assertThrows<CalculatorException> {
            calculator.calculate(instruction2)
        }
    }

    @Test
    fun `must throw exception when declare function with operands - undeclared variables`() {
        val instruction = Function(
                identifier = "qwerty",
                operands = listOf("asd"),
                operation = null
        )

        assertThrows<CalculatorException> {
            calculator.calculate(instruction)
        }
    }

    @Test
    fun `must throw exception when print unknown identifier`() {
        val instruction = Print(
                identifier = "qwerty"
        )

        assertThrows<CalculatorException> {
            calculator.calculate(instruction)
        }
    }

    @Test
    fun `must throw exception when identifier is keyword`() {
        val instruction1 = Variable(
                identifier = "val",
                value = null,
                type = Variable.Type.DECLARATION
        )
        val instruction2 = Variable(
                identifier = "let",
                value = null,
                type = Variable.Type.DECLARATION
        )

        assertThrows<CalculatorException> {
            calculator.calculate(instruction1)
            calculator.calculate(instruction2)
        }
    }

    @Test
    @DisplayName("присвоение переменной значения другой переменной не должно приводить к изменению" +
            " ее значения при изменении значения присвоенной переменной")
    fun `assignment to other variable value should not lead to react to its changes`() {
        /*
        let a = 100
        let b = 50
        let v = 20
        let a = b
        let b = v
        print a
        50
        print b
        20
        */
        calculator.calculate(Variable(
                identifier = "a",
                value = Variable.Value.Number(100.0),
                type = Variable.Type.ASSIGNMENT
        ))
        calculator.calculate(Variable(
                identifier = "b",
                value = Variable.Value.Number(50.0),
                type = Variable.Type.ASSIGNMENT
        ))
        calculator.calculate(Variable(
                identifier = "v",
                value = Variable.Value.Number(20.0),
                type = Variable.Type.ASSIGNMENT
        ))
        calculator.calculate(Variable(
                identifier = "a",
                type = Variable.Type.ASSIGNMENT,
                value = Variable.Value.Identifier("b")
        ))
        calculator.calculate(Variable(
                identifier = "b",
                type = Variable.Type.ASSIGNMENT,
                value = Variable.Value.Identifier("v")
        ))
        assertEquals(Calculator.OutputData("50"), calculator.calculate(Print("a")))
        assertEquals(Calculator.OutputData("20"), calculator.calculate(Print("b")))
    }

}