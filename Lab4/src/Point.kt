import kotlin.math.pow
import kotlin.math.sqrt

data class Point(
        val x: Double,
        val y: Double
)

fun Point.distanceTo(other: Point): Double {
    return sqrt((x - other.x).pow(2) + (y - other.y).pow(2))
}
