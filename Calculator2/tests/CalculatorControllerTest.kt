import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.time.Duration
import kotlin.test.assertEquals
import kotlin.test.fail

internal class CalculatorControllerTest {

    private val mController = CalculatorController(
            calculator = Calculator(),
            parser = InstructionParser()
    )
    private val mOutputStream = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        mOutputStream.reset()
        mController.resetAll()
        System.setOut(PrintStream(mOutputStream))
    }

    companion object {
        @AfterAll
        fun tearDown() {
            System.setOut(System.out)
        }
    }

    @Test
    fun `variable declaration and assignment`() {
        mController.calculate("var x")
        mController.calculate("print x")

        assertEquals("NaN", mOutputStream.toString().trim())
        mOutputStream.reset()

        mController.calculate("let x=42")
        mController.calculate("print x")

        assertEquals("42", mOutputStream.toString().trim())
        mOutputStream.reset()

        mController.calculate("let x=1.234")
        mController.calculate("print x")

        assertEquals("1.23", mOutputStream.toString().trim())
        mOutputStream.reset()

        mController.calculate("let y=x")
        mController.calculate("let x=99")
        mController.calculate("printvars")

        assertEquals("x : 99\ny : 1.23", mOutputStream.toString().trim())
    }

    @Test
    fun `function declaration`() {
        mController.calculate("var x")
        mController.calculate("var y")
        mController.calculate("fn XPlusY=x+y")
        mController.calculate("print XPlusY")

        assertEquals("NaN", mOutputStream.toString().trim())
        mOutputStream.reset()

        mController.calculate("let x=3")
        mController.calculate("let y=4")
        mController.calculate("print XPlusY")

        assertEquals("7", mOutputStream.toString().trim())
        mOutputStream.reset()

        mController.calculate("let x=10")
        mController.calculate("print XPlusY")

        assertEquals("14", mOutputStream.toString().trim())
        mOutputStream.reset()

        mController.calculate("let z=3.5")
        mController.calculate("fn XPlusYDivZ=XPlusY/z")
        mController.calculate("printfns")

        assertEquals("XPlusY : 14\nXPlusYDivZ : 4", mOutputStream.toString().trim())
    }

    @Test
    fun `calculating the area of a circle`() {
        mController.calculate("var radius")
        mController.calculate("let pi=3.14159265")
        mController.calculate("fn radiusSquared=radius*radius")
        mController.calculate("fn circleArea=pi*radiusSquared")
        mController.calculate("let radius=10")
        mController.calculate("print circleArea")

        assertEquals("314.16", mOutputStream.toString().trim())
        mOutputStream.reset()

        mController.calculate("let circle10Area=circleArea")
        mController.calculate("let radius=20")
        mController.calculate("let circle20Area=circleArea")
        mController.calculate("printfns")

        assertEquals("circleArea : 1256.64\nradiusSquared : 400", mOutputStream.toString().trim())
        mOutputStream.reset()

        mController.calculate("printvars")

        assertEquals("circle10Area : 314.16\ncircle20Area : 1256.64\npi : 3.14\nradius : 20", mOutputStream.toString().trim())
    }

    @Test
    fun `calculating fibonacci sequence`() {
        val fib = listOf(
                "let v0=0",
                "let v1=1",
                "fn fib0=v0",
                "fn fib1=v1",
                "fn fib2=fib1+fib0",
                "fn fib3=fib2+fib1",
                "fn fib4=fib3+fib2",
                "fn fib5=fib4+fib3",
                "fn fib6=fib5+fib4",
                "print fib6"
        )
        fib.forEach { mController.calculate(it) }

        assertEquals("8", mOutputStream.toString().trim())
    }

    //    @Test
    fun `calculating fibonacci sequence 2`() {
        // 10 -> Time: 0.04     Time: 0.041
        // 20 -> Time: 0.056    Time: 0.047 sec
        // 30 -> Time: 0.191    Time: 0.163 sec
        // 40 -> Time: 5.94     Time: 4.028 sec
        // 42 -> Time: 15.118   Time: 10.109 sec
        // 45                   Time: 44.023 sec
        // 48                   Time: 185.783
        // 50 -> Time: 749.828
        val nesting = 40
        val expected = when (nesting) {
            10 -> 55
            20 -> 6765
            30 -> 832040
            40 -> 102334155
            50 -> 12586269025
            else -> 0L
        }
        val data = mutableListOf(
                "let v0=0",
                "let v1=1",
                "fn fib0=v0",
                "fn fib1=v1"
        )
        for (i in 2..nesting) {
            data.add("fn fib$i=fib${i - 1}+fib${i - 2}")
        }
        data.add("print fib$nesting")

        data.forEach { mController.calculate(it) }

        Assertions.assertTimeout(Duration.ofSeconds(30)) {
            val time = System.currentTimeMillis()
            Assertions.assertDoesNotThrow {
                data.forEach { mController.calculate(it) }
                //fail("Time: ${(System.currentTimeMillis() - time) / 1000.0} sec")
            }
        }
        assertEquals(expected.toString(), mOutputStream.toString().trim())
    }

    //    @Test
    fun `calculating nested functions`() {
        // 10000 Time: 1.905 sec
        // 20000 Time: 7.173 sec
        // 30000 Time: 20.983 sec
        // 40000 Time: 46.313 sec
        // 50000 Time: 83.117 sec
        // 60000 Time: 139.151 sec

        val nesting = 20000
        val data = mutableListOf(
                "let x=1",
                "let x1=1"
        )
        for (i in 1..nesting) {
            data.add("fn x${i + 1}=x$i+x")
        }
        data.add("print x$nesting")

        Assertions.assertTimeout(Duration.ofMinutes(3)) {
            val time = System.currentTimeMillis()
            Assertions.assertDoesNotThrow {
                data.forEach {
                    mController.calculate(it)
                }
                assertEquals("$nesting", mOutputStream.toString().trim())
            }
            fail("Time: ${(System.currentTimeMillis() - time) / 1000.0} sec")
        }
    }

}