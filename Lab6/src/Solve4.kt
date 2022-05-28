import java.lang.Math.pow
import kotlin.math.pow

data class EquationRoot4(
        val a: Double,
        val b: Double,
        val c: Double,
        val d: Double,
        val e: Double
)

fun solve4(a: Double, b: Double, c: Double, d: Double, e: Double): EquationRoot4 {

    val x = 1.0


    val equation = a * x.pow(4) + b * x.pow(3) + c * x.pow(2) + d * x + e


    val y = 1.0

    val equation3 = y.pow(3) - (b * y.pow(2)) + (a * c - 4 * d) * y - (a.pow(2) * d) + 4*b*d - c.pow(2)


    return EquationRoot4(0.0, 0.0, 0.0, 0.0, 0.0)
}

