import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import shape.Circle
import shape.Rectangle
import java.awt.Color

internal class MainKtTest {

    private val testShapes = listOf<IShape>(
        Circle(
            radius = 10.0,
            center = Point(12.0, 30.0),
            outlineColor = Color.GRAY,
            fillColor = Color.CYAN
        ),
        Rectangle(
            width = 12.0,
            height = 90.0,
            leftTop = Point(70.0, 60.0),
            outlineColor = Color.MAGENTA,
            fillColor = Color.GREEN
        )
    )

    @Test
    @DisplayName("Функция должна возвращать фигуру с наименьшим периметром")
    fun case_1() {
        val expected = testShapes[0]
        Assertions.assertEquals(expected, findShapeWithSmallestPerimeter(testShapes))
    }

    @Test
    @DisplayName("Функция должна возвращать фигуру с наибольшей площадью")
    fun case_2() {
        val expected = testShapes[1]
        Assertions.assertEquals(expected, findShapeWithBiggestArea(testShapes))
    }

}