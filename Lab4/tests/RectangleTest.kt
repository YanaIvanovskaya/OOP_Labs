import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import shape.Rectangle
import java.awt.Color

internal class RectangleTest {

    private val testShape = Rectangle(
        leftTop = Point(20.0, 40.0),
        width = 80.0,
        height = 19.0,
        outlineColor = Color.BLUE,
        fillColor = Color.GREEN
    )
    private val mockCanvas = MockCanvas()

    @Test
    @DisplayName("Вычисление периметра")
    fun case_1() {
        val expected = 2 * 80.0 + 2 * 19.0
        Assertions.assertEquals(expected, testShape.getPerimeter())
    }

    @Test
    @DisplayName("Вычисление площади")
    fun case_2() {
        val expected = 80.0 * 19.0
        Assertions.assertEquals(expected, testShape.getArea())
    }

    @Test
    @DisplayName("Рисование")
    fun case_3() {
        testShape.draw(mockCanvas)
        val points = mutableListOf<Point>()
        val rightTop = Point(20.0 + 80.0, 40.0)
        val leftBottom = Point(20.0, 40.0 - 19.0)
        val rightBottom = Point(20.0 + 80.0, 40.0 - 19.0)
        points.add(Point(20.0, 40.0))
        points.add(rightTop)
        points.add(rightBottom)
        points.add(leftBottom)
        val expected = listOf(
            "fillPolygon points=$points fillColor=${Color.GREEN}",
            "drawPolygon points=$points outlineColor=${Color.BLUE}"
        )
        Assertions.assertEquals(expected, mockCanvas.getDrawingResult())
    }

}