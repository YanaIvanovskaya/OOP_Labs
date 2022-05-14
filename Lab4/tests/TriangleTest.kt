import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import shape.Triangle
import java.awt.Color
import kotlin.math.abs
import kotlin.math.sqrt

internal class TriangleTest {

    private val testShape = Triangle(
        vertex1 = Point(12.0, 50.0),
        vertex2 = Point(10.0, 7.0),
        vertex3 = Point(120.0, 30.0),
        outlineColor = Color.RED,
        fillColor = Color.GREEN
    )
    private val mockCanvas = MockCanvas()

    @Test
    @DisplayName("Вычисление периметра")
    fun case_1() {
        val vector12 = Point(10.0 - 12.0, 7.0 - 50.0)
        val vector23 = Point(120.0 - 10.0, 30.0 - 7.0)
        val vector31 = Point(12.0 - 120.0, 50.0 - 30.0)

        val len12 = sqrt(vector12.x * vector12.x + vector12.y * vector12.y)
        val len23 = sqrt(vector23.x * vector23.x + vector23.y * vector23.y)
        val len31 = sqrt(vector31.x * vector31.x + vector31.y * vector31.y)

        val expected = len12 + len23 + len31

        Assertions.assertEquals(expected, testShape.getPerimeter())
    }

    @Test
    @DisplayName("Вычисление площади")
    fun case_2() {
        val expected = 0.5 * abs(
            (10.0 - 12.0) * (30.0 - 50.0) -
                    (120.0 - 12.0) * (7.0 - 50.0)
        )
        Assertions.assertEquals(expected, testShape.getArea())
    }

    @Test
    @DisplayName("Рисование")
    fun case_3() {
        testShape.draw(mockCanvas)
        val points = mutableListOf<Point>()
        points.add(Point(12.0, 50.0))
        points.add(Point(10.0, 7.0))
        points.add(Point(120.0, 30.0))
        val expected = listOf(
            "fillPolygon points=$points fillColor=${Color.GREEN}",
            "drawPolygon points=$points outlineColor=${Color.RED}"
        )
        Assertions.assertEquals(expected, mockCanvas.getDrawingResult())
    }
}