import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import shape.LineSegment
import java.awt.Color
import kotlin.math.pow
import kotlin.math.sqrt

internal class LineSegmentTest {

    private val testShape = LineSegment(
        start = Point(20.0, 40.5),
        end = Point(120.0, 400.0),
        outlineColor = Color.BLUE
    )

    @Test
    @DisplayName("Вычисление периметра")
    fun case_1() {
        val expected = sqrt((20.0 - 120.0).pow(2) + (40.5 - 400.0).pow(2))
        Assertions.assertEquals(expected, testShape.getPerimeter())
    }

    @Test
    @DisplayName("Вычисление площади")
    fun case_2() {
        val expected = sqrt((20.0 - 120.0).pow(2) + (40.5 - 400.0).pow(2))
        Assertions.assertEquals(expected, testShape.getArea())
    }

}